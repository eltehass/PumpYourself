package leo.com.pumpyourself.controllers.groups

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.FriendsAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutFriendsBinding
import leo.com.pumpyourself.network.InviteFriendInGroupRequest
import leo.com.pumpyourself.network.PumpYourSelfService

class FriendsController : BaseController<LayoutFriendsBinding>(), LazyAdapter.OnItemClickListener<ItemFriend> {

    override lateinit var binding: LayoutFriendsBinding

    var userId : Int = 1

    var groupId : Int = 1

    override fun initController() {

        userId = arguments?.get("user_id") as Int? ?: 1

        asyncSafe {
            val networkResult = PumpYourSelfService.service.getProfileInfo(userId).await()

            val friends = networkResult.friends.map {
                ItemFriend(it.friendId, it.userName, it.userStatus,
                    "http://upe.pl.ua:8080/images/users?image_id=${it.friendId}")
            }

            binding.rvContainer.initWithLinLay(LinearLayoutManager.VERTICAL,
                FriendsAdapter(this@FriendsController), friends)
        }


    }

    override fun onLazyItemClick(data: ItemFriend) {
    }

    override fun getLayoutId(): Int = R.layout.layout_friends

    override fun getTitle(): String = "Friends"
}