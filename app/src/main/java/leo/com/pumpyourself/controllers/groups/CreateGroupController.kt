package leo.com.pumpyourself.controllers.groups

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.Constants.GALLERY_REQUEST_CODE
import leo.com.pumpyourself.common.ImageGalleryEvent
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
      val intent = Intent(Intent.ACTION_PICK).apply {
          type = "image/*"
          putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
      }

      startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    binding.tvCreate.setOnClickListener {

      val currDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        .format(Calendar.getInstance().time)

      asyncSafe {
        val trainingId = PumpYourSelfService.service.createTraining(daysInfo.map {
          CreateTrainingRequest(binding.etGroupName.text.toString() + "_training", "",
            it.name.substring(4).toInt(), it.description)
        }).await()

        PumpYourSelfService.service.addGroup(AddGroupRequest(
          userId, binding.etGroupName.text.toString(), binding.etGroupDescription.text.toString(),
          null, trainingId, currDateStr)).await()

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

  @Subscribe
  fun onImageGalleryEvent(event: ImageGalleryEvent) {
    binding.ivGroupIcon.setImageURI(event.uri)
  }

  override fun getLayoutId(): Int = R.layout.layout_create_group

  override fun getTitle(): String = "Create group"
}