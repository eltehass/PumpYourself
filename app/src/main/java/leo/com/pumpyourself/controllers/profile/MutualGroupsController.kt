package leo.com.pumpyourself.controllers.profile

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.GroupsController
import leo.com.pumpyourself.controllers.groups.extras.GroupsAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutMutualGroupsBinding
import leo.com.pumpyourself.network.UserGroupResponse

class MutualGroupsController : BaseController<LayoutMutualGroupsBinding>(),
    LazyAdapter.OnItemClickListener<ItemGroup> {

    override lateinit var binding: LayoutMutualGroupsBinding

    override fun getLayoutId(): Int = R.layout.layout_mutual_groups

    override fun getTitle(): String = "Mutual groups"

    override fun initController() {

        val groups = arguments?.get("mutual_groups") as Array<UserGroupResponse>? ?: arrayOf()

        binding.rvContainer.initWithLinLay(
            LinearLayout.VERTICAL, GroupsAdapter(this),
            groups.map { item -> ItemGroup(item.groupName, item.groupDescription,
                "http://upe.pl.ua:8080/images/groups?image_id=" + item.groupId) })

    }

    override fun onLazyItemClick(data: ItemGroup) {
        val groupsController = GroupsController()
        val bundle = Bundle()
        bundle.putSerializable("item_group", data)
        groupsController.arguments = bundle

        show(TAB_PROFILE, groupsController)
    }

}