package leo.com.pumpyourself.controllers.meal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.Constants
import leo.com.pumpyourself.common.ImageGalleryEvent
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutAddMealBinding
import leo.com.pumpyourself.network.AddEatingRequest
import leo.com.pumpyourself.network.PumpYourSelfService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

class AddMealController : BaseController<LayoutAddMealBinding>() {

    override lateinit var binding: LayoutAddMealBinding

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1

        binding.ivMealIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }

            startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE)
        }

        binding.tvAddMeal.setOnClickListener {

            val currDate = Calendar.getInstance()
            val currDateStr = SimpleDateFormat("yyyy-MM-dd'T'HHmmss", Locale.ENGLISH).format(currDate.time)

            try {
                val weight = binding.etWeight.text.toString().toDouble()
                val proteins = binding.etProteins.text.toString().toDouble()
                val fats = binding.etFats.text.toString().toDouble()
                val carbs = binding.etCarb.text.toString().toDouble()
                val calories = binding.etCal.text.toString().toDouble()

                asyncSafe {
                    PumpYourSelfService.service.addEating(AddEatingRequest(userId, currDateStr,
                        binding.etFoodName.text.toString(), weight, null, proteins, fats,
                        carbs, calories)).await()

                    mainActivity.onBackPressed()
                }
            }
            catch (e : Exception) {
                Toast.makeText(context, "Incorrect number", Toast.LENGTH_LONG).show()
            }
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