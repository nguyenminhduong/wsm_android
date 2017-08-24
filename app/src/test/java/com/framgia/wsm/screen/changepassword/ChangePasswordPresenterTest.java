package com.framgia.wsm.screen.changepassword;

import android.content.Context;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.Observable;
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
 * Created by nguyenhuy95dn on 8/23/2017.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class ChangePasswordPresenterTest {

    private static final String CURRENT_PASSWORD = "123456";
    private static final String NEW_PASSWORD = "12345";
    private static final String CONFIRM_PASSWORD = "12345";

    @InjectMocks
    ChangePasswordPresenter mPresenter;
    @Mock
    ChangePasswordViewModel mViewModel;
    @Mock
    UserRepository mUserRepository;
    @Mock
    Validator mValidator;
    @Mock
    Context mMockContext;
    @InjectMocks
    ImmediateSchedulerProvider mBaseSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mBaseSchedulerProvider);
    }

    @Test
    public void getUser_shouldSuccess_whenGetUserFromLocal() throws Exception {
        // Give
        User user = new User();
        user.setName("Huy");
        user.setBirthday("11/1/1995");
        // When
        when(mUserRepository.getUser()).thenReturn(Observable.just(user));
        // Then
        mPresenter.getUser();

        verify(mViewModel, Mockito.never()).onGetUserError(null);
        verify(mViewModel).onGetUserSuccess(user);
    }

    @Test
    public void getUser_shouldError_whenGetUserFromLocalAndNetworkError()
            throws IllegalAccessException {
        // Give
        User user = new User();
        user.setName("Huy");
        user.setBirthday("11/1/1995");
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        // When
        when(mUserRepository.getUser()).thenReturn(Observable.<User>error(baseException));
        // Then
        mPresenter.getUser();

        verify(mViewModel, Mockito.never()).onGetUserSuccess(user);
        verify(mViewModel).onGetUserError(baseException);
    }

    @Test
    public void changePassword_shouldSuccess_whenInputValidData() throws Exception {
        // Give
        Object a = new Object();
        // When
        when(mUserRepository.changePassword(CURRENT_PASSWORD, NEW_PASSWORD,
                CONFIRM_PASSWORD)).thenReturn(Observable.just(a));
        // Then
        mPresenter.changePassword(CURRENT_PASSWORD, NEW_PASSWORD, CONFIRM_PASSWORD);

        verify(mViewModel, Mockito.never()).onChangePasswordError(null);
        verify(mViewModel).onChangePasswordSuccess();
    }

    @Test
    public void changePassword_shouldError_whenInputValidDataAndNetworkError()
            throws IllegalAccessException {
        // Give
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        // When
        when(mUserRepository.changePassword(CURRENT_PASSWORD, NEW_PASSWORD,
                CONFIRM_PASSWORD)).thenReturn(Observable.<Object>error(baseException));
        // Then
        mPresenter.changePassword(CURRENT_PASSWORD, NEW_PASSWORD, CONFIRM_PASSWORD);

        verify(mViewModel, Mockito.never()).onChangePasswordSuccess();
        verify(mViewModel).onChangePasswordError(baseException);
    }

    @Test
    public void validateDataInput_shouldReturnTrue_whenInputValidData() throws Exception {
        // Give
        boolean expect = true;
        // When
        when(mValidator.validateAll(mViewModel)).thenReturn(true);
        // Then
        boolean actual =
                mPresenter.validateDataInput(CURRENT_PASSWORD, NEW_PASSWORD, CONFIRM_PASSWORD);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void validateDataInput_shouldReturnFalse_whenInputValidDataError() throws Exception {
        // Give
        boolean expect = false;
        // When
        when(mValidator.validateAll(mViewModel)).thenReturn(true);
        // Then
        boolean actual =
                mPresenter.validateDataInput(CONFIRM_PASSWORD, NEW_PASSWORD, CURRENT_PASSWORD);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void validateCurrentPasswordInput_shouldCorrect_whenInputValidData() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        // When
        when(mValidator.validateValueNonEmpty(CURRENT_PASSWORD)).thenReturn(msgNotEmpty);
        // Then
        mPresenter.validateCurrentPasswordInput(CURRENT_PASSWORD);

        verify(mViewModel, Mockito.never()).onInputCurrentPasswordError(null);
    }

    @Test
    public void validateCurrentPasswordInput_shouldError_whenInputValidDataError()
            throws IllegalAccessException {
        // Give
        String msgNotEmpty = "Must not empty";
        String emptyMsg = "";
        // When
        when(mValidator.validateValueNonEmpty(emptyMsg)).thenReturn(msgNotEmpty);
        // Then
        mPresenter.validateCurrentPasswordInput(emptyMsg);

        verify(mViewModel).onInputCurrentPasswordError(msgNotEmpty);
    }

    @Test
    public void validateNewPasswordInput_shouldCorrect_whenInputValidData() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        String msgRangeThan6 = "Must range than 6";
        // When
        when(mValidator.validateValueNonEmpty(NEW_PASSWORD)).thenReturn(msgNotEmpty);
        when(mValidator.validateValueRangeMin6(NEW_PASSWORD)).thenReturn(msgRangeThan6);
        // Then
        mPresenter.validateNewPasswordInput(NEW_PASSWORD);

        verify(mViewModel, Mockito.never()).onInputNewPasswordError(null);
    }

    @Test
    public void validateNewPasswordInput_shouldError_whenInputValidDataError() throws Exception {
        // Give
        String msgNotEmpty = "Must not empty";
        String msgRangeThan6 = "Must range than 6";
        String msgEmpty = "";
        // When
        when(mValidator.validateValueNonEmpty(msgEmpty)).thenReturn(msgNotEmpty);
        when(mValidator.validateValueRangeMin6(msgEmpty)).thenReturn(msgRangeThan6);
        // Then
        mPresenter.validateNewPasswordInput(msgEmpty);

        verify(mViewModel).onInputNewPasswordError(msgNotEmpty);
    }

    @Test
    public void validateConfirmPasswordInput_shouldReturnTrue_whenInputValidData()
            throws Exception {
        // Give
        boolean expect = true;
        // Then
        boolean actual = mPresenter.validateConfirmPasswordInput(NEW_PASSWORD, CONFIRM_PASSWORD);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void validateConfirmPasswordInput_shouldReturnFalse_whenInputValidDataError()
            throws Exception {
        // Give
        boolean expect = false;
        // Then
        boolean actual = mPresenter.validateConfirmPasswordInput(NEW_PASSWORD, CURRENT_PASSWORD);

        Assert.assertEquals(expect, actual);
    }
}
