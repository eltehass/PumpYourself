package leo.com.pumpyourself.controllers.meal

import android.os.Bundle
import android.view.View
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController
import leo.com.pumpyourself.databinding.LayoutMealDeep1Binding

class MealDeep1Controller : BaseController<LayoutMealDeep1Binding>() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setOnClickListener { show(TAB_MEAL, MealDeep2Controller()) }
  }

  override fun getLayoutId(): Int = R.layout.layout_meal_deep1

  override fun getTitle(): String = "MealDeep1"

  override fun setDataForView(binding: LayoutMealDeep1Binding) { binding.controller = this }

}