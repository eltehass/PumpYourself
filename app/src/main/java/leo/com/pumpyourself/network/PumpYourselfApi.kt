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
        @Query("end_date") endDate: String
    ): Deferred<List<FoodResponse>>

    @POST("meal/addeating")
    fun addEating(@Body meal: AddEatingRequest): Deferred<Int>

    @POST("meal/editeating")
    fun editEating(@Body meal: EditEatingRequest): Deferred<Unit>

    @POST("meal/deleteeating")
    fun deleteEating(@Body meal: DeleteEatingRequest): Deferred<Unit>

    /** Trainings **/
    @GET("trainings/getallusertrainings")
    fun getAllUserTrainings(@Query("user_id") userId: Int): Deferred<List<UserTrainingResponse>>

    @GET("trainings/getallpublictrainings")
    fun getAllPublicTrainings(): Deferred<List<PublicTrainingResponse>>

    @POST("trainings/createtraining")
    fun createTraining(@Body training: List<CreateTrainingRequest>): Deferred<Int>

    @POST("trainings/starttraining")
    fun startTraining(@Body training: StartTrainingRequest): Deferred<Unit>

    @POST("trainings/stoptraining")
    fun stopTraining(@Body training: StopTrainingRequest): Deferred<Unit>

    /** Groups **/
    @GET("/groups/getallusergroups")
    fun getAllUserGroups(@Query("user_id") userId: Int): Deferred<List<UserGroupResponse>>

    @GET("/groups/getmoregroupinfo")
    fun getMoreGroupInfo(@Query("group_id") groupId: Int): Deferred<MoreDetailedGroupInfoResponse>

    @POST("groups/addgroup")
    fun addGroup(@Body group: AddGroupRequest): Deferred<Int>

    @POST("groups/editgroup")
    fun editGroup(@Body group: EditGroupRequest): Deferred<Unit>

    @POST("groups/invitefriendintogroup")
    fun inviteFriendIntoGroup(@Body inviting: InviteFriendInGroupRequest): Deferred<Unit>

    @POST("groups/leavethegroup")
    fun leaveTheGroup(@Body leaveGroupRequest: LeaveGroupRequest): Deferred<Unit>

    /** Profile **/
    @GET("profile/getprofileinfo")
    fun getProfileInfo(
        @Query("user_id") userId: Int
    ): Deferred<ProfileGetUserResponse>

    @GET("profile/getfriendinfo")
    fun getFriendInfo(
        @Query("user_id") userId: Int,
        @Query("friend_id") friendId: Int
    ): Deferred<ProfileFriendInfo>

    @GET("profile/searchuser")
    fun searchUsers(
        @Query("phrase") phrase: String
    ): Deferred<List<UserInfo>>

    @POST("profile/changeprofileinfo")
    fun changeProfileInfo(@Body changeUserInfo: ChangeUserInfo): Deferred<Unit>

    @POST("profile/sendfriendrequest")
    fun sendFriendRequest(@Body friendRequest: ProcessFriendRequest): Deferred<Unit>

    @POST("profile/acceptgrouprequest")
    fun acceptGroupRequest(@Body groupRequest: ProcessGroupRequest): Deferred<Unit>

    @POST("profile/declinegrouprequest")
    fun declineGroupRequest(@Body groupRequest: ProcessGroupRequest): Deferred<Unit>

    @POST("profile/acceptfriendrequest")
    fun acceptFriendRequest(@Body friendRequest: ProcessFriendRequest): Deferred<Unit>

    @POST("profile/declinefriendrequest")
    fun declineFriendRequest(@Body friendRequest: ProcessFriendRequest): Deferred<Unit>

    @POST("profile/removefriend")
    fun removeFriend(@Body friendRequest: ProcessFriendRequest): Deferred<Unit>

    @GET("profile/login")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Deferred<Int>

    @POST("profile/addnewuser")
    fun register(@Body registerInfo: RegisterInfo): Deferred<Int>

}