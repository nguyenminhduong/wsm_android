package com.framgia.wsm.screen.timesheet;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.model.UserTimeSheet;
import com.framgia.wsm.data.source.TimeSheetRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Duong on 8/24/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TimeSheetPresenterTest {
    @Mock
    TimeSheetRepository mTimeSheetRepository;
    @InjectMocks
    private TimeSheetPresenter mPresenter;
    @InjectMocks
    private ImmediateSchedulerProvider mSchedulerProvider;
    @Mock
    private UserRepository mUserRepository;
    @Mock
    private TimeSheetViewModel mViewModel;

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
    public void getTimeSheet_shouldReturnUserTimeSheet_whenGetTimeSheetSuccess() throws Exception {
        //Give
        int month = 7;
        int year = 2017;
        UserTimeSheet userTimeSheet = new UserTimeSheet();
        BaseResponse<UserTimeSheet> userTimeSheetBaseResponse = new BaseResponse<>(userTimeSheet);
        //When
        Mockito.when(mTimeSheetRepository.getTimeSheet(month, year))
                .thenReturn(Observable.just(userTimeSheetBaseResponse));
        //Then
        mPresenter.getTimeSheet(month, year);
        //Actual
        Mockito.verify(mViewModel).onGetTimeSheetSuccess(userTimeSheet);
    }

    @Test(expected = BaseException.class)
    public void getTimeSheet_shouldReturnError_whenGetTimeSheetError() throws Exception {
        //Give
        int month = 7;
        int year = 2017;
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        //When
        Mockito.when(mTimeSheetRepository.getTimeSheet(month, year)).thenThrow(baseException);
        //Then
        mPresenter.getTimeSheet(month, year);
        //Actual
        Mockito.verify(mViewModel).onGetTimeSheetError(baseException);
    }
}
