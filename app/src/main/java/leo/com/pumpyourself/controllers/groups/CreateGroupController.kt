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
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.*
import leo.com.pumpyourself.databinding.LayoutCreateGroupBinding
import leo.com.pumpyourself.network.AddGroupRequest
import leo.com.pumpyourself.network.CreateTrainingRequest
import leo.com.pumpyourself.network.InviteFriendInGroupRequest
import leo.com.pumpyourself.network.PumpYourSelfService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

class CreateGroupController : BaseController<LayoutCreateGroupBinding>() {

  override lateinit var binding: LayoutCreateGroupBinding

  private lateinit var dialogAddDay: AlertDialog
  private lateinit var dialogAddMember: AlertDialog

  private val members: MutableList<ItemMember> = mutableListOf()
  private val daysInfo: MutableList<ItemDayExercise> = mutableListOf()

  private var mealBitmap: Bitmap? = null

  var userId : Int = 1

  private fun createDialog() {

    dialogAddDay = AlertDialog.Builder(binding.root.context).create()
    dialogAddMember = AlertDialog.Builder(binding.root.context).create()

    // Add day dialog
    val dialogAddDayView = LayoutInflater.from(context!!).inflate(R.layout.dialog_add_day, null)
    val dialogAddDayText = dialogAddDayView.findViewById<EditText>(R.id.et_description)

    dialogAddDayView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { dialogAddDay.dismiss() }
    dialogAddDayView.findViewById<TextView>(R.id.tv_add).setOnClickListener {
      if (dialogAddDayText.text.toString().isEmpty()) {
        Toast.makeText(it.context, "Field is empty", Toast.LENGTH_LONG).show()
      } else {
        (binding.rvDays.adapter as DayExercisesAdapter).let{ adapter ->
          adapter.addData(ItemDayExercise("Day${adapter.itemCount+1}", dialogAddDayText.text.toString()))
          daysInfo.add(ItemDayExercise("Day ${adapter.itemCount}", dialogAddDayText.text.toString()))
        }

        dialogAddDayText.setText("")
        dialogAddDay.dismiss()
      }
    }

    dialogAddDay.setView(dialogAddDayView)
    dialogAddDay.setCancelable(true)

      // Add member dialog
      val dialogAddMemberView = LayoutInflater.from(context!!).inflate(R.layout.dialog_invite_friend, null)

      asyncSafe {
          val networkResult = PumpYourSelfService.service.getProfileInfo(userId).await()

          val friends = networkResult.friends.map {
              ItemFriend(it.friendId, it.userName, it.userStatus,
                  "http://upe.pl.ua:8080/images/users?image_id=${it.friendId}")
          }

          dialogAddMemberView.findViewById<RecyclerView>(R.id.rv_container).initWithLinLay(
              LinearLayout.VERTICAL, FriendsAdapter(
                  object : LazyAdapter.OnItemClickListener<ItemFriend> {
                      override fun onLazyItemClick(data: ItemFriend) {
                          (binding.rvMembers.adapter as MembersAdapter).let{ adapter ->
                              adapter.addData(ItemMember(data.friendId, data.name, data.imgUrl))
                              members.add(ItemMember(data.friendId, data.name, data.imgUrl))
                          }
                          dialogAddMember.dismiss()
                      }
                  }
              ), friends)

          dialogAddMember.setView(dialogAddMemberView)
          dialogAddMember.setCancelable(true)
      }
  }

  override fun initController() {

    userId = arguments?.get("user_id") as Int? ?: 1

    createDialog()

    binding.rvMembers.initWithLinLay(LinearLayout.VERTICAL, MembersAdapter(), listOf())
    binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), listOf())

    binding.tvAddMember.setOnClickListener {
        dialogAddMember.show()
    }

    binding.tvAddDay.setOnClickListener {
        dialogAddDay.show()
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

        val groupId = PumpYourSelfService.service.addGroup(AddGroupRequest(
          userId, binding.etGroupName.text.toString(), binding.etGroupDescription.text.toString(),
          photoBase64, trainingId, currDateStr)).await()

        // Sending the invitations
        members.forEach {
            PumpYourSelfService.service.inviteFriendIntoGroup(
                InviteFriendInGroupRequest(userId, it.userId, groupId)).await()
        }

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