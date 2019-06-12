package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.profile.extras.AddFriendAdapter
import leo.com.pumpyourself.controllers.profile.extras.ItemAddFriend
import leo.com.pumpyourself.databinding.LayoutProfileAddFriendBinding
import leo.com.pumpyourself.network.ProcessFriendRequest
import leo.com.pumpyourself.network.PumpYourSelfService

class ProfileAddFriendController : BaseController<LayoutProfileAddFriendBinding>() {

    override lateinit var binding: LayoutProfileAddFriendBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_add_friend

    override fun getTitle(): String = "Add a friend"

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1

        binding.searchIcon.setOnClickListener {

            asyncSafe {
                val networkResult = PumpYourSelfService.service.searchUsers(
                    binding.etSearch.text.toString()).await()

                binding.rvContainer.initWithLinLay(
                    LinearLayout.VERTICAL, AddFriendAdapter(
                        object : LazyAdapter.OnItemClickListener<ItemAddFriend> {
                            override fun onLazyItemClick(data: ItemAddFriend) {

                                // Sending the friend request
                                asyncSafe {
                                    PumpYourSelfService.service.sendFriendRequest(
                                        ProcessFriendRequest(userId, data.friendId))

                                    Toast.makeText(context, "Request was sent", Toast.LENGTH_LONG).show()
                                }
                            }
                        }),
                    networkResult.map { item -> ItemAddFriend(item.userId, item.userName,
                        "http://upe.pl.ua:8080/images/users?image_id=" + item.userId) })

            }
        }
    }
}