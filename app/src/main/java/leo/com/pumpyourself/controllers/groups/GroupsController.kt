package leo.com.pumpyourself.controllers.groups

import android.os.Bundle
import android.widget.LinearLayout
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.groups.extras.GroupsAdapter
import leo.com.pumpyourself.controllers.groups.extras.ItemGroup
import leo.com.pumpyourself.databinding.LayoutGroupsBinding
import leo.com.pumpyourself.network.PumpYourSelfService

class GroupsController : BaseController<LayoutGroupsBinding>(), LazyAdapter.OnItemClickListener<ItemGroup> {

  override lateinit var binding: LayoutGroupsBinding

  // TODO: Get user id
  var userId = 1

  override fun initController() {

      val controllerThis = this

      asyncSafe {
          val networkResult = PumpYourSelfService.service.getAllUserGroups(userId).await()

          val dataList = networkResult.map {
              ItemGroup(it.groupId, it.groupName, it.groupDescription,
                  "http://upe.pl.ua:8080/images/groups?image_id=${it.groupId}")
          }

          binding.rvContainer.initWithLinLay(LinearLayout.VERTICAL, GroupsAdapter(controllerThis), dataList)

          binding.fabAction.setOnClickListener {
              val createGroupController = CreateGroupController()
              val bundle = Bundle()
              bundle.putSerializable("user_id", userId)
              createGroupController.arguments = bundle
              show(TAB_GROUPS, createGroupController)
          }
      }
  }

  override fun onLazyItemClick(data: ItemGroup) {
    val editController = EditGroupController()
    val bundle = Bundle()
    bundle.putSerializable("user_id", userId)
    bundle.putSerializable("item_group", data)
    editController.arguments = bundle

    show(TAB_GROUPS, editController)
  }

  override fun getLayoutId(): Int = R.layout.layout_groups

  override fun getTitle(): String = "Groups"
}