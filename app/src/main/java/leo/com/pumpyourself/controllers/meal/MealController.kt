package leo.com.pumpyourself.controllers.meal

import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutMealBinding
import leo.com.pumpyourself.network.PumpYourSelfService

class MealController : BaseController<LayoutMealBinding>() {

  override lateinit var binding: LayoutMealBinding

  override fun initController() {

    binding.root.setOnClickListener { show(TAB_MEAL, MealDeep1Controller()) }

    asyncSafe {
      val meals = PumpYourSelfService.service.getAllFood(1, "2019-05-07", "2019-05-22").await()
      binding.textView.text = meals[0].dishName
    }

  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}