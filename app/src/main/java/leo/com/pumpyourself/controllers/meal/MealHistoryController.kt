package leo.com.pumpyourself.controllers.meal

import android.support.v7.widget.LinearLayoutManager
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.meal.extras.ItemMeal
import leo.com.pumpyourself.controllers.meal.extras.ItemMealHistory
import leo.com.pumpyourself.controllers.meal.extras.MealHistoryAdapter
import leo.com.pumpyourself.databinding.LayoutMealHistoryBinding

class MealHistoryController : BaseController<LayoutMealHistoryBinding>() {

    override lateinit var binding: LayoutMealHistoryBinding

    override fun initController() {
        val meals = listOf(
                ItemMeal("Buckwheat","110 g",""),
                ItemMeal("Buckwheat","110 g",""),
                ItemMeal("Buckwheat","110 g",""),
                ItemMeal("Buckwheat","110 g","")
        )

        val mealHistories = listOf(
                ItemMealHistory("Meal for 07.04.2019", meals),
                ItemMealHistory("Meal for 07.04.2019", meals),
                ItemMealHistory("Meal for 07.04.2019", meals),
                ItemMealHistory("Meal for 07.04.2019", meals)
        )

        binding.rvMealHistories.initWithLinLay(LinearLayoutManager.VERTICAL, MealHistoryAdapter(), mealHistories)
    }

    override fun getLayoutId(): Int = R.layout.layout_meal_history

    override fun getTitle(): String = "Meal history"
}