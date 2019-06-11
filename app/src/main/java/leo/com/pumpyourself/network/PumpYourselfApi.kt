package leo.com.pumpyourself.network

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface PumpYourselfApi {

    /** Meals **/
    @GET("meal/getallfood")
    fun getAllFood(
        @Query("user_id") userId: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String) : Deferred<List<FoodResponse>>

    @POST("meal/addeating")
    fun addEating(@Body meal: AddEatingRequest): Deferred<Int>

    @POST("meal/editeating")
    fun editEating(@Body meal: EditEatingRequest): Deferred<Unit>

    @POST("meal/deleteeating")
    fun deleteEating(@Body user_dish_id: Int): Deferred<Int>


    /** Trainings **/
    @GET("trainings/getallusertrainings")
    fun getAllUserTrainings(@Query("user_id") userId: Int) : Deferred<List<UserTrainingResponse>>

    @GET("trainings/getallpublictrainings")
    fun getAllPublicTrainings() : Deferred<List<PublicTrainingResponse>>

    @POST("trainings/createtraining")
    fun createTraining(@Body training: CreateTrainingRequest) : Deferred<Int>

    @POST("trainings/starttraining")
    fun startTraining(@Body training: StartTrainingRequest) : Deferred<Unit>

    @POST("trainings/stoptraining")
    fun stopTraining(@Body training: StopTrainingRequest) : Deferred<Unit>

    /** Groups **/
    @GET("/groups/getallusergroups")
    fun getAllUserGroups(@Query("user_id") userId: Int) : Deferred<List<UserGroupResponse>>

    @GET("/groups/getmoregroupinfo")
    fun getMoreGroupInfo(@Query("group_id") groupId: Int) : Deferred<MoreDetailedGroupInfoResponse>

    @POST("groups/addgroup")
    fun addGroup(@Body group: AddGroupRequest): Deferred<Int>

    @POST("groups/editgroup")
    fun editGroup(@Body group: EditGroupRequest): Deferred<Unit>

    @POST("groups/invitefriendintogroup")
    fun inviteFriendIntoGroup(@Body inviting: InviteFriendInGroupRequest): Deferred<Unit>

    @POST("groups/leavethegroup")
    fun leaveTheGroup(@Body leaveGroupRequest: LeaveGroupRequest): Deferred<Unit>

}