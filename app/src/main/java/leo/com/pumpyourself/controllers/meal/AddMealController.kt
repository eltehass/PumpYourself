package leo.com.pumpyourself.controllers.meal

import android.content.Intent
import android.os.Bundle
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.Constants
import leo.com.pumpyourself.common.ImageGalleryEvent
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutAddMealBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddMealController : BaseController<LayoutAddMealBinding>() {

    override lateinit var binding: LayoutAddMealBinding

    override fun initController() {

        binding.ivMealIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }

            startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @Subscribe
    fun onImageGalleryEvent(event: ImageGalleryEvent) {
        binding.ivMealIcon.setImageURI(event.uri)
    }

    override fun getLayoutId(): Int = R.layout.layout_add_meal

    override fun getTitle(): String = "Add meal"

}