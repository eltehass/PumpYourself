package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutMutualGroupsBinding

class MutualGroupsController : BaseController<LayoutMutualGroupsBinding>(),
    LazyAdapter.OnItemClickListener<ItemGroup> {

    override lateinit var binding: LayoutMutualGroupsBinding

    override fun getLayoutId(): Int = R.layout.layout_mutual_groups

    override fun getTitle(): String = "Mutual groups"

    override fun initController() {

        //binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, FriendsAdapter(this), dataList)
        //binding.fabAction.setOnClickListener { show(TAB_PROFILE, ProfileAddFriendController()) }
    }

    override fun onLazyItemClick(data: ItemGroup) {
        val friendController = FriendProfileController()
        val bundle = Bundle()
        bundle.putSerializable("item_friend", data)
        friendController.arguments = bundle

        show(TAB_PROFILE, friendController)
    }

}