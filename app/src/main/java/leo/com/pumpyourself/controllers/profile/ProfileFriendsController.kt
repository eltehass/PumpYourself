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

class ProfileFriendsController : BaseController<LayoutProfileFriendsBinding>(),
    LazyAdapter.OnItemClickListener<ItemFriend> {

    override lateinit var binding: LayoutProfileFriendsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_friends

    override fun getTitle(): String = "Friends"

    override fun initController() {

        val dataList = listOf (
            ItemFriend("Peter Jackson", "Peter's status"),
            ItemFriend("Michael Swidler", "My status"),
            ItemFriend("Simon Black", "Keep calm")
        )

        binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, FriendsAdapter(this), dataList)
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