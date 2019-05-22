package leo.com.pumpyourself.controllers.meal

import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.initWithValues
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutMealStatisticsBinding

class MealStatisticsController : BaseController<LayoutMealStatisticsBinding>() {

    override lateinit var binding: LayoutMealStatisticsBinding

    override fun initController() {
        binding.tvProteins1Value.text = "180 g"
        binding.tvProteins2Value.text = "180 g"
        binding.tvProteins3Value.text = "180 g"
        binding.tvProteins4Value.text = "180 g"

        binding.tvFats1Value.text = "180 g"
        binding.tvFats2Value.text = "180 g"
        binding.tvFats3Value.text = "180 g"
        binding.tvFats4Value.text = "180 g"

        binding.tvCarbs1Value.text = "180 g"
        binding.tvCarbs2Value.text = "180 g"
        binding.tvCarbs3Value.text = "180 g"
        binding.tvCarbs4Value.text = "180 g"

        binding.tvCal1Value.text = "560 kcal"
        binding.tvCal2Value.text = "560 kcal"
        binding.tvCal3Value.text = "560 kcal"
        binding.tvCal4Value.text = "560 kcal"

        binding.acvChart.initWithValues(145, 30, 95, 60,"Stuff for today")
    }

    override fun getLayoutId(): Int = R.layout.layout_meal_statistics

    override fun getTitle(): String  = "Meal statistics"
}