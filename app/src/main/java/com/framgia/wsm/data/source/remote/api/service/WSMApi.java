package com.framgia.wsm.data.source.remote.api.service;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface WSMApi {
    @POST("/api/v1/sign_in")
    Observable<BaseResponse<SignInDataResponse>> login(@Body SignInRequest signInRequest);

    // TODO edit later
    @GET("v1/time_sheet")
    Observable<TimeSheetResponse> getTimeSheet(@Query("month") int month, @Query("year") int year);

    // TODO edit later
    @POST("/api/v1/request_overtime")
    Observable<Object> createFormRequestOverTime(@Body Request request);

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
