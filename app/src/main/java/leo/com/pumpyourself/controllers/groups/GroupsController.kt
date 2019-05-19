package leo.com.pumpyourself.controllers.groups

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.trainings.extras.GroupsAdapter
import leo.com.pumpyourself.controllers.trainings.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutGroupsBinding

class GroupsController : BaseController<LayoutGroupsBinding>(), LazyAdapter.OnItemClickListener<ItemGroup> {

  override lateinit var binding: LayoutGroupsBinding

  override fun initController() {

    val listData = listOf(
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run",""),
            ItemGroup("Group of four","Group for morning run","")
    )

    binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, GroupsAdapter(this), listData)

  }

  override fun onLazyItemClick(data: ItemGroup) {
    val editController = EditGroupController()
    val bundle = Bundle()
    bundle.putSerializable("item_group", data)
    editController.arguments = bundle

    show(TAB_GROUPS, editController)
  }

  override fun getLayoutId(): Int = R.layout.layout_groups

  override fun getTitle(): String = "Groups"

}