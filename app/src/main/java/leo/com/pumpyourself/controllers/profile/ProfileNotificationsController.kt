package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.GroupsRequestsAdapter
import leo.com.pumpyourself.controllers.profile.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutProfileNotificationsBinding
import leo.com.pumpyourself.network.ProfileGetUserResponse

class ProfileNotificationsController : BaseController<LayoutProfileNotificationsBinding>() {

    override lateinit var binding: LayoutProfileNotificationsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_notifications

    override fun getTitle(): String = "Notifications"

    override fun initController() {

        val friendsRequests = (arguments?.get("notifications") as ProfileGetUserResponse? ?:
            ProfileGetUserResponse("", "", listOf(), listOf(), listOf())).friendsRequests
        val groupsRequests = (arguments?.get("notifications") as ProfileGetUserResponse? ?:
            ProfileGetUserResponse("", "", listOf(), listOf(), listOf())).groupsRequests

        binding.rvContainerGroups.initWithLinLay(
            LinearLayout.VERTICAL, GroupsRequestsAdapter(
                object : LazyAdapter.OnItemClickListener<ItemGroup> {
                    override fun onLazyItemClick(data: ItemGroup) {
                        // TODO: Add dialog to enter the group
//                        val groupController = FriendProfileController()
//                        val bundle = Bundle()
//                        bundle.putSerializable("item_friend", data)
//                        friendController.arguments = bundle
//
//                        show(TAB_PROFILE, friendController)
                    }
                }
            ),
            groupsRequests.map { item -> ItemGroup(item.groupName, item.groupDescription,
                "http://upe.pl.ua:8080/images/groups?image_id=" + item.groupId) })

        // TODO: Add accepting and declining the friend requests

        binding.rvContainerFriends.initWithLinLay(
            LinearLayout.VERTICAL, FriendsRequestsAdapter(
                object : LazyAdapter.OnItemClickListener<ItemFriend> {
                    override fun onLazyItemClick(data: ItemFriend) {
                        val friendController = FriendProfileController()
                        val bundle = Bundle()
                        bundle.putSerializable("item_friend", data)
                        friendController.arguments = bundle

                        show(TAB_PROFILE, friendController)
                    }
                }
            ),
            friendsRequests.map { item -> ItemFriend(item.userName, item.userStatus,
                "http://upe.pl.ua:8080/images/users?image_id=" + item.friendId) })
    }
}