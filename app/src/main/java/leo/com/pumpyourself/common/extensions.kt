package leo.com.pumpyourself.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setCircleImgUrl(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}


fun ImageView.setCircleImgResource(imgResource: Int) {
    Glide.with(context)
        .load(imgResource)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}