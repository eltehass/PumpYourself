package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.FriendsAdapter
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutProfileFriendsBinding
import leo.com.pumpyourself.network.FriendsRequest
import leo.com.pumpyourself.network.GroupsRequest
import leo.com.pumpyourself.network.ProfileGetUserResponse

class ProfileFriendsController : BaseController<LayoutProfileFriendsBinding>(),
    LazyAdapter.OnItemClickListener<ItemFriend> {

    override lateinit var binding: LayoutProfileFriendsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_friends

    override fun getTitle(): String = "Friends"

    override fun initController() {

        val friends = (arguments?.get("friends") as ProfileGetUserResponse? ?:
            ProfileGetUserResponse("", "", listOf(), listOf(), listOf())).friends

        binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, FriendsAdapter(this),
            friends.map { item -> ItemFriend(item.userName, item.userStatus, "") })
        binding.fabAction.setOnClickListener { show(TAB_PROFILE, ProfileAddFriendController()) }
    }

    override fun onLazyItemClick(data: ItemFriend) {
        val friendController = FriendProfileController()
        val bundle = Bundle()
        bundle.putSerializable("item_friend", data)
        friendController.arguments = bundle

        show(TAB_PROFILE, friendController)
    }
}