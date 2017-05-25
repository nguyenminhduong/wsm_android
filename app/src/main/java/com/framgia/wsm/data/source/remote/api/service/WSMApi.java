package com.framgia.wsm.data.source.remote.api.service;

import com.framgia.wsm.data.source.remote.api.response.UserResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface WSMApi {
    // TODO: 25/05/2017 edit later
    @GET("v1/authen_user_tokens")
    Observable<UserResponse> login(@Query("user_email") String userEmail,
            @Query("password") String passWord);
}
