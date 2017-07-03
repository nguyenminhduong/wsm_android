package com.framgia.wsm.data.source.remote.api.service;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.HolidayCalendarResponse;
import com.framgia.wsm.data.source.remote.api.response.LeaveTypeResponse;
import com.framgia.wsm.data.source.remote.api.response.OffTypeResponse;
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
    Observable<BaseResponse<UserProfileResponse>> getUserProfile(@Path("user_id") int userId);

    @Multipart
    @PUT("/api/dashboard/users")
    Observable<BaseResponse<UserProfileResponse>> updateProfile(
            @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);

    // TODO edit later
    @GET("v1/time_sheet")
    Observable<TimeSheetResponse> getTimeSheet(@Query("month") int month, @Query("year") int year);

    @GET("v1/holiday_calendar")
    Observable<BaseResponse<HolidayCalendarResponse>> getHolidayCalendar(@Query("year") int year);

    // TODO edit later
    @POST("/api/v1/request_overtime")
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
    Observable<Object> createFormRequestOff(@Body RequestOff requestOff);

    // TODO edit later
    @DELETE("/api/request_off")
    Observable<Object> deleteFormRequestOff(@Query("requestoff_id") int requestOffId);

    // TODO edit later
    @PUT("/api/request_off")
    Observable<BaseResponse<RequestOff>> editFormRequestOff(@Body RequestOff requestOff);

    // TODO edit later
    @DELETE("/api/request_off")
    Observable<Object> deleteFormRequestLeave(@Query("id") int requestLeaveId);

    // TODO edit later
    @PUT("/api/request_off")
    Observable<BaseResponse<Request>> editFormRequestLeave(@Body Request requestLeave);

    // TODO edit later
    @POST("/api/v1/request_leave")
    Observable<Object> createFormRequestLeave(@Body Request request);

    // TODO edit later
    @GET("/api/v1/list_request_off")
    Observable<BaseResponse<List<Request>>> getListRequestOff();

    // TODO edit later
    @GET("/api/v1/list_request_over_time")
    Observable<BaseResponse<List<Request>>> getListRequestOverTime();

    // TODO edit later
    @GET("/api/v1/list_request_late_early")
    Observable<BaseResponse<List<Request>>> getListRequestLateEarly();

    @GET("/api/dashboard/leave_types")
    Observable<BaseResponse<LeaveTypeResponse>> getListLeaveType();

    @GET("api/dashboard/dayoff_settings")
    Observable<BaseResponse<OffTypeResponse>> getListOffType();

    //TODO edit later
    @GET("/api/v1/list_request_over_time")
    Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTimeWithStatusAndTime(
            @Query("status") int status, @Query("time") String time);

    //TODO edit later
    @GET("/api/v1/list_request_leave")
    Observable<BaseResponse<List<Request>>> getListRequestLeaveWithStatusAndTime(
            @Query("status") int status, @Query("time") String time);

    //TODO edit later
    @GET("/api/v1/list_request_leave")
    Observable<BaseResponse<List<Request>>> getListRequestOffWithStatusAndTime(
            @Query("status") int status, @Query("time") String time);
}
