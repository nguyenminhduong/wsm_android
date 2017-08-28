package com.framgia.wsm.screen.forgotpassword;

import android.util.Log;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link ForgotPasswordActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {
    private static final String TAG = ForgotPasswordPresenter.class.getName();

    private ForgotPasswordContract.ViewModel mViewModel;
    private RequestRepository mRequestRepository;
    private Validator mValidator;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    ForgotPasswordPresenter(RequestRepository requestRepository, Validator validator) {
        mRequestRepository = requestRepository;
        mValidator = validator;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(ForgotPasswordContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void sendEmail(final String email) {
        validateEmailInput(email);
        Disposable disposable = mRequestRepository.sendEmail(email)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(@NonNull BaseResponse baseRequestBaseResponse)
                            throws Exception {
                        mViewModel.onSendEmailSuccess();
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onSendEmailError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public boolean validateDataInput(String email) {
        validateEmailInput(email);
        try {
            return mValidator.validateAll(mViewModel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "validateDataInput: ", e);
            return false;
        }
    }

    @Override
    public void validateEmailInput(String emailInput) {
        String messageEmail = mValidator.validateValueNonEmpty(emailInput);
        if (StringUtils.isBlank(messageEmail)) {
            messageEmail = mValidator.validateEmailFormat(emailInput);
            if (StringUtils.isNotBlank(messageEmail)) {
                mViewModel.onInputEmailError(messageEmail);
            } else {
                mViewModel.onInputEmailError("");
            }
        } else {
            mViewModel.onInputEmailError(messageEmail);
        }
    }

    public void setBaseSchedulerProvider(BaseSchedulerProvider baseSchedulerProvider) {
        mBaseSchedulerProvider = baseSchedulerProvider;
    }
}
