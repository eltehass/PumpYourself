package leo.com.pumpyourself.network

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PumpYourselfApi {

    @GET("meal/getallfood")
    fun getAllFood(
        @Query("user_id") userId: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String) : Deferred<List<MealResponse>>

    @POST("meal/addeating")
    fun addEating(@Body meal: MealRequest): Deferred<Int>

    @GET("profile/getprofileinfo")
    fun getProfileInfo(
        @Query("user_id") userId: Int
    ) : Deferred<ProfileGetUserResponse>
}

