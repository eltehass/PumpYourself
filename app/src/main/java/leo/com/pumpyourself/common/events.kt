package leo.com.pumpyourself.common

import android.graphics.Bitmap
import android.net.Uri
import java.io.Serializable

data class ImageGalleryEvent(val uri: Uri) : Serializable

data class CameraEvent(val bitmap: Bitmap) : Serializable