package leo.com.pumpyourself.controllers.meal

import android.os.Bundle
import android.view.View
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.BaseController
import leo.com.pumpyourself.databinding.LayoutMealBinding

class MealController : BaseController<LayoutMealBinding>() {

  val titleText = "Sample text for binding"

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setOnClickListener { show(TAB_MEAL, MealDeep1Controller()) }
  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

  override fun setDataForView(binding: LayoutMealBinding) { binding.controller = this }

}