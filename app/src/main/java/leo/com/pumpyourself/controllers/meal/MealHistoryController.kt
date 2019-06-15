package leo.com.pumpyourself.controllers.meal

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import leo.com.pumpyourself.R
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.meal.extras.ItemMeal
import leo.com.pumpyourself.controllers.meal.extras.ItemMealHistory
import leo.com.pumpyourself.controllers.meal.extras.MealHistoryAdapter
import leo.com.pumpyourself.databinding.LayoutMealHistoryBinding
import leo.com.pumpyourself.network.PumpYourSelfService
import java.text.SimpleDateFormat
import java.util.*

class MealHistoryController : BaseController<LayoutMealHistoryBinding>() {

    override lateinit var binding: LayoutMealHistoryBinding

    var userId = 1

    override fun initController() {

        userId = arguments?.get("user_id") as Int? ?: 1

        val currDate = Calendar.getInstance()

        currDate.add(Calendar.DAY_OF_MONTH, 1)
        val tomorrowDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currDate.time)

        currDate.add(Calendar.YEAR, -1)
        val oldDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currDate.time)

        asyncSafe {

            val networkResult = PumpYourSelfService.service.getAllFood(
                userId, oldDateStr, tomorrowDateStr
            ).await()

            val mealByDays = networkResult.groupBy {
                it.eatingDate.substring(0, 10)
            }

            val mealHistories = mealByDays.map {
                ItemMealHistory(
                    "Meal for " + it.key,
                    it.value.map { inner ->
                        ItemMeal(inner.userDishId, inner.dishName, inner.weight,
                            "http://upe.pl.ua:8080/images/dishes?image_id=${inner.photoId}",
                            inner.proteins, inner.fats, inner.carbohydrates, inner.calories)
                    }
                )
            }


            val itemMealClickListener = object : LazyAdapter.OnItemClickListener<ItemMeal> {
                override fun onLazyItemClick(data: ItemMeal) {
                    onItemMealClick(data)
                }
            }

            val mealHistoryAdapter = MealHistoryAdapter(itemMealClickListener)

            binding.rvMealHistories.initWithLinLay(LinearLayoutManager.VERTICAL, mealHistoryAdapter, mealHistories.sortedByDescending { it.date } )
        }
    }

    private fun onItemMealClick(meal: ItemMeal) {
        val editMealController = EditMealController()
        val bundle = Bundle().apply {
            putSerializable("user_id", userId)
            putSerializable("item_meal", meal)
        }

        editMealController.arguments = bundle

        show(TAB_MEAL, editMealController)
    }

    override fun getLayoutId(): Int = R.layout.layout_meal_history

    override fun getTitle(): String = "Meal history"
}