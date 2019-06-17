package leo.com.pumpyourself.controllers.meal

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.*
import leo.com.pumpyourself.common.Constants.CAMERA_REQUEST
import leo.com.pumpyourself.common.Constants.MY_CAMERA_PERMISSION_CODE
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.meal.extras.ItemMeal
import leo.com.pumpyourself.databinding.LayoutEditMealBinding
import leo.com.pumpyourself.network.EditEatingRequest
import leo.com.pumpyourself.network.PumpYourSelfService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditMealController : BaseController<LayoutEditMealBinding>() {

    override lateinit var binding: LayoutEditMealBinding
    private var mealBitmap: Bitmap? = null

    override fun initController() {

        val userId = arguments?.get("user_id") as Int? ?: 1
        val itemMeal = arguments?.get("item_meal") as ItemMeal? ?: ItemMeal(
            0, "", 0.0, "", 1.0, 2.0, 3.0, 4.0)

        binding.etFoodName.setText(itemMeal.name)
        binding.etWeight.setText(itemMeal.weight.toString())
        binding.etProteins.setText(itemMeal.proteins.toString())
        binding.etFats.setText(itemMeal.fats.toString())
        binding.etCarb.setText(itemMeal.carbs.toString())
        binding.etCal.setText(itemMeal.calories.toString())

        if (itemMeal.imgUrl.isNotEmpty()) {
            binding.ivMealIcon.setCircleImgUrl(itemMeal.imgUrl)
        } else {
            binding.ivMealIcon.setCircleImgResource(R.drawable.ic_launcher_background)
        }

        binding.ivMealIcon.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }

        binding.tvRemove.setOnClickListener {
            asyncSafe {
                PumpYourSelfService.service.deleteEating(itemMeal.userDishId).await()

                mainActivity.onBackPressed()
            }
        }

        binding.tvSaveMeal.setOnClickListener {

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
                    PumpYourSelfService.service.editEating(EditEatingRequest(userId, itemMeal.userDishId,
                        currDateStr, binding.etFoodName.text.toString(), weight, photoBase64, proteins, fats,
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

//    @Subscribe
//    fun onImageGalleryEvent(event: ImageGalleryEvent) {
//        Log.e("aaaa", event.uri.toString())
//
//        binding.ivMealIcon.setImageURI(event.uri)
//
//        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2121)
//            }
//        } else {
//            //val base64 = encodeToBase64(event.uri, context!!)
//            //val addMealObj = AddEatingRequest(5, "2019-06-09T201546", "efasfs", 232, base64, 123, 123, 213, 123)
//
// //            asyncSafe {
// //                val res = PumpYourSelfService.service.addEating(addMealObj).await()
// //                Log.e("EditMealController", "Result: $res")
// //            }
//        }
//    }

    fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context!!.contentResolver.query(uri, projection, null, null, null) ?: return null
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(columnIndex)
        cursor.close()
        return s
    }

    private fun getImageRealPath(contentResolver: ContentResolver, uri: Uri, whereClause: String?): String {
        var ret = ""

        // Query the uri with condition.
        val cursor = contentResolver.query(uri, null, whereClause, null, null)

        if (cursor != null) {
            val moveToFirst = cursor.moveToFirst()
            if (moveToFirst) {

                // Get columns name by uri type.
                var columnName = MediaStore.Images.Media.DATA

                if (uri === MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA
                } else if (uri === MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA
                } else if (uri === MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA
                }

                // Get column index.
                val imageColumnIndex = cursor.getColumnIndex(columnName)

                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex)
            }
        }

        return ret
    }

    // Some Utils for request
    fun fileToPart(uri: Uri): MultipartBody.Part {
        val file = File(getPath(uri))
        return MultipartBody.Part.createFormData(
                "photo",
                file.name,
                RequestBody.create(MediaType.parse("image/*"), file)
        )
    }

    @Subscribe
    fun onCameraEvent(event: CameraEvent) {
        binding.ivMealIcon.setCircleImgBitmap(event.bitmap)
        mealBitmap = event.bitmap
    }

    override fun getLayoutId(): Int = R.layout.layout_edit_meal

    override fun getTitle(): String = "Edit meal"
}