package leo.com.pumpyourself.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MealResponse(
    @SerializedName("User_dish_ID") val userDishId: Int,
    @SerializedName("Dish_ID") val dishId: Int,
    @SerializedName("Weight") val weight: Int,
    @SerializedName("Eating_date") val eatingDate: String,
    @SerializedName("Photo_ID") val photoId: String,
    @SerializedName("Dish_name") val dishName: String,
    @SerializedName("Proteins") val proteins: Double,
    @SerializedName("Fats") val fats: Double,
    @SerializedName("Carbohydrates") val carbohydrates: Double,
    @SerializedName("Calories") val calories: Double
)

data class MealRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Int
)

data class ProfileGetUserResponse(
    @SerializedName("User_name") val userName: String,
    @SerializedName("User_status") val userStatus: String,
    @SerializedName("Friends_requests") val friendsRequests: List<FriendsRequest>,
    @SerializedName("Groups_requests") val groupsRequests: List<GroupsRequest>,
    @SerializedName("Friends") val friends: List<Friend>
) : Serializable

data class FriendsRequest(
    @SerializedName("Friend_ID") val friendId: String,
    @SerializedName("User_name") val userName: String,
    @SerializedName("User_status") val userStatus: String
) : Serializable

data class GroupsRequest(
    @SerializedName("Group_ID") val groupId: String,
    @SerializedName("Group_name") val groupName: String,
    @SerializedName("Group_description") val groupDescription: String
) : Serializable

data class Friend(
    @SerializedName("Friend_ID") val friendId: String,
    @SerializedName("User_name") val userName: String,
    @SerializedName("User_status") val userStatus: String
) : Serializable

