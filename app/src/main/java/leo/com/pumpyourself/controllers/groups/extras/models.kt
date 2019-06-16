package leo.com.pumpyourself.controllers.groups.extras

import java.io.Serializable

data class ItemGroup(
  val groupId: Int,
  val name: String,
  val description: String,
  val imgUrl: String
) : Serializable

data class ItemMember(
  val name: String,
  val imgUrl: String
)

data class ItemDayExercise(
  val name: String,
  val description: String
)

data class ItemFriend(
  val friendId: Int,
  val name: String,
  val status: String,
  val imgUrl: String
)