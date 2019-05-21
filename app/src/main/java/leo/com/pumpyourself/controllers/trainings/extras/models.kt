package leo.com.pumpyourself.controllers.trainings.extras

import java.io.Serializable

data class ItemTraining(
  val title: String,
  val description: String,
  val day: String
) : Serializable

data class ItemTrainingNew(
  val title: String,
  val description: String
) : Serializable