package leo.com.pumpyourself.controllers.profile

import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutFriendProfileBinding

class FriendProfileController : BaseController<LayoutFriendProfileBinding>() {

    override lateinit var binding: LayoutFriendProfileBinding

    override fun getLayoutId(): Int = R.layout.layout_friend_profile

    override fun getTitle(): String = "Friend profile"

    override fun initController() {

        val itemFriend = arguments?.get("item_friend") as ItemFriend? ?: ItemFriend("","")

        binding.tvName.text = itemFriend.name
        binding.tvStatus.text = itemFriend.status

        binding.cvContainerFriends.setOnClickListener { show(TAB_PROFILE, ProfileFriendsController()) }
        binding.cvContainerNotifications.setOnClickListener { show(TAB_PROFILE, MutualGroupsController()) }
    }
}