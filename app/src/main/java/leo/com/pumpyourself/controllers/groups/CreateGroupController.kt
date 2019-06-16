package leo.com.pumpyourself.controllers.groups

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.CameraEvent
import leo.com.pumpyourself.common.Constants
import leo.com.pumpyourself.common.encodeToBase64
import leo.com.pumpyourself.common.setCircleImgBitmap
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.DayExercisesAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import leo.com.pumpyourself.controllers.groups.extras.ItemMember
import leo.com.pumpyourself.controllers.groups.extras.MembersAdapter
import leo.com.pumpyourself.databinding.LayoutCreateGroupBinding
import leo.com.pumpyourself.network.AddGroupRequest
import leo.com.pumpyourself.network.CreateTrainingRequest
import leo.com.pumpyourself.network.PumpYourSelfService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

class CreateGroupController : BaseController<LayoutCreateGroupBinding>() {

  override lateinit var binding: LayoutCreateGroupBinding

  private lateinit var dialog: AlertDialog

  private val members: MutableList<ItemMember> = mutableListOf()
  private val daysInfo: MutableList<ItemDayExercise> = mutableListOf()

  private var mealBitmap: Bitmap? = null


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = super.onCreateView(inflater, container, savedInstanceState)

    dialog = AlertDialog.Builder(binding.root.context).create()

    val dialogView = inflater.inflate(R.layout.dialog_add_day, null)
    val dialogEditText = dialogView.findViewById<EditText>(R.id.et_description)

    dialogView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { dialog.dismiss() }
    dialogView.findViewById<TextView>(R.id.tv_add).setOnClickListener {
      if (dialogEditText.text.toString().isEmpty()) {
        Toast.makeText(it.context, "Field is empty", Toast.LENGTH_LONG).show()
      } else {
        (binding.rvDays.adapter as DayExercisesAdapter).let{ adapter ->
          adapter.addData(ItemDayExercise("Day${adapter.itemCount+1}", dialogEditText.text.toString()))
          daysInfo.add(ItemDayExercise("Day ${adapter.itemCount}", dialogEditText.text.toString()))
        }

        dialogEditText.setText("")
        dialog.dismiss()
      }
    }

    dialog.setView(dialogView)
    dialog.setCancelable(true)

    return view
  }

  override fun initController() {

    val userId = arguments?.get("user_id") as Int? ?: 1

    binding.rvMembers.initWithLinLay(LinearLayout.VERTICAL, MembersAdapter(), listOf())
    binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), listOf())

    binding.tvAddMember.setOnClickListener {
      val friendsController = FriendsController()
      val bundle = Bundle()
      bundle.putSerializable("user_id", userId)
      friendsController.arguments = bundle
      show(TAB_GROUPS, friendsController)
    }

    binding.tvAddDay.setOnClickListener {
      dialog.show()
    }

    binding.ivGroupIcon.setOnClickListener {
//      val intent = Intent(Intent.ACTION_PICK).apply {
//          type = "image/*"
//          putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
//      }
//
//      startActivityForResult(intent, GALLERY_REQUEST_CODE)

      if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), Constants.MY_CAMERA_PERMISSION_CODE)
      } else {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST)
      }
    }

    binding.tvCreate.setOnClickListener {

      val currDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        .format(Calendar.getInstance().time)

      asyncSafe {
        val trainingId = PumpYourSelfService.service.createTraining(daysInfo.map {
          CreateTrainingRequest(binding.etGroupName.text.toString() + "_training", "",
            it.name.substring(4).toInt(), it.description)
        }).await()

        val photoBase64 = if (mealBitmap != null) encodeToBase64(mealBitmap!!, context!!) else null

        PumpYourSelfService.service.addGroup(AddGroupRequest(
          userId, binding.etGroupName.text.toString(), binding.etGroupDescription.text.toString(),
          photoBase64, trainingId, currDateStr)).await()

        mainActivity.onBackPressed()
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      if (!EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().register(this)
      }
  }

//  @Subscribe
//  fun onImageGalleryEvent(event: ImageGalleryEvent) {
//    binding.ivGroupIcon.setImageURI(event.uri)
//  }

  @Subscribe
  fun onCameraEvent(event: CameraEvent) {
    binding.ivGroupIcon.setCircleImgBitmap(event.bitmap)
    mealBitmap = event.bitmap
  }

  override fun getLayoutId(): Int = R.layout.layout_create_group

  override fun getTitle(): String = "Create group"
}