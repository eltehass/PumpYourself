package leo.com.pumpyourself.controllers.groups

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController

class GroupsController : BaseController() {

  override fun getLayoutId(): Int = R.layout.layout_groups

  override fun getTitle(): String = "Groups"

}