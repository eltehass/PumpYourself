package leo.com.pumpyourself.controllers.groups

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutGroupsBinding

class GroupsController : BaseController<LayoutGroupsBinding>() {

  override lateinit var binding: LayoutGroupsBinding

  override fun getLayoutId(): Int = R.layout.layout_groups

  override fun getTitle(): String = "Groups"

}