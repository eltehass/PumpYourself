package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.*
import leo.com.pumpyourself.databinding.LayoutProfileNotificationsBinding
import leo.com.pumpyourself.network.ProfileGetUserResponse

class ProfileNotificationsController : BaseController<LayoutProfileNotificationsBinding>(),
    LazyAdapter.OnItemClickListener<ItemGroup> {

    override lateinit var binding: LayoutProfileNotificationsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_notifications

    override fun getTitle(): String = "Notifications"

    override fun initController() {

        val friendsRequests = (arguments?.get("notifications") as ProfileGetUserResponse? ?:
            ProfileGetUserResponse("", "", listOf(), listOf(), listOf())).friendsRequests
        val groupsRequests = (arguments?.get("notifications") as ProfileGetUserResponse? ?:
            ProfileGetUserResponse("", "", listOf(), listOf(), listOf())).groupsRequests

        binding.rvContainerGroups.initWithLinLay(
            LinearLayout.VERTICAL, GroupsRequestsAdapter(this),
            groupsRequests.map { item -> ItemGroup(item.groupName, item.groupDescription,
                "http://upe.pl.ua:8080/images/groups?image_id=" + item.groupId) })

//        binding.rvContainerFriends.initWithLinLay(
//            LinearLayout.VERTICAL, FriendsRequestsAdapter(this),
//            friendsRequests.map { item -> ItemFriend(item.groupName, item.groupDescription,
//                "http://upe.pl.ua:8080/images/groups?image_id=" + item.groupId) })
    }

    override fun onLazyItemClick(data: ItemGroup) {
        val friendController = FriendProfileController()
        val bundle = Bundle()
        bundle.putSerializable("item_friend", data)
        friendController.arguments = bundle

        show(TAB_PROFILE, friendController)
    }
}