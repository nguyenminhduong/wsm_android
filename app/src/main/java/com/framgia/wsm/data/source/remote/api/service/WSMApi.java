package com.framgia.wsm.data.source.remote.api.service;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.OffTypeDay;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.request.ChangePasswordRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.HolidayCalendarResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import io.reactivex.Observable;
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

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface WSMApi {
    @POST("/api/sign_in")
    Observable<BaseResponse<SignInDataResponse>> login(@Body SignInRequest signInRequest);

    @GET("/api/dashboard/users/{user_id}")
    Observable<BaseResponse<User>> getUserProfile(@Path("user_id") int userId);

    @Multipart
    @PUT("/api/dashboard/users")
    Observable<BaseResponse<UserProfileResponse>> updateProfile(
            @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);

    // TODO edit later
    @PUT("/api/dashboard/users/change_password")
    Observable<Object> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("/api/dashboard/user_timesheets")
    Observable<BaseResponse<TimeSheetResponse>> getTimeSheet(@Query("month") int month,
            @Query("year") int year);

    @GET("v1/holiday_calendar")
    Observable<BaseResponse<HolidayCalendarResponse>> getHolidayCalendar(@Query("year") int year);

    @POST("/api/dashboard/request_ots")
    Observable<Object> createFormRequestOverTime(@Body RequestOverTime requestOverTime);

    // TODO edit later
    @PUT("/api/request_overtime/edit")
    Observable<BaseResponse<RequestOverTime>> editFormRequestOverTime(
            @Body RequestOverTime requestOverTime);

    // TODO edit later
    @DELETE("/api/request_overtime/delete/{request_overtime_id}")
    Observable<Object> deleteFormRequestOverTime(
            @Path("request_overtime_id") int requestOverTimeId);

    // TODO edit later
    @POST("/api/dashboard/request_offs")
    Observable<Object> createFormRequestOff(@Body RequestOffRequest requestOffRequest);

    // TODO edit later
    @DELETE("/api/request_off")
    Observable<Object> deleteFormRequestOff(@Query("requestoff_id") int requestOffId);

    // TODO edit later
    @PUT("/api/request_off")
    Observable<BaseResponse<OffRequest>> editFormRequestOff(
            @Body RequestOffRequest requestOffRequest);

    // TODO edit later
    @DELETE("/api/request_off")
    Observable<Object> deleteFormRequestLeave(@Query("id") int requestLeaveId);

    // TODO edit later
    @PUT("/api/request_off")
    Observable<BaseResponse<LeaveRequest>> editFormRequestLeave(@Body LeaveRequest requestLeave);

    // TODO edit later
    @POST("/api/dashboard/request_leaves")
    Observable<Object> createFormRequestLeave(@Body LeaveRequest request);

    // TODO edit later
    @GET("/api/v1/list_request_off")
    Observable<BaseResponse<List<LeaveRequest>>> getListRequestOff();

    @GET("/api/dashboard/request_ots")
    Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime();

    // TODO edit later
    @GET("/api/v1/list_request_late_early")
    Observable<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly();

    @GET("/api/dashboard/leave_types")
    Observable<BaseResponse<List<LeaveType>>> getListLeaveType();

    @GET("api/dashboard/dayoff_settings")
    Observable<BaseResponse<OffTypeDay>> getListOffType();

    //TODO edit later
    @GET("/api/v1/list_request_over_time")
    Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTimeWithStatusAndTime(
            @Query("status") int status, @Query("time") String time);

    //TODO edit later
    @GET("/api/v1/list_request_leave")
    Observable<BaseResponse<List<LeaveRequest>>> getListRequestLeaveWithStatusAndTime(
            @Query("status") int status, @Query("time") String time);

    //TODO edit later
    @GET("/api/v1/list_request_leave")
    Observable<BaseResponse<List<LeaveRequest>>> getListRequestOffWithStatusAndTime(
            @Query("status") int status, @Query("time") String time);
}
