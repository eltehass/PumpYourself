package leo.com.pumpyourself.controllers.profile

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutFriendProfileBinding
import leo.com.pumpyourself.network.PumpYourSelfService

class FriendProfileController : BaseController<LayoutFriendProfileBinding>() {

    override lateinit var binding: LayoutFriendProfileBinding

    override fun getLayoutId(): Int = R.layout.layout_friend_profile

    override fun getTitle(): String = "Friend profile"

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1
        val itemFriend = arguments?.get("item_friend") as ItemFriend? ?: ItemFriend(
            0, 0, "", "", "")

        binding.tvName.text = itemFriend.name
        binding.tvStatus.text = itemFriend.status

        // Making the request
        asyncSafe {

            val networkResult = PumpYourSelfService.service.getProfileInfo(userId).await()

        }

        binding.cvContainerFriends.setOnClickListener { show(TAB_PROFILE, ProfileFriendsController()) }
        binding.cvContainerNotifications.setOnClickListener { show(TAB_PROFILE, MutualGroupsController()) }
    }
}