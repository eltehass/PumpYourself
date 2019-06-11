package leo.com.pumpyourself.controllers.groups

import android.support.v7.widget.LinearLayoutManager
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.FriendsAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemFriend
import leo.com.pumpyourself.databinding.LayoutFriendsBinding

class FriendsController : BaseController<LayoutFriendsBinding>() {

    override lateinit var binding: LayoutFriendsBinding

    override fun initController() {

        val friends = listOf(
                ItemFriend("Peter Jackson", "His status", ""),
                ItemFriend("Peter Jackson", "His status", ""),
                ItemFriend("Peter Jackson", "His status", ""),
                ItemFriend("Peter Jackson", "His status", ""),
                ItemFriend("Peter Jackson", "His status", "")
        )

        binding.rvContainer.initWithLinLay(LinearLayoutManager.VERTICAL, FriendsAdapter(), friends)
    }

    override fun getLayoutId(): Int = R.layout.layout_friends

    override fun getTitle(): String = "Friends"
}