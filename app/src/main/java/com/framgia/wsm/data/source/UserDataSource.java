package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.request.ChangePasswordRequest;
import com.framgia.wsm.data.source.remote.api.request.UpdateProfileRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface UserDataSource {
    /**
     * LocalData For User
     */
    interface LocalDataSource extends BaseLocalDataSource {
        void saveUser(User user);

        Observable<User> getUser();

        void clearData();
    }

    /**
     * RemoteData For User
     */
    interface RemoteDataSource {
        Observable<BaseResponse<SignInDataResponse>> login(String userName, String password);

        Observable<BaseResponse<User>> getUserProfile(int userId);

        Observable<List<LeaveType>> getListLeaveType();

        Observable<List<OffType>> getListOffType();

        Observable<BaseResponse<UserProfileResponse>> updateProfile(
                UpdateProfileRequest updateProfileRequest);

        Observable<Object> changePassword(ChangePasswordRequest changePasswordRequest);
    }
}
