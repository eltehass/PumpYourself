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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.*
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.*
import leo.com.pumpyourself.databinding.LayoutEditGroupBinding
import leo.com.pumpyourself.network.EditGroupRequest
import leo.com.pumpyourself.network.InviteFriendInGroupRequest
import leo.com.pumpyourself.network.PumpYourSelfService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class EditGroupController : BaseController<LayoutEditGroupBinding>() {

    override lateinit var binding: LayoutEditGroupBinding

    private lateinit var dialogAddMember: AlertDialog

    private var mealBitmap: Bitmap? = null

    var userId : Int = 1
    var groupId : Int = 1

    private fun createDialog() {

        dialogAddMember = AlertDialog.Builder(binding.root.context).create()

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
                            }
                            dialogAddMember.dismiss()

                            asyncSafe {
                                PumpYourSelfService.service.inviteFriendIntoGroup(
                                    InviteFriendInGroupRequest(userId, data.friendId, groupId)
                                ).await()
                            }
                        }
                    }
                ), friends)

            dialogAddMember.setView(dialogAddMemberView)
            dialogAddMember.setCancelable(true)
        }
    }

  override fun initController() {

    userId = arguments?.get("user_id") as Int? ?: 1

    val itemGroup = arguments?.get("item_group") as ItemGroup? ?:
        ItemGroup(0, "","", "")
    groupId = itemGroup.groupId

    createDialog()

    binding.etGroupName.setText(itemGroup.name)
    binding.etGroupDescription.setText(itemGroup.description)

    if (itemGroup.imgUrl.isNotEmpty()) {
      binding.ivGroupIcon.setCircleImgUrl(itemGroup.imgUrl)
    } else {
      binding.ivGroupIcon.setCircleImgResource(R.drawable.ic_launcher_background)
    }

      asyncSafe {
          val networkResult = PumpYourSelfService.service.getMoreGroupInfo(itemGroup.groupId).await()

          val members = networkResult.members.map {
              ItemMember(it.userId, it.userName,
                  "http://upe.pl.ua:8080/images/users?image_id=${it.userId}")
          }

          val dayExercises = networkResult.trainings.map {
              ItemDayExercise("Day " + it.dayNumber, it.dayPlan)
          }

          binding.rvMembers.initWithLinLay(LinearLayout.VERTICAL, MembersAdapter(), members)
          binding.rvDays.initWithLinLay(LinearLayout.VERTICAL, DayExercisesAdapter(), dayExercises)
      }

    binding.tvAddMember.setOnClickListener {
        dialogAddMember.show()
    }

    binding.ivGroupIcon.setOnClickListener {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.CAMERA),
                Constants.MY_CAMERA_PERMISSION_CODE
            )
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST)
        }
    }

      binding.tvSave.setOnClickListener {

          val photoBase64 = if (mealBitmap != null) encodeToBase64(mealBitmap!!, context!!) else null

          asyncSafe {
              PumpYourSelfService.service.editGroup(EditGroupRequest(itemGroup.groupId,
                  binding.etGroupName.text.toString(), binding.etGroupDescription.text.toString(),
                  photoBase64)).await()
          }

          mainActivity.onBackPressed()
      }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (!EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().register(this)
    }
  }

  @Subscribe
  fun onCameraEvent(event: CameraEvent) {
      binding.ivGroupIcon.setCircleImgBitmap(event.bitmap)
      mealBitmap = event.bitmap
  }

  override fun getLayoutId(): Int = R.layout.layout_edit_group

  override fun getTitle(): String = "Edit group"
}