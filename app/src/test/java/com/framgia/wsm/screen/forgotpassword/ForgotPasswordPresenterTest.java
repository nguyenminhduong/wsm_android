package com.framgia.wsm.screen.forgotpassword;

import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.Single;
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
 * Created by levutantuan on 8/23/17.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class ForgotPasswordPresenterTest {

    private static final String EMAIL = "le.vu.tan.tuan@framgia.com.edev";

    @InjectMocks
    private ForgotPasswordPresenter mPresenter;
    @Mock
    private RequestRepository mRepository;
    @Mock
    private ForgotPasswordViewModel mViewModel;
    @Mock
    private Validator mValidator;
    @InjectMocks
    private ImmediateSchedulerProvider mSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mSchedulerProvider);
    }

    @Test
    public void sendEmail_shouldSuccess_whenInputValidDataAndNetworkSuccess() throws Exception {
        //When
        when(mRepository.sendEmail(EMAIL)).thenReturn(
                Single.just(new BaseResponse(Mockito.anyString())));

        //Then
        mPresenter.sendEmail(EMAIL);

        verify(mViewModel, Mockito.never()).onSendEmailError(null);
        verify(mViewModel).onSendEmailSuccess();
    }

    @Test
    public void sendEmail_shouldError_whenInputValidDataAndNetworkError()
            throws IllegalAccessException {
        // Give
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        // When
        when(mRepository.sendEmail(EMAIL)).thenReturn(Single.<BaseResponse>error(baseException));
        // Then
        mPresenter.sendEmail(EMAIL);

        verify(mViewModel, Mockito.never()).onSendEmailSuccess();
        verify(mViewModel).onSendEmailError(baseException);
    }

    @Test
    public void validateEmailInput_shouldCorrect_whenInputValidData() throws Exception {
        //Give
        String email = "abc@framgia.com.edev";
        String msgNotEmpty = "Must not Empty";
        String msgError = "Invalid email";

        //When
        when(mValidator.validateValueNonEmpty(email)).thenReturn(msgNotEmpty);
        when(mValidator.validateEmailFormat(email)).thenReturn(msgError);

        //Then
        mPresenter.validateEmailInput(email);
        verify(mViewModel, Mockito.never()).onInputEmailError("");
    }
}
