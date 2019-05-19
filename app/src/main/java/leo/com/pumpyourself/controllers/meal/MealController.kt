package leo.com.pumpyourself.controllers.meal

import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.setCircleImgResource
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.databinding.LayoutMealBinding

class MealController : BaseController<LayoutMealBinding>() {

  override lateinit var binding: LayoutMealBinding

  override fun initController() {

    binding.root.setOnClickListener { show(TAB_MEAL, MealDeep1Controller()) }

    asyncSafe {

      binding.imageView.setCircleImgResource(R.drawable.ic_launcher_background)
      //val meals = PumpYourSelfService.service.getAllFood(1, "2019-05-07", "2019-05-22").await()
      //binding.textView.text = meals[0].dishName
    }

  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}