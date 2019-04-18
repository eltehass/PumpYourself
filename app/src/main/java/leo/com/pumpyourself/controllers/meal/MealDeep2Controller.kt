package leo.com.pumpyourself.controllers.meal

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController
import leo.com.pumpyourself.databinding.LayoutMealDeep2Binding

class MealDeep2Controller : BaseController<LayoutMealDeep2Binding>() {

  override fun getLayoutId(): Int = R.layout.layout_meal_deep2

  override fun getTitle(): String = "MealDeep2"

  override fun setDataForView(binding: LayoutMealDeep2Binding) { binding.controller = this }

}