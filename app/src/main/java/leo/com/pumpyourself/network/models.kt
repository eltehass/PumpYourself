package leo.com.pumpyourself.network

import com.google.gson.annotations.SerializedName

data class Meal(
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