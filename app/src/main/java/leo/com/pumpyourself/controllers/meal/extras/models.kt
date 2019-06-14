package leo.com.pumpyourself.controllers.meal.extras

import java.io.Serializable

data class ItemMealUnit(
  val name: String,
  val value: String
) : Serializable

data class ItemMeal(
  val userDishId: Int,
  val name: String,
  val weight: Double,
  val imgUrl: String,
  var proteins : Double,
  var fats : Double,
  var carbs : Double,
  var calories : Double
) : Serializable

data class ItemMealHistory(
  val date: String,
  val meals: List<ItemMeal>
) : Serializable