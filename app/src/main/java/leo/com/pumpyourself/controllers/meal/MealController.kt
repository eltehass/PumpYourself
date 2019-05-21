package leo.com.pumpyourself.controllers.meal

import android.app.DatePickerDialog
import android.support.v7.widget.LinearLayoutManager
import leo.com.pumpyourself.R
import leo.com.pumpyourself.common.initWithValues
import leo.com.pumpyourself.controllers.base.BaseController
import leo.com.pumpyourself.controllers.base.recycler.LazyAdapter
import leo.com.pumpyourself.controllers.base.recycler.initWithGridLay
import leo.com.pumpyourself.controllers.base.recycler.initWithLinLay
import leo.com.pumpyourself.controllers.meal.extras.ItemMeal
import leo.com.pumpyourself.controllers.meal.extras.ItemMealUnit
import leo.com.pumpyourself.controllers.meal.extras.MealAdapter
import leo.com.pumpyourself.controllers.meal.extras.MealUnitAdapter
import leo.com.pumpyourself.databinding.LayoutMealBinding
import java.text.SimpleDateFormat
import java.util.*

class MealController : BaseController<LayoutMealBinding>(), LazyAdapter.OnItemClickListener<ItemMeal> {

  override lateinit var binding: LayoutMealBinding

  var year: Int = -1
  var month: Int = -1
  var day: Int = -1

  override fun initController() {
      val mealUnits = listOf(
        ItemMealUnit("Proteins","240 g"),
        ItemMealUnit("Fats","175 g"),
        ItemMealUnit("Carbs","320 g"),
        ItemMealUnit("Calories","615 kcal")
      )

      val meals = listOf(
        ItemMeal("Buckwheat","110 g",""),
        ItemMeal("Buckwheat","110 g",""),
        ItemMeal("Buckwheat","110 g",""),
        ItemMeal("Buckwheat","110 g","")
      )

      binding.tvAddMeal.setOnClickListener { show(TAB_MEAL, AddMealController()) }

      binding.tvCalendarDate.text = "April 7, 2017"
      binding.acvChart.initWithValues(145, 30, 95, 60)
      binding.rvMealUnits.initWithGridLay(2, MealUnitAdapter(), mealUnits)
      binding.rvMeals.initWithLinLay(LinearLayoutManager.VERTICAL, MealAdapter(this), meals)

      binding.ivCalendar.setOnClickListener {
          if (year == -1) {
              val calendar = Calendar.getInstance()
              year = calendar.get(Calendar.YEAR)
              month = calendar.get(Calendar.MONTH)
              day = calendar.get(Calendar.DAY_OF_MONTH)
          }

          val datePickerDialog = DatePickerDialog(context!!,
              DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                  val calendar = Calendar.getInstance()
                  calendar.set(year, monthOfYear, dayOfMonth)

                  val formattedDate = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(calendar.time)
                  binding.tvCalendarDate.text = formattedDate
              }, year, month, day
          )

          datePickerDialog.show()
      }
  }

  override fun onLazyItemClick(data: ItemMeal) {
      show(TAB_MEAL, EditMealController())
  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}