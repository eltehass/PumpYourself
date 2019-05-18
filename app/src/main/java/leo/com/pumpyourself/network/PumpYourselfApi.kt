package leo.com.pumpyourself.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface PumpYourselfApi {

    @GET("meal/getallfood")
    fun getAllFood(
        @Query("user_id") userId: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String) : Deferred<List<Meal>>

}

