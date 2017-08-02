package com.framgia.wsm.data.source.remote.api.service;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.OffTypeDay;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.Setting;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.model.UserTimeSheet;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.request.ChangePasswordRequest;
import com.framgia.wsm.data.source.remote.api.request.NotificationRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.NotificationResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface WSMApi {
    @POST("/api/sign_in")
    Observable<BaseResponse<SignInDataResponse>> login(@Body SignInRequest signInRequest);

    @DELETE("/api/sign_out")
    Observable<Object> logout();

    @GET("/api/dashboard/users/{user_id}")
    Observable<BaseResponse<User>> getUserProfile(@Path("user_id") int userId);

    @Multipart
    @PUT("/api/dashboard/users/{user_id}")
    Observable<BaseResponse<User>> updateProfile(@Path("user_id") int userId,
            @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);

    @PUT("/api/dashboard/passwords")
    Observable<Object> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("/api/dashboard/user_timesheets")
    Observable<BaseResponse<UserTimeSheet>> getTimeSheet(@Query("month") int month,
            @Query("year") int year);

    @GET("/api/dashboard/holidays")
    Single<BaseResponse<List<HolidayCalendar>>> getHolidayCalendar(@Query("year") int year);

    @POST("/api/dashboard/request_ots")
    Single<Object> createFormRequestOverTime(@Body RequestOverTime requestOverTime);

    @PUT("api/dashboard/request_ots/{request_ots_id}")
    Single<Object> editFormRequestOverTime(@Path("request_ots_id") int requestOverTimeId,
            @Body RequestOverTime requestOverTime);

    @DELETE("api/dashboard/request_ots/{request_ots_id}")
    Single<Object> deleteFormRequestOverTime(@Path("request_ots_id") int requestOverTimeId);

    @POST("/api/dashboard/request_offs")
    Single<Object> createFormRequestOff(@Body RequestOffRequest requestOffRequest);

    @DELETE("/api/dashboard/request_offs/{request_offs_id}")
    Single<Object> deleteFormRequestOff(@Path("request_offs_id") int requestOffId);

    @PUT("/api/dashboard/request_offs/{request_offs_id}")
    Single<Object> editFormRequestOff(@Path("request_offs_id") int requestOffId,
            @Body RequestOffRequest requestOffRequest);

    @DELETE("/api/dashboard/request_leaves/{request_leave_id}")
    Single<Object> deleteFormRequestLeave(@Path("request_leave_id") int requestLeaveId);

    @PUT("/api/dashboard/request_leaves/{request_leave_id}")
    Single<Object> editFormRequestLeave(@Path("request_leave_id") int requestLeaveId,
            @Body RequestLeaveRequest requestLeaveRequest);

    @POST("/api/dashboard/request_leaves")
    Single<Object> createFormRequestLeave(@Body RequestLeaveRequest requestLeaveRequest);

    @GET("/api/dashboard/request_offs?")
    Single<BaseResponse<List<OffRequest>>> getListRequestOff(@QueryMap Map<String, String> params);

    @GET("/api/dashboard/request_ots?")
    Single<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
            @QueryMap Map<String, String> params);

    @GET("/api/dashboard/request_leaves?")
    Single<BaseResponse<List<LeaveRequest>>> getListRequestLeaves(
            @QueryMap Map<String, String> params);

    @GET("/api/dashboard/leave_types")
    Observable<BaseResponse<List<LeaveType>>> getListLeaveType();

    @GET("api/dashboard/dayoff_settings")
    Observable<BaseResponse<OffTypeDay>> getListOffType();

    @GET("/api/dashboard/notifications")
    Single<BaseResponse<NotificationResponse>> getNotification(@Query("page") int page);

    @PUT("/api/dashboard/set_notifications")
    Observable<BaseResponse> setReadNotifcation(@Body NotificationRequest notificationRequest);

    @GET("/api/dashboard/manager/request_leaves?")
    Single<BaseResponse<List<LeaveRequest>>> getListRequestLeaveManage(
            @QueryMap Map<String, String> params);

    @GET("/api/dashboard/manager/request_ots?")
    Single<BaseResponse<List<RequestOverTime>>> getListRequestOvertimeManage(
            @QueryMap Map<String, String> params);

    @GET("/api/dashboard/manager/request_offs?")
    Single<BaseResponse<List<OffRequest>>> getListRequestOffManage(
            @QueryMap Map<String, String> params);

    @PUT("api/dashboard/manager/approvals")
    Single<BaseResponse<ActionRequestResponse>> approveOrRejectRequest(
            @Body ActionRequest actionRequest);

    @GET("api/dashboard/user_settings")
    Single<BaseResponse<Setting>> getSetting();

    @PUT("api/dashboard/user_settings")
    Single<Object> changeSetting(@Body Setting setting);

    @PUT("api/dashboard/manager/approvals")
    Single<BaseResponse<List<ActionRequestResponse>>> approveAll(@Body ActionRequest actionRequest);
}
