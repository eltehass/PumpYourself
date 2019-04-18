package leo.com.pumpyourself.controllers.profile

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController
import leo.com.pumpyourself.databinding.LayoutProfileBinding

class ProfileController : BaseController<LayoutProfileBinding>() {

  override fun getLayoutId(): Int = R.layout.layout_profile

  override fun getTitle(): String = "Profile"

  override fun setDataForView(binding: LayoutProfileBinding) { binding.controller = this }

}