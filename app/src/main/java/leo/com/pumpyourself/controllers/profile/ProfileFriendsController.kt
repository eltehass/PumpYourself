package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.FriendsAdapter
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.controllers.profile.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutProfileFriendsBinding
import leo.com.pumpyourself.network.ProfileGetUserResponse

class ProfileFriendsController : BaseController<LayoutProfileFriendsBinding>(),
    LazyAdapter.OnItemClickListener<ItemFriend> {

    override lateinit var binding: LayoutProfileFriendsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_friends

    override fun getTitle(): String = "Friends"

    var userId = 1

    override fun initController() {

        userId = arguments?.get("user_id") as Int? ?: 1
        val friends = (arguments?.get("friends") as ProfileGetUserResponse? ?:
            ProfileGetUserResponse("", "", listOf(), listOf(), listOf())).friends

        binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, FriendsAdapter(this),
            friends.map { item -> ItemFriend(userId, item.friendId.toInt(),
                item.userName, item.userStatus,
                "http://upe.pl.ua:8080/images/users?image_id=" + item.friendId) })

        binding.fabAction.setOnClickListener { show(TAB_PROFILE, ProfileAddFriendController()) }
    }

    override fun onLazyItemClick(data: ItemFriend) {
        val friendController = FriendProfileController()
        val bundle = Bundle()
        bundle.putSerializable("user_id", userId)
        bundle.putSerializable("item_friend", data)
        friendController.arguments = bundle

        show(TAB_PROFILE, friendController)
    }
}