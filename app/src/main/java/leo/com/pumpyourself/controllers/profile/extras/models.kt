package leo.com.pumpyourself.controllers.profile.extras

import java.io.Serializable

data class ItemFriend(
    val userId: Int,
    val friendId: Int,
    val name: String,
    val status: String,
    val imgUrl: String
) : Serializable

data class ItemGroup(
    val userId: Int,
    val groupId: Int,
    val name: String,
    val description: String,
    val imgUrl: String
) : Serializable

data class ItemAddFriend(
    val friendId: Int,
    val name: String,
    val imgUrl: String
) : Serializable
