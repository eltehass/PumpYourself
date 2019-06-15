package leo.com.pumpyourself.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodResponse(
    @SerializedName("User_dish_ID") val userDishId: Int,
    @SerializedName("Dish_ID") val dishId: Int,
    @SerializedName("Weight") val weight: Double,
    @SerializedName("Eating_date") val eatingDate: String,
    @SerializedName("Photo_ID") val photoId: String,
    @SerializedName("Dish_name") val dishName: String,
    @SerializedName("Proteins") val proteins: Double,
    @SerializedName("Fats") val fats: Double,
    @SerializedName("Carbohydrates") val carbohydrates: Double,
    @SerializedName("Calories") val calories: Double
) : Serializable

data class AddEatingRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("photo") val photo: String?,
    @SerializedName("proteins") val proteins: Double,
    @SerializedName("fats") val fats: Double,
    @SerializedName("carbohydrates") val carbohydrates: Double,
    @SerializedName("calories") val calories: Double
) : Serializable

data class EditEatingRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("user_dish_id") val userDishId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("photo") val photo: String?,
    @SerializedName("proteins") val proteins: Double,
    @SerializedName("fats") val fats: Double,
    @SerializedName("carbohydrates") val carbohydrates: Double,
    @SerializedName("calories") val calories: Double
) : Serializable

data class UserTrainingResponse(
    @SerializedName("Training_ID") val trainingID: Int,
    @SerializedName("Training_name") val trainingName: String,
    @SerializedName("Training_description") val trainingDescription: String,
    @SerializedName("Start_date") val startDate: String,
    @SerializedName("Day_number") val dayNumber: Int,
    @SerializedName("Day_plan") val dayPlan: String
) : Serializable

data class PublicTrainingResponse(
    @SerializedName("Training_ID") val trainingID: Int,
    @SerializedName("Training_name") val trainingName: String,
    @SerializedName("Training_description") val trainingDescription: String,
    @SerializedName("Day_number") val dayNumber: Int,
    @SerializedName("Day_plan") val dayPlan: String
) : Serializable

data class CreateTrainingRequest(
    @SerializedName("training_name") val trainingName: String,
    @SerializedName("training_description") val trainingDescription: String,
    @SerializedName("day_number") val dayNumber: Int,
    @SerializedName("day_plan") val dayPlan: String
) : Serializable

data class StartTrainingRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("training_id") val trainingId: Int,
    @SerializedName("date") val date: String
) : Serializable

data class StopTrainingRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("training_id") val trainingId: Int
) : Serializable

data class UserGroupResponse(
    @SerializedName("Group_ID") val groupId: Int,
    @SerializedName("Group_name") val groupName: String,
    @SerializedName("Group_description") val groupDescription: String
) : Serializable

data class MoreDetailedGroupInfoResponse(
    @SerializedName("Members") val members: List<Member>,
    @SerializedName("Training") val trainings: List<Training>
) : Serializable

data class Member(
    @SerializedName("User_ID") val userId: Int,
    @SerializedName("User_name") val userName: String
) : Serializable

data class Training(
    @SerializedName("Training_ID") val trainingID: Int,
    @SerializedName("Start_date") val startDate: String,
    @SerializedName("Day_number") val dayNumber: Int,
    @SerializedName("Day_plan") val dayPlan: String
) : Serializable

data class AddGroupRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("group_name") val groupName: String,
    @SerializedName("description") val description: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("training_id") val trainingId: Int,
    @SerializedName("start_date") val startDate: String
) : Serializable

data class EditGroupRequest(
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("photo") val photo: String
) : Serializable

data class InviteFriendInGroupRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("friend_id") val friendId: Int,
    @SerializedName("group_id") val groupId: Int
) : Serializable

data class LeaveGroupRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("group_id") val groupId: Int
) : Serializable

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

data class ProfileFriendInfo(
    @SerializedName("Friends") val friends: List<Friend>,
    @SerializedName("Mutual_groups") val mutualGroups: List<UserGroupResponse>
) : Serializable

data class UserInfo(
    @SerializedName("User_ID") val userId: Int,
    @SerializedName("User_name") val userName: String,
    @SerializedName("User_status") val userStatus: String
) : Serializable

data class ChangeUserInfo(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("name") val userName: String,
    @SerializedName("status") val userStatus: String
) : Serializable

data class ProcessGroupRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("group_id") val groupId: Int
) : Serializable

data class ProcessFriendRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("friend_id") val userName: Int
) : Serializable
