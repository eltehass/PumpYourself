package leo.com.pumpyourself.controllers.profile

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutProfileAddFriendBinding

class ProfileAddFriendController : BaseController<LayoutProfileAddFriendBinding>() {

    override lateinit var binding: LayoutProfileAddFriendBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_add_friend

    override fun getTitle(): String = "Add a friend"
}