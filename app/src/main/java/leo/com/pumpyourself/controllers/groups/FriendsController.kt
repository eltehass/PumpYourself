package leo.com.pumpyourself.controllers.groups

import android.support.v7.widget.LinearLayoutManager
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.FriendsAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutFriendsBinding
import leo.com.pumpyourself.network.PumpYourSelfService

class FriendsController : BaseController<LayoutFriendsBinding>() {

    override lateinit var binding: LayoutFriendsBinding

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1

        asyncSafe {
            val networkResult = PumpYourSelfService.service.getProfileInfo(userId).await()

            val friends = networkResult.friends.map {
                ItemFriend(it.friendId, it.userName, it.userStatus,
                    "http://upe.pl.ua:8080/images/users?image_id=${it.friendId}")
            }

            binding.rvContainer.initWithLinLay(LinearLayoutManager.VERTICAL, FriendsAdapter(), friends)
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_friends

    override fun getTitle(): String = "Friends"
}