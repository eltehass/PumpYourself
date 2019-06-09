package leo.com.pumpyourself.network

import com.google.gson.annotations.SerializedName

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