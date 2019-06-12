package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.controllers.profile.extras.ItemGroup
import leo.com.pumpyourself.databinding.DialogLogOutBinding
import leo.com.pumpyourself.databinding.LayoutProfileBinding
import leo.com.pumpyourself.network.Friend
import leo.com.pumpyourself.network.FriendsRequest
import leo.com.pumpyourself.network.GroupsRequest
import leo.com.pumpyourself.network.PumpYourSelfService

class ProfileController : BaseController<LayoutProfileBinding>() {

  override lateinit var binding: LayoutProfileBinding

  private lateinit var logOutDialog: AlertDialog
  private lateinit var infoDialog: AlertDialog

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val view = super.onCreateView(inflater, container, savedInstanceState)

    logOutDialog = AlertDialog.Builder(binding.root.context).create()
    infoDialog = AlertDialog.Builder(binding.root.context).create()

    // Initializing the log out dialog
    val logOutDialogView = inflater.inflate(R.layout.dialog_log_out, null)

    logOutDialogView.findViewById<TextView>(R.id.tv_no).setOnClickListener { logOutDialog.dismiss() }
    logOutDialogView.findViewById<TextView>(R.id.tv_yes).setOnClickListener {
      // TODO: Log out
      logOutDialog.dismiss()
    }

    logOutDialog.setView(logOutDialogView)
    logOutDialog.setCancelable(true)

    // Initializing the change info dialog
    val infoDialogView = inflater.inflate(R.layout.dialog_profile_info, null)
    val infoDialogName = infoDialogView.findViewById<EditText>(R.id.et_name)
    val infoDialogStatus = infoDialogView.findViewById<EditText>(R.id.et_status)

    infoDialogView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener { infoDialog.dismiss() }
    infoDialogView.findViewById<TextView>(R.id.tv_save).setOnClickListener {
      if (infoDialogName.text.toString().isEmpty() || infoDialogStatus.text.toString().isEmpty()) {
        Toast.makeText(it.context, "Empty field", Toast.LENGTH_LONG).show()
      } else {
        binding.tvName.text = infoDialogName.text.toString()
        binding.tvStatus.text = infoDialogStatus.text.toString()

        infoDialog.dismiss()
      }
    }

    infoDialog.setView(infoDialogView)
    infoDialog.setCancelable(true)

    return view
  }

  override fun initController() {

    // TODO: Get user id
      val userId = 1

      asyncSafe {

          val networkResult = PumpYourSelfService.service.getProfileInfo(userId).await()

          // Setting the profile info
          binding.tvName.text = networkResult.userName
          binding.tvStatus.text = networkResult.userStatus
          binding.tvFriendsNumber.text = networkResult.friends.size.toString()

          // Setting the button listeners
          binding.cvContainerFriends.setOnClickListener {

              val friendController = ProfileFriendsController()

              val bundle = Bundle()
              bundle.putSerializable("user_id", userId)
              bundle.putSerializable("friends", networkResult)
              friendController.arguments = bundle

              show(TAB_PROFILE, friendController)
          }

          binding.cvContainerNotifications.setOnClickListener {

              val notificationsController = ProfileNotificationsController()

              val bundle = Bundle()
              bundle.putSerializable("notifications", networkResult)
              notificationsController.arguments = bundle

              show(TAB_PROFILE, ProfileNotificationsController())
          }
      }

      // Setting the dialogues
      binding.exitIcon.setOnClickListener { logOutDialog.show() }
      binding.tvName.setOnClickListener { infoDialog.show() }
      binding.tvStatus.setOnClickListener { infoDialog.show() }
  }

  override fun getLayoutId(): Int = R.layout.layout_profile

  override fun getTitle(): String = "Profile"

}