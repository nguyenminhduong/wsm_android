package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.Group;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */
public class UserRepository {
    private UserDataSource.LocalDataSource mLocalDataSource;
    private UserDataSource.RemoteDataSource mRemoteDataSource;

    public UserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<BaseResponse<SignInDataResponse>> login(String userName, String passWord) {
        //todo edit later
        User user = new User();
        user.setName("Nguyễn Văn A");
        user.setCode("B1228368");
        user.setEmail("nguyen.van.a@framgia.com");
        List<Branch> branchList = new ArrayList<>();
        branchList.add(new Branch("1", "Da Nang Office"));
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group("1", "Android Members"));
        user.setBranches(branchList);
        user.setGroups(groupList);

        String[] arrLeaveType = {
                "In late (M)", "In late (A)", "Leave early (M)", "Leave early (A)", "Leave out",
                "Forgot to check in/check out (all day)", "Forgot to check in",
                "Forgot to check out", "Forgot card (all day)", "Forgot card (in)",
                "Forgot card (out)", "In late (woman only) (M)", "In late (woman only) (A)",
                "Leave early (woman only) (M)", "Leave early (woman only) (A)"
        };
        List<LeaveType> leaveTypeList = new ArrayList<>();
        for (int i = 0; i < arrLeaveType.length; i++) {
            LeaveType leaveType = new LeaveType();
            leaveType.setId(i);
            leaveType.setName(arrLeaveType[i]);
            leaveTypeList.add(leaveType);
        }
        user.setLeaveTypes(leaveTypeList);

        SignInDataResponse signInDataResponse = new SignInDataResponse();
        signInDataResponse.setUser(user);
        signInDataResponse.setAuthenToken("12345678");
        BaseResponse<SignInDataResponse> baseResponse =
                new BaseResponse<SignInDataResponse>(signInDataResponse) {
                };
        return Observable.just(baseResponse);
    }

    public void saveUser(User user) {
        mLocalDataSource.saveUser(user);
    }

    public Observable<User> getUser() {
        return mLocalDataSource.getUser();
    }

    public void clearData() {
        mLocalDataSource.clearData();
    }
}
