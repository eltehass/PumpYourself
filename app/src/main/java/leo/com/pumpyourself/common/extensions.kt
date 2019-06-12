package leo.com.pumpyourself.common

import android.widget.ImageView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setCircleImgUrl(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}


fun ImageView.setImgUrl(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}


fun ImageView.setCircleImgResource(imgResource: Int) {
    Glide.with(context)
        .load(imgResource)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun AnyChartView.initWithValues(value1: Int, value2: Int, value3: Int, value4: Int, title: String) {
    val cartesian = AnyChart.column()

    val data = listOf(
            ValueDataEntry("Proteins", value1),
            ValueDataEntry("Fats", value2),
            ValueDataEntry("Carbs", value3),
            ValueDataEntry("Calories", value4)
    )

    val column = cartesian.column(data)

    column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}%")

    cartesian.animation(true)
    cartesian.title(title)

    cartesian.yScale().minimum(0.0)

    cartesian.yAxis(0).labels().format("{%Value}%")

    cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
    cartesian.interactivity().hoverMode(HoverMode.BY_X)

    setChart(cartesian)
}