package leo.com.pumpyourself.controllers.meal

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutMealDeep1Binding

class MealDeep1Controller : BaseController<LayoutMealDeep1Binding>() {

  override lateinit var binding: LayoutMealDeep1Binding

  override fun initController() {
    binding.root.setOnClickListener { show(TAB_MEAL, MealDeep2Controller()) }
  }

  override fun getLayoutId(): Int = R.layout.layout_meal_deep1

  override fun getTitle(): String = "MealDeep1"

}