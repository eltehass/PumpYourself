package leo.com.pumpyourself.controllers.profile

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.setCircleImgUrl
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.FriendsRequestsAdapter
import leo.com.pumpyourself.controllers.profile.extras.GroupsRequestsAdapter
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.controllers.profile.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutProfileNotificationsBinding
import leo.com.pumpyourself.network.*

class ProfileNotificationsController : BaseController<LayoutProfileNotificationsBinding>() {

    override lateinit var binding: LayoutProfileNotificationsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_notifications

    override fun getTitle(): String = "Notifications"

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1

        val friendsRequests = arguments?.get("friends_requests") as Array<FriendsRequest>? ?: arrayOf()
        val groupsRequests = arguments?.get("groups_requests") as Array<GroupsRequest>? ?: arrayOf()

        binding.rvContainerGroups.initWithLinLay(
            LinearLayout.VERTICAL, GroupsRequestsAdapter(
                object : LazyAdapter.OnItemClickListener<ItemGroup> {
                    override fun onLazyItemClick(data: ItemGroup) {

                        val inflater = LayoutInflater.from(context) // Maybe wrong

                        val groupDialog = AlertDialog.Builder(binding.root.context).create()
                        val groupDialogView = inflater.inflate(R.layout.dialog_group_notification, null)

                        // Setting the data
                        val groupDialogName = groupDialogView.findViewById<TextView>(R.id.tv_name)
                        val groupDialogDescription = groupDialogView.findViewById<TextView>(R.id.tv_description)
                        val groupDialogImage = groupDialogView.findViewById<ImageView>(R.id.iv_icon)

                        groupDialogName.text = data.name
                        groupDialogDescription.text = data.description
                        groupDialogImage.setCircleImgUrl(data.imgUrl)

                        groupDialogView.findViewById<TextView>(R.id.tv_accept).setOnClickListener {
                            asyncSafe {
                                PumpYourSelfService.service.acceptGroupRequest(
                                    ProcessGroupRequest(data.userId, data.groupId)).await()

                                (binding.rvContainerGroups.adapter as GroupsRequestsAdapter).removeData(data)

                                Toast.makeText(context, "Request was accepted", Toast.LENGTH_LONG).show()

                                groupDialog.dismiss()
                            }
                        }

                        groupDialogView.findViewById<TextView>(R.id.tv_decline).setOnClickListener {
                            asyncSafe {
                                PumpYourSelfService.service.declineGroupRequest(
                                    ProcessGroupRequest(data.userId, data.groupId)).await()

                                (binding.rvContainerGroups.adapter as GroupsRequestsAdapter).removeData(data)

                                Toast.makeText(context, "Request was declined", Toast.LENGTH_LONG).show()

                                groupDialog.dismiss()
                            }
                        }

                        groupDialog.setView(groupDialogView)
                        groupDialog.setCancelable(true)

                        groupDialog.show()
                    }
                }
            ),
            groupsRequests.map { item -> ItemGroup(userId, item.groupId.toInt(), item.groupName, item.groupDescription,
                "http://upe.pl.ua:8080/images/groups?image_id=" + item.groupId) })

        // TODO: Add accepting and declining the friend requests

        binding.rvContainerFriends.initWithLinLay(
            LinearLayout.VERTICAL, FriendsRequestsAdapter(
                object : LazyAdapter.OnItemClickListener<ItemFriend> {
                    override fun onLazyItemClick(data: ItemFriend) {

                        val inflater = LayoutInflater.from(context) // Maybe wrong

                        val friendDialog = AlertDialog.Builder(binding.root.context).create()
                        val friendDialogView = inflater.inflate(R.layout.dialog_friend_notification, null)

                        // Setting the data
                        val friendDialogName = friendDialogView.findViewById<TextView>(R.id.tv_name)
                        val friendDialogStatus = friendDialogView.findViewById<TextView>(R.id.tv_status)
                        val friendDialogImage = friendDialogView.findViewById<ImageView>(R.id.iv_icon)

                        friendDialogName.text = data.name
                        friendDialogStatus.text = data.status
                        friendDialogImage.setCircleImgUrl(data.imgUrl)

                        friendDialogView.findViewById<TextView>(R.id.tv_accept).setOnClickListener {
                            asyncSafe {
                                PumpYourSelfService.service.acceptFriendRequest(
                                    ProcessFriendRequest(data.userId, data.friendId)).await()

                                (binding.rvContainerFriends.adapter as FriendsRequestsAdapter).removeData(data)

                                Toast.makeText(context, "Request was accepted", Toast.LENGTH_LONG).show()

                                friendDialog.dismiss()
                            }
                        }

                        friendDialogView.findViewById<TextView>(R.id.tv_decline).setOnClickListener {
                            asyncSafe {
                                PumpYourSelfService.service.declineFriendRequest(
                                    ProcessFriendRequest(data.userId, data.friendId)).await()

                                (binding.rvContainerFriends.adapter as FriendsRequestsAdapter).removeData(data)

                                Toast.makeText(context, "Request was declined", Toast.LENGTH_LONG).show()

                                friendDialog.dismiss()
                            }
                        }

                        friendDialog.setView(friendDialogView)
                        friendDialog.setCancelable(true)

                        friendDialog.show()
                    }
                }
            ),
            friendsRequests.map { item -> ItemFriend(userId, item.friendId.toInt(), item.userName,
                item.userStatus, "http://upe.pl.ua:8080/images/users?image_id=" + item.friendId) })
    }
}