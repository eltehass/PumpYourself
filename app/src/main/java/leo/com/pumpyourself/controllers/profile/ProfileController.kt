package leo.com.pumpyourself.controllers.profile

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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.*
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutProfileBinding
import leo.com.pumpyourself.network.ChangeUserInfo
import leo.com.pumpyourself.network.PumpYourSelfService
import org.greenrobot.eventbus.Subscribe

class ProfileController : BaseController<LayoutProfileBinding>() {

  override lateinit var binding: LayoutProfileBinding

  private lateinit var logOutDialog: AlertDialog
  private lateinit var infoDialog: AlertDialog

  private var mealBitmap: Bitmap? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val userId = AccountManager.getId(context!!)

    val view = super.onCreateView(inflater, container, savedInstanceState)

    logOutDialog = AlertDialog.Builder(binding.root.context).create()
    infoDialog = AlertDialog.Builder(binding.root.context).create()

    // Initializing the log out dialog
    val logOutDialogView = inflater.inflate(R.layout.dialog_log_out, null)

    logOutDialogView.findViewById<TextView>(R.id.tv_no).setOnClickListener { logOutDialog.dismiss() }
    logOutDialogView.findViewById<TextView>(R.id.tv_yes).setOnClickListener {
      AccountManager.logOut(context!!)
      mainActivity.goToLoginActivity()
      logOutDialog.dismiss()
    }

    logOutDialog.setView(logOutDialogView)
    logOutDialog.setCancelable(true)

    // Initializing the change info dialog
    val infoDialogView = inflater.inflate(R.layout.dialog_profile_info, null)
    val infoDialogName = infoDialogView.findViewById<EditText>(R.id.et_name)
    val infoDialogStatus = infoDialogView.findViewById<EditText>(R.id.et_status)
    val infoDialogImage = infoDialogView.findViewById<ImageView>(R.id.iv_icon)

    infoDialogName.setText(binding.tvName.text.toString())
    infoDialogStatus.setText(binding.tvStatus.text.toString())
    infoDialogImage.setCircleImgUrl("http://upe.pl.ua:8080/images/users?image_id=$userId")

      infoDialogImage.setOnClickListener {
          if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
              ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), Constants.MY_CAMERA_PERMISSION_CODE)
          } else {
              val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
              startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST)
          }
      }

    infoDialogView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { infoDialog.dismiss() }
    infoDialogView.findViewById<TextView>(R.id.tv_save).setOnClickListener {
      if (infoDialogName.text.toString().isEmpty() || infoDialogStatus.text.toString().isEmpty()) {
        Toast.makeText(it.context, "Empty field", Toast.LENGTH_LONG).show()
      } else {
          asyncSafe {
              val photoBase64 = if (mealBitmap != null) encodeToBase64(mealBitmap!!, context!!) else null

              PumpYourSelfService.service.changeProfileInfo(ChangeUserInfo(
                  userId, infoDialogName.text.toString(), infoDialogStatus.text.toString(),
                  photoBase64)).await()

              binding.tvName.text = infoDialogName.text.toString()
              binding.tvStatus.text = infoDialogStatus.text.toString()

              infoDialog.dismiss()
          }
      }
    }

    infoDialog.setView(infoDialogView)
    infoDialog.setCancelable(true)

    return view
  }

  override fun initController() {

      val userId = AccountManager.getId(context!!)

      asyncSafe {

          val networkResult = PumpYourSelfService.service.getProfileInfo(userId).await()

          // Setting the profile info
          binding.tvName.text = networkResult.userName
          binding.tvStatus.text = networkResult.userStatus
          binding.imageView.setImgUrl("http://upe.pl.ua:8080/images/users?image_id=$userId")
          binding.tvFriendsNumber.text = networkResult.friends.size.toString()

          // Setting the button listeners
          binding.cvContainerFriends.setOnClickListener {

              val friendController = ProfileFriendsController()

              val bundle = Bundle()
              bundle.putSerializable("user_id", userId)
              bundle.putSerializable("friends", networkResult.friends.toTypedArray())
              friendController.arguments = bundle

              show(TAB_PROFILE, friendController)
          }

          binding.cvContainerNotifications.setOnClickListener {

              val notificationsController = ProfileNotificationsController()

              val bundle = Bundle()
              bundle.putSerializable("user_id", userId)
              bundle.putSerializable("friends_requests", networkResult.friendsRequests.toTypedArray())
              bundle.putSerializable("groups_requests", networkResult.groupsRequests.toTypedArray())
              notificationsController.arguments = bundle

              show(TAB_PROFILE, notificationsController)
          }
      }

      // Setting the dialogues
      binding.exitIcon.setOnClickListener { logOutDialog.show() }
      binding.tvName.setOnClickListener { infoDialog.show() }
      binding.tvStatus.setOnClickListener { infoDialog.show() }
  }

  override fun getLayoutId(): Int = R.layout.layout_profile

  override fun getTitle(): String = "Profile"

    @Subscribe
    fun onCameraEvent(event: CameraEvent) {
        LayoutInflater.from(context!!).inflate(R.layout.dialog_log_out, null)
            .findViewById<ImageView>(R.id.iv_icon).setCircleImgBitmap(event.bitmap)
        mealBitmap = event.bitmap
    }
}