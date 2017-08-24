package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by ThS on 8/23/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MemberRequestDetailPresenterTest {

    @InjectMocks
    private MemberRequestDetailPresenter mPresenter;
    @Mock
    private UserRepository mUserRepository;
    @Mock
    private MemberRequestDetailViewModel mViewModel;
    @InjectMocks
    private ImmediateSchedulerProvider mSchedulerProvider;
    @Mock
    private RequestRepository mRequestRepository;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setSchedulerProvider(mSchedulerProvider);
    }

    @Test
    public void approveOrRejectRequest_shouldReturnTrue_whenPushActionRequest() throws Exception {
        //Give
        ActionRequest actionRequest = new ActionRequest();
        BaseResponse<ActionRequestResponse> actionRequestResponseBaseResponse =
                new BaseResponse<ActionRequestResponse>();
        //When
        Mockito.when(mRequestRepository.actionApproveRejectRequest(actionRequest))
                .thenReturn(Single.just(actionRequestResponseBaseResponse));
        //Then
        mPresenter.approveOrRejectRequest(actionRequest);
        //Actual
        Mockito.verify(mViewModel)
                .onApproveOrRejectRequestSuccess(actionRequestResponseBaseResponse.getData());
    }

    @Test(expected = BaseException.class)
    public void approveOrRejectRequest_shouldReturnError_whenCanNotActionRequest()
            throws Exception {
        //Give
        ActionRequest actionRequest = new ActionRequest();
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        //When
        Mockito.when(mRequestRepository.actionApproveRejectRequest(actionRequest))
                .thenThrow(baseException);
        //Then
        mPresenter.approveOrRejectRequest(actionRequest);
        //Actual
        Mockito.verify(mViewModel).onError(baseException);
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
        Mockito.verify(mViewModel).onError(baseException);
    }
}
