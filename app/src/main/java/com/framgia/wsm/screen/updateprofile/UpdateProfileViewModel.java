package com.framgia.wsm.screen.updateprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.UpdateProfileRequest;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.FileUtils;
import com.framgia.wsm.utils.RequestPermissionManager;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.io.File;
import java.util.Calendar;

/**
 * Exposes the data to be used in the Updateprofile screen.
 */

public class UpdateProfileViewModel extends BaseObservable
        implements UpdateProfileContract.ViewModel, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "UpdateProfileViewModel";
    private static final int AGE_EIGHTEEN = 18;

    private UpdateProfileContract.Presenter mPresenter;

    private Context mContext;
    private User mUser;
    private DialogManager mDialogManager;
    private Navigator mNavigator;
    private String mEmployeeNameError;
    private String mBirthdayError;
    private String mAvatar;
    private String mBirthday;
    private RequestPermissionManager mPermissionManager;
    private UpdateProfileRequest mUpdateProfileRequest;
    private User mUserLocal;

    UpdateProfileViewModel(Context context, User user, Navigator navigator,
            DialogManager dialogManager, UpdateProfileContract.Presenter presenter,
            RequestPermissionManager requestPermissionManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mUser = user;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mDialogManager.dialogDatePicker(this);
        mPermissionManager = requestPermissionManager;
        initData(mUser);
    }

    private void initData(User user) {
        mUserLocal = new User();
        mUpdateProfileRequest = new UpdateProfileRequest();
        mAvatar = user.getAvatar();
        setBirthday(user.getBirthday());
        mPresenter.getUserLocal();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onDismissProgressDialog() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onShowIndeterminateProgressDialog() {
        mDialogManager.showIndeterminateProgressDialog();
    }

    @Override
    public void onGetUserLocalSuccess(User userLocal) {
        if (userLocal == null) {
            return;
        }
        mUserLocal = userLocal;
    }

    @Override
    public void onGetUserLocalError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Override
    public void onInputEmployeeNameError(String employeeName) {
        mEmployeeNameError = employeeName;
        notifyPropertyChanged(BR.employeeNameError);
    }

    @Override
    public void onInputBirthdayError(String birthday) {
        mBirthdayError = birthday;
        notifyPropertyChanged(BR.birthdayError);
    }

    @Override
    public void onUpdateProfileSuccess(User user) {
        mUserLocal.setBirthday(user.getBirthday());
        mUserLocal.setAvatar(user.getAvatar());
        mUserLocal.setName(user.getName());
        mPresenter.updateUserLocal(mUserLocal);
        mNavigator.finishActivityWithResult(Activity.RESULT_OK);
    }

    @Override
    public void onUpdateProfileError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (year == 0) {
            setBirthday(null);
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int currentAgeEmployee = calendar.get(Calendar.YEAR) - year;
        if (currentAgeEmployee > AGE_EIGHTEEN) {
            String date = formatDate(DateTimeUtils.convertDateToString(year, month, dayOfMonth));
            setBirthday(date);
            return;
        }
        mDialogManager.dialogError(mContext.getString(R.string.your_age_is_under_employment),
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog,
                            @NonNull DialogAction dialogAction) {
                        mDialogManager.showDatePickerDialog();
                    }
                });
    }

    @Override
    public void setAvatarUser(Uri avatarUser) {
        mAvatar = avatarUser.toString();
        File file = new File(FileUtils.getRealPathFromURI(mContext, avatarUser));
        mUpdateProfileRequest.setAvatar(file);
        notifyPropertyChanged(BR.avatar);
    }

    @Override
    public void pickAvatarUser() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mNavigator.startActivityForResult(intent, Constant.RequestCode.REQUEST_SELECT_AVATAR);
    }

    @Bindable
    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public String getEmployeeNameError() {
        return mEmployeeNameError;
    }

    @Bindable
    public String getBirthdayError() {
        return mBirthdayError;
    }

    @Bindable
    public String getAvatar() {
        return mAvatar;
    }

    private String formatDate(String dataInput) {
        return DateTimeUtils.convertUiFormatToDataFormat(dataInput,
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD,
                mContext.getString(R.string.format_date_mm_dd_yyyy));
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onPickDate(View view) {
        mDialogManager.showDatePickerDialog();
    }

    public void onClickPickAvatar(View view) {
        if (mPermissionManager.isPermissionWriteExternalStorageGranted()) {
            pickAvatarUser();
        }
    }

    public void onCickSubmit(View view) {
        if (mUser == null) {
            return;
        }
        mUpdateProfileRequest.setUserId(mUser.getId());
        mUpdateProfileRequest.setName(mUser.getName());
        mUpdateProfileRequest.setBirthday(DateTimeUtils.convertUiFormatToDataFormat(mBirthday,
                mContext.getString(R.string.format_date_mm_dd_yyyy),
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD));
        mPresenter.updateProfile(mUpdateProfileRequest);
    }
}
