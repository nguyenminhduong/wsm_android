package com.framgia.wsm.screen.login;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by tri on 25/08/2017.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginPresenterTest {

    private static final String EMAIL = "le.quang.dao@framgia.com.edev";
    private static final String PASSWORD = "123456";
    private static final String DEVICE_ID = "Android";
    @InjectMocks
    ImmediateSchedulerProvider mBaseSchedulerProvider;
    @InjectMocks
    LoginPresenter mPresenter;
    @Mock
    LoginViewModel mViewModel;
    @Mock
    UserRepository mUserRepository;
    @Mock
    Validator mValidator;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mBaseSchedulerProvider);
    }

    @Test
    public void login_shouldCorrect_whenLogin() throws Exception {
        //TODO edit later
    }

    @Test(expected = BaseException.class)
    public void login_shouldError_whenLoginError() throws Exception {
        //Give
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());

        //When
        when(mUserRepository.login(EMAIL, PASSWORD, DEVICE_ID)).thenThrow(baseException);

        //Then
        mPresenter.login(EMAIL, PASSWORD, DEVICE_ID);

        //Actual
        verify(mViewModel).onLoginError(baseException);
    }

    @Test
    public void validateDataInput_shouldReturnFalse_whenInputValidDataError() throws Exception {
        // Give
        boolean expect = false;

        // When
        when(mValidator.validateAll(mViewModel)).thenReturn(false);

        // Then
        boolean actual = mPresenter.validateDataInput(EMAIL, PASSWORD);

        //Actual
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void validateDataInput_shouldReturnTrue_whenInputValidData() throws Exception {
        // Give
        boolean expect = true;

        // When
        when(mValidator.validateAll(mViewModel)).thenReturn(true);

        // Then
        boolean actual = mPresenter.validateDataInput(EMAIL, PASSWORD);

        //Actual
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void checkUserLogin_shouldError_whencheckUserLoginError() throws Exception {
        //Give
        boolean isUnauthorized = true;

        //Then
        mPresenter.checkUserLogin(isUnauthorized);

        //Actual
        verify(mUserRepository).clearData();
    }

    @Test
    public void checkUserLogin_shouldCorrect_whenCheckUserLogin() throws Exception {
        //Give
        boolean isUnauthorized = false;
        User user = new User();
        List<LeaveType> leaveTypes = new ArrayList<>();
        LeaveType leaveType = new LeaveType();
        leaveType.setId(1);
        leaveTypes.add(leaveType);
        user.setLeaveTypes(leaveTypes);

        //When
        when(mUserRepository.getUserCheckLogin()).thenReturn(user);

        //Then
        mPresenter.checkUserLogin(isUnauthorized);

        //Actual
        verify(mViewModel).onUserLoggedIn();
    }

    @Test
    public void validateUserNameInput_shouldError_whenInputValidDataError() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        String msgInCorrectEmail = "Must In Correct Email";
        String msgEmpty = "";

        // When
        when(mValidator.validateValueNonEmpty(msgEmpty)).thenReturn(msgNotEmpty);
        when(mValidator.validateEmailFormat(msgEmpty)).thenReturn(msgInCorrectEmail);

        // Then
        mPresenter.validateUserNameInput(msgEmpty);

        //Actual
        verify(mViewModel).onInputUserNameError(msgNotEmpty);
    }

    @Test
    public void validateUserNameInput_shouldCorrect_whenInputValidData() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        String msgInCorrectEmail = "Must In Correct Email";

        // When
        when(mValidator.validateValueNonEmpty(EMAIL)).thenReturn(msgNotEmpty);
        when(mValidator.validateEmailFormat(EMAIL)).thenReturn(msgInCorrectEmail);

        // Then
        mPresenter.validateUserNameInput(EMAIL);

        //Actual
        verify(mViewModel).onInputUserNameError(msgNotEmpty);
    }

    @Test
    public void validatePasswordInput_shouldError_whenInputValidDataError() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        String msgRangeThan6 = "Must range than 6";
        String msgEmpty = "";

        // When
        when(mValidator.validateValueNonEmpty(msgEmpty)).thenReturn(msgNotEmpty);
        when(mValidator.validateValueRangeMin6(msgEmpty)).thenReturn(msgRangeThan6);

        // Then
        mPresenter.validatePasswordInput(msgEmpty);

        //Actual
        verify(mViewModel).onInputPasswordError(msgNotEmpty);
    }

    @Test
    public void validatePasswordInputt_shouldCorrect_whenInputValidData() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        String msgRangeThan6 = "Must range than 6";

        // When
        when(mValidator.validateValueNonEmpty(PASSWORD)).thenReturn(msgNotEmpty);
        when(mValidator.validateValueRangeMin6(PASSWORD)).thenReturn(msgRangeThan6);

        // Then
        mPresenter.validatePasswordInput(PASSWORD);

        //Actual
        verify(mViewModel, Mockito.never()).onInputPasswordError(null);
    }
}
