package leo.com.pumpyourself.controllers.profile

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController

class ProfileController : BaseController() {

    override fun getLayoutId(): Int = R.layout.layout_profile

    override fun getTitle(): String = "Profile"

}