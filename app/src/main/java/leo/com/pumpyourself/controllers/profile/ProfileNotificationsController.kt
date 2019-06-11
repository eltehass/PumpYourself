package leo.com.pumpyourself.controllers.profile

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutProfileNotificationsBinding

class ProfileNotificationsController : BaseController<LayoutProfileNotificationsBinding>() {

    override lateinit var binding: LayoutProfileNotificationsBinding

    override fun getLayoutId(): Int = R.layout.layout_profile_notifications

    override fun getTitle(): String = "Notifications"
}