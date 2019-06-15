package leo.com.pumpyourself.controllers.meal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.CameraEvent
import leo.com.pumpyourself.common.Constants
import leo.com.pumpyourself.common.encodeToBase64
import leo.com.pumpyourself.common.setCircleImgBitmap
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
    private var mealBitmap: Bitmap? = null

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1

        binding.ivMealIcon.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), Constants.MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST)
            }
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
                val photoBase64 = if (mealBitmap != null) encodeToBase64(mealBitmap!!, context!!) else null

                asyncSafe {
                    PumpYourSelfService.service.addEating(AddEatingRequest(userId, currDateStr,
                        binding.etFoodName.text.toString(), weight, photoBase64, proteins, fats,
                        carbs, calories)).await()

                    mainActivity.onBackPressed()
                }
            } catch (e: Exception) {
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
    fun onCameraEvent(event: CameraEvent) {
        binding.ivMealIcon.setCircleImgBitmap(event.bitmap)
        mealBitmap = event.bitmap
    }

    override fun getLayoutId(): Int = R.layout.layout_add_meal

    override fun getTitle(): String = "Add meal"
}