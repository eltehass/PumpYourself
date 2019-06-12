package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.setImgUrl
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.profile.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutFriendProfileBinding
import leo.com.pumpyourself.network.PumpYourSelfService

class FriendProfileController : BaseController<LayoutFriendProfileBinding>() {

    override lateinit var binding: LayoutFriendProfileBinding

    override fun getLayoutId(): Int = R.layout.layout_friend_profile

    override fun getTitle(): String = "Friend profile"

    override fun initController() {

        val itemFriend = arguments?.get("item_friend") as ItemFriend? ?: ItemFriend(
            0, 0, "", "", "")

        binding.tvName.text = itemFriend.name
        binding.tvStatus.text = itemFriend.status
        binding.imageView.setImgUrl("http://upe.pl.ua:8080/images/users?image_id=" + itemFriend.friendId)

        // Making the request
        asyncSafe {

            val networkResult = PumpYourSelfService.service.getFriendInfo(
                itemFriend.userId, itemFriend.friendId).await()

            // Setting the numbers
            binding.tvFriendsNumber.text = networkResult.friends.size.toString()
            binding.tvGroupsNumber.text = networkResult.mutualGroups.size.toString()

            // Setting the listeners
            binding.cvContainerFriends.setOnClickListener {

                val friendController = ProfileFriendsController()

                val bundle = Bundle()
                bundle.putSerializable("user_id", itemFriend.friendId)
                bundle.putSerializable("friends", networkResult.friends.toTypedArray())
                friendController.arguments = bundle

                show(TAB_PROFILE, friendController)
            }

            binding.cvContainerNotifications.setOnClickListener {

                val mutualGroupsController = MutualGroupsController()

                val bundle = Bundle()
                bundle.putSerializable("mutual_groups", networkResult.mutualGroups.toTypedArray())
                mutualGroupsController.arguments = bundle

                show(TAB_PROFILE, mutualGroupsController)
            }
        }
    }
}