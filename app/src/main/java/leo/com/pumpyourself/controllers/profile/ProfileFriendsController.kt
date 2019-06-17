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
import leo.com.pumpyourself.network.Friend

class ProfileFriendsController : BaseController<LayoutProfileFriendsBinding>(),
    LazyAdapter.OnItemClickListener<ItemFriend> {

    override lateinit var binding: LayoutProfileFriendsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_friends

    override fun getTitle(): String = "Friends"

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1
        val friends = arguments?.get("friends") as Array<Friend>? ?: arrayOf()

        binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, FriendsAdapter(this),
            friends.map { item -> ItemFriend(userId, item.friendId,
                item.userName, item.userStatus,
                "http://upe.pl.ua:8080/images/users?image_id=" + item.friendId) })

        binding.fabAction.setOnClickListener {

            val addFriendController = ProfileAddFriendController()

            val bundle = Bundle()
            bundle.putSerializable("user_id", userId)
            addFriendController.arguments = bundle

            show(TAB_PROFILE, addFriendController)
        }
    }

    override fun onLazyItemClick(data: ItemFriend) {
        val friendController = FriendProfileController()
        val bundle = Bundle()
        bundle.putSerializable("item_friend", data)
        friendController.arguments = bundle

        show(TAB_PROFILE, friendController)
    }
}