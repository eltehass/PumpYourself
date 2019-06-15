package leo.com.pumpyourself.controllers.trainings.extras

import leo.com.pumpyourself.controllers.groups.extras.ItemDayExercise
import java.io.Serializable

data class ItemTraining(
  val trainingId: Int,
  val title: String,
  val description: String,
  val day: String,
  val days: List<ItemDayExercise>
) : Serializable