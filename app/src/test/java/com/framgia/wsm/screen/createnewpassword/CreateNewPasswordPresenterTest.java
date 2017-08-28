package com.framgia.wsm.screen.createnewpassword;

import android.content.Context;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.Single;
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
 * Created by levutantuan on 8/26/17.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class CreateNewPasswordPresenterTest {

    private static final String TOKEN_RESET = "ABCD";
    private static final String NEW_PASSWORD = "123123";
    private static final String CONFIRM_PASSWORD = "123123";
    private static final String PASSWORD_INCORRECT = "111111";

    @InjectMocks
    private CreateNewPasswordPresenter mPresenter;
    @Mock
    private CreateNewPasswordViewModel mViewModel;
    @Mock
    private RequestRepository mRepository;
    @Mock
    private Validator mValidator;
    @Mock
    private Context mContext;
    @InjectMocks
    private ImmediateSchedulerProvider mBaseSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mBaseSchedulerProvider);
    }

    @Test
    public void resetPassword_shouldSuccess_whenInputValidData() throws Exception {
        //Give
        Object object = new Object();

        //When
        when(mRepository.resetPassword(TOKEN_RESET, NEW_PASSWORD, CONFIRM_PASSWORD)).thenReturn(
                Single.just(object));

        //Then
        mPresenter.resetPassword(TOKEN_RESET, NEW_PASSWORD, CONFIRM_PASSWORD);

        verify(mViewModel, Mockito.never()).onResetPasswordError(null);
        verify(mViewModel).onResetPasswordSuccess();
    }

    @Test
    public void resetPassword_shouldError_whenInputValidData() throws Exception {
        //Give
        BaseException exception = new BaseException(Type.HTTP, new ErrorResponse());
        //When
        when(mRepository.resetPassword(TOKEN_RESET, NEW_PASSWORD, CONFIRM_PASSWORD)).thenReturn(
                Single.error(exception));

        //Then
        mPresenter.resetPassword(TOKEN_RESET, NEW_PASSWORD, CONFIRM_PASSWORD);

        verify(mViewModel, Mockito.never()).onResetPasswordSuccess();
        verify(mViewModel).onResetPasswordError(exception);
    }

    @Test
    public void validateNewPasswordInput_shouldCorrect_whenInputValidData() throws Exception {
        //Give
        String msgNotEmpty = "Password Must Not Empty";
        String msgError = "Must not be less than 6 characters";

        //When
        when(mValidator.validateValueNonEmpty(NEW_PASSWORD)).thenReturn(msgNotEmpty);
        when(mValidator.validateValueRangeMin6(NEW_PASSWORD)).thenReturn(msgError);

        //Then
        mPresenter.validateNewPasswordInput(NEW_PASSWORD);
        verify(mViewModel, Mockito.never()).onInputNewPasswordError("");
    }

    @Test
    public void validateNewPasswordInput_shouldError_whenInputValidData() throws Exception {
        //Give
        String msgNotEmpty = "Password Must Not Empty";
        String msgError = "Must not be less than 6 characters";

        //When
        when(mValidator.validateValueNonEmpty(NEW_PASSWORD)).thenReturn(msgNotEmpty);
        when(mValidator.validateValueRangeMin6(NEW_PASSWORD)).thenReturn(msgError);

        //Then
        mPresenter.validateNewPasswordInput("");
        verify(mViewModel, Mockito.never()).onInputNewPasswordError(msgNotEmpty);
    }

    @Test
    public void validateConfirmPasswordInput_shouldReturnTrue_whenInputValidData()
            throws Exception {
        //Give
        boolean expect = true;

        //Then
        boolean actual = mPresenter.validateConfirmPasswordInput(NEW_PASSWORD, CONFIRM_PASSWORD);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void validateConfirmPasswordInput_shouldReturnFalse_whenInputValidData()
            throws Exception {
        //Give
        boolean expect = false;

        //Then
        boolean actual = mPresenter.validateConfirmPasswordInput(NEW_PASSWORD, PASSWORD_INCORRECT);
        Assert.assertEquals(expect, actual);
    }
}
