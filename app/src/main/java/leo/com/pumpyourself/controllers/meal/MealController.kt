package leo.com.pumpyourself.controllers.meal

import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.initWithValues
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithGridLay
import leo.com.pumpyourself.controllers.meal.extras.ItemMealUnit
import leo.com.pumpyourself.controllers.meal.extras.MealUnitAdapter
import leo.com.pumpyourself.databinding.LayoutMealBinding

class MealController : BaseController<LayoutMealBinding>() {

  override lateinit var binding: LayoutMealBinding

  override fun initController() {
      val mealUnits = listOf(
        ItemMealUnit("Proteins","240 g"),
        ItemMealUnit("Fats","175 g"),
        ItemMealUnit("Carbs","320 g"),
        ItemMealUnit("Calories","615 kcal")
      )

      binding.tvCalendarDate.text = "April 7, 2017"
      binding.acvChart.initWithValues(145, 30, 95, 60)
      binding.rvMealUnits.initWithGridLay(2, MealUnitAdapter(), mealUnits)
  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}