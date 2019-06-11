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

    // TODO: Get profile info from server

    val profileName = "Ser Samuel"
    val profileStatus = "I am the best"

    val friends = listOf (
      ItemFriend("Peter Jackson", "Peter's status"),
      ItemFriend("Michael Swidler", "My status"),
      ItemFriend("Simon Black", "Keep calm")
    )

    val friendsRequests = listOf (
      ItemFriend("Mike Lasker", "Number one"),
      ItemFriend("Amani Sew", "I am here")
    )

    val groupsRequests = listOf (
      ItemGroup("Group of four", "Group for morning run"),
      ItemGroup("Left group", "Boring group")
    )

    // Setting the profile info
    binding.tvName.text = profileName
    binding.tvStatus.text = profileStatus
    binding.tvFriendsNumber.text = friends.size.toString()

    // Setting the button listeners
    binding.exitIcon.setOnClickListener { logOutDialog.show() }
    binding.tvName.setOnClickListener { infoDialog.show() }
    binding.tvStatus.setOnClickListener { infoDialog.show() }

    binding.cvContainerFriends.setOnClickListener {

      val friendController = ProfileFriendsController()

//      val bundle = Bundle()
//      bundle.putParcelableArrayList("friends", friends)
//      friendController.arguments = bundle

      show(TAB_PROFILE, friendController)
    }

    binding.cvContainerNotifications.setOnClickListener { show(TAB_PROFILE, ProfileNotificationsController()) }
  }

  override fun getLayoutId(): Int = R.layout.layout_profile

  override fun getTitle(): String = "Profile"

}