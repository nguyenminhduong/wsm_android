package com.framgia.wsm.data.source.remote.api.service;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.RequestOffResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    // TODO edit later
    @GET("v1/time_sheet")
    Observable<TimeSheetResponse> getTimeSheet(@Query("month") int month, @Query("year") int year);

    // TODO edit later
    @POST("/api/v1/request_overtime")
    Observable<Object> createFormRequestOverTime(@Body RequestOverTime requestOverTime);

    // TODO edit later
    @POST("/api/v1/request_off")
    Observable<Object> createFormRequestOff(@Body RequestOff requestOff);

    // TODO edit later
    @DELETE("/api/request_off")
    Observable<Object> deleteFormRequestOff(@Query("requestoff_id") int requestOffId);

    // TODO edit later
    @PUT("/api/request_off")
    Observable<BaseResponse<RequestOffResponse>> editFormRequestOff(@Body RequestOff requestOff);

    // TODO edit later
    @POST("/api/v1/request_leave")
    Observable<Object> createFormRequestLeave(@Body Request request);

    // TODO edit later
    @GET("/api/v1/list_request_off")
    Observable<BaseResponse<List<Request>>> getListRequestOff(@Query("user_id") int userId);

    // TODO edit later
    @GET("/api/v1/list_request_over_time")
    Observable<BaseResponse<List<Request>>> getListRequestOverTime(@Query("user_id") int userId);

    // TODO edit later
    @GET("/api/v1/list_request_late_early")
    Observable<BaseResponse<List<Request>>> getListRequestLateEarly(@Query("user_id") int userId);
}
