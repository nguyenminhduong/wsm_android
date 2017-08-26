package com.framgia.wsm.screen.listrequest;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by ThS on 8/24/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ListRequestPresenterTest {

    @InjectMocks
    private ListRequestPresenter mPresenter;
    @InjectMocks
    private ImmediateSchedulerProvider mSchedulerProvider;
    @Mock
    private RequestRepository mRequestRepository;
    @Mock
    private UserRepository mUserRepository;
    @Mock
    private ListRequestViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mSchedulerProvider);
    }

    @Test
    public void getUser_shouldReturnTrue_whenGetUser() throws Exception {
        //Give
        User user = new User();
        //When
        Mockito.when(mUserRepository.getUser()).thenReturn(Observable.just(user));
        //Then
        mPresenter.getUser();
        Mockito.verify(mViewModel).onGetUserSuccess(user);
    }

    @Test(expected = BaseException.class)
    public void getUser_shouldReturnError_whenCanNotUser() throws Exception {
        //Give
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        //When
        Mockito.when(mUserRepository.getUser()).thenThrow(baseException);
        //Then
        mPresenter.getUser();
        //Actual
        Mockito.verify(mViewModel).onGetUserError(baseException);
    }

    @Test
    public void getListAllRequest_shouldReturnObject_whenGetListRequestOverTime() throws Exception {
        //Give
        boolean isLoad = false;
        Object object = null;
        QueryRequest queryRequest = new QueryRequest();
        BaseResponse<List<RequestOverTime>> listBaseResponseRequestOverTime =
                new BaseResponse<List<RequestOverTime>>();
        //When
        Mockito.when(mRequestRepository.getListRequestOverTime(queryRequest))
                .thenReturn(Single.just(listBaseResponseRequestOverTime));
        //Then
        mPresenter.getListAllRequest(RequestType.REQUEST_OVERTIME, queryRequest,
                Mockito.anyBoolean());
        //Actual
        Mockito.verify(mViewModel)
                .onGetListRequestSuccess(RequestType.REQUEST_OVERTIME, object, isLoad);
    }

    @Test
    public void getListAllRequest_shouldReturnObject_whenGetListRequestLeave() throws Exception {
        //Give
        boolean isLoad = false;
        Object object = null;
        QueryRequest queryRequest = new QueryRequest();
        BaseResponse<List<OffRequest>> listBaseResponseRequestOff =
                new BaseResponse<List<OffRequest>>();
        //When
        Mockito.when(mRequestRepository.getListRequestOff(queryRequest))
                .thenReturn(Single.just(listBaseResponseRequestOff));
        //Then
        mPresenter.getListAllRequest(RequestType.REQUEST_OFF, queryRequest, Mockito.anyBoolean());
        //Actual
        Mockito.verify(mViewModel).onGetListRequestSuccess(RequestType.REQUEST_OFF, object, isLoad);
    }

    @Test
    public void getListAllRequest_shouldReturnObject_whenGetListRequestOthers() throws Exception {
        //Give
        boolean isLoad = false;
        Object object = null;
        QueryRequest queryRequest = new QueryRequest();
        BaseResponse<List<LeaveRequest>> listBaseResponseRequestLeave =
                new BaseResponse<List<LeaveRequest>>();
        //When
        Mockito.when(mRequestRepository.getListRequestLateEarly(queryRequest))
                .thenReturn(Single.just(listBaseResponseRequestLeave));
        //Then
        mPresenter.getListAllRequest(RequestType.REQUEST_LATE_EARLY, queryRequest,
                Mockito.anyBoolean());
        //Actual
        Mockito.verify(mViewModel)
                .onGetListRequestSuccess(RequestType.REQUEST_LATE_EARLY, object, isLoad);
    }

    @Test(expected = BaseException.class)
    public void getListAllRequest_shouldReturnError_whenGetListRequest() throws Exception {
        //Give
        QueryRequest queryRequest = new QueryRequest();
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        //When
        Mockito.when(mRequestRepository.getListRequestLateEarly(queryRequest))
                .thenThrow(baseException);
        //Then
        mPresenter.getListAllRequest(RequestType.REQUEST_LATE_EARLY, queryRequest,
                Mockito.anyBoolean());
        //Actual
        Mockito.verify(mViewModel).onGetListRequestError(baseException);
    }
}
