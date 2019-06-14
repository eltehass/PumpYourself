package leo.com.pumpyourself.controllers.meal

import android.app.DatePickerDialog
import android.os.Bundle
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
import leo.com.pumpyourself.network.PumpYourSelfService
import java.text.SimpleDateFormat
import java.util.*

class MealController : BaseController<LayoutMealBinding>(), LazyAdapter.OnItemClickListener<ItemMeal> {

  override lateinit var binding: LayoutMealBinding

  var year: Int = -1
  var month: Int = -1
  var day: Int = -1

  // TODO: Get user id
  var userId = 1

  override fun initController() {

      val controllerThis = this

      val currDate = Calendar.getInstance()
      val currDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currDate.time)
      val currDateFormatted = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(currDate.time)

      currDate.add(Calendar.DAY_OF_MONTH, 1)
      val tomorrowDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currDate.time)

      asyncSafe {

          val networkResult = PumpYourSelfService.service.getAllFood(
              userId, currDateStr, tomorrowDateStr).await()

          var proteins = 0.0
          var fats = 0.0
          var carbs = 0.0
          var calories = 0.0

          networkResult.forEach {
              proteins += it.proteins
              fats += it.fats
              carbs += it.carbohydrates
              calories += it.calories
          }

          val mealUnits = listOf(
              ItemMealUnit("Proteins", "$proteins g"),
              ItemMealUnit("Fats","$fats g"),
              ItemMealUnit("Carbs","$carbs g"),
              ItemMealUnit("Calories","$calories kcal")
          )

          val meals = networkResult.map { ItemMeal(it.userDishId, it.dishName, it.weight,
              "http://upe.pl.ua:8080/images/dishes?image_id=${it.photoId}",
              it.proteins, it.fats, it.carbohydrates, it.calories) }

          binding.acvChart.initWithValues(proteins.toInt(), fats.toInt(),
              carbs.toInt(), calories.toInt(),"Consumed stuff")
          binding.rvMealUnits.initWithGridLay(2, MealUnitAdapter(), mealUnits)
          binding.rvMeals.initWithLinLay(LinearLayoutManager.VERTICAL, MealAdapter(controllerThis), meals)


      }

      //binding.cv2.setOnClickListener { show(TAB_MEAL, MealStatisticsController()) }

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

      binding.tvAddMeal.setOnClickListener {
          val addMealController = AddMealController()
          val bundle = Bundle()
          bundle.putSerializable("user_id", userId)
          addMealController.arguments = bundle

          show(TAB_MEAL, addMealController)
      }
      binding.tvShowHistory.setOnClickListener {
          val historyController = MealHistoryController()
          val bundle = Bundle()
          bundle.putSerializable("user_id", userId)
          historyController.arguments = bundle

          show(TAB_MEAL, historyController)
      }

      binding.tvCalendarDate.text = currDateFormatted
  }

  override fun onLazyItemClick(data: ItemMeal) {
      val editMealController = EditMealController()
      val bundle = Bundle()
      bundle.putSerializable("user_id", userId)
      bundle.putSerializable("item_meal", data)
      editMealController.arguments = bundle

      show(TAB_MEAL, editMealController)
  }

  override fun getLayoutId(): Int = R.layout.layout_meal

  override fun getTitle(): String = "Meal"

}