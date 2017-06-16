package com.framgia.wsm.widget.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.R;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.validator.Validator;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by le.quang.dao on 14/03/2017.
 */
public class DialogManagerImpl implements DialogManager {
    private static final String TAG = Validator.class.getName();
    private static final String DATE_PICKER = "mDatePicker";
    private static final String DAY_FIELD = "day";
    private static final String ID = "id";
    private static final String ANDROID = "android";
    private Context mContext;
    private MaterialDialog mProgressDialog;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private Calendar mCalendar;

    public DialogManagerImpl(Context context) {
        mContext = context;
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void showIndeterminateProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new MaterialDialog.Builder(mContext).content("Please wait…")
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .build();
        }
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
    }

    @Override
    public void dialogError(String content,
            MaterialDialog.SingleButtonCallback positiveButtonListener) {
        new MaterialDialog.Builder(mContext).content(content)
                .positiveText(R.string.retry)
                .onPositive(positiveButtonListener)
                .show();
    }

    @Override
    public void dialogError(BaseException e) {
        new MaterialDialog.Builder(mContext).content(e.getMessage())
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override
    public void dialogBasicWithoutTitle(String content,
            MaterialDialog.SingleButtonCallback positiveButtonListener) {
        new MaterialDialog.Builder(mContext).content(content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive(positiveButtonListener)
                .show();
    }

    @Override
    public void dialogBasic(String title, String content,
            MaterialDialog.SingleButtonCallback positiveButtonListener) {
        new MaterialDialog.Builder(mContext).title(title)
                .content(content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive(positiveButtonListener)
                .show();
    }

    @Override
    public void dialogBasicIcon(String title, String content, @DrawableRes int icon,
            MaterialDialog.SingleButtonCallback positiveButtonListener) {
        new MaterialDialog.Builder(mContext).title(title)
                .content(content)
                .iconRes(icon)
                .limitIconToDefaultSize()
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive(positiveButtonListener)
                .show();
    }

    @Override
    public void dialogBasicCheckPrompt(String title, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(mContext).iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize()
                .title(title)
                .checkBoxPrompt(mContext.getString(R.string.dont_ask_again), false, null)
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onAny(callback)
                .show();
    }

    @Override
    public void dialogNeutral(String title, String content,
            MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(mContext).title(title)
                .content(content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .neutralText(R.string.more_info)
                .onAny(callback)
                .show();
    }

    @Override
    public void dialogList(String title, @ArrayRes int arrayId,
            MaterialDialog.ListCallback callback) {
        new MaterialDialog.Builder(mContext).title(title)
                .items(arrayId)
                .itemsCallback(callback)
                .show();
    }

    @Override
    public void dialogListWithoutTitle(@ArrayRes int arrayId,
            MaterialDialog.ListCallback callback) {
        new MaterialDialog.Builder(mContext).items(arrayId).itemsCallback(callback).show();
    }

    @Override
    public void dialogListSingleChoice(String title, @ArrayRes int arrayId, int selectedIndex,
            MaterialDialog.ListCallbackSingleChoice callback) {
        new MaterialDialog.Builder(mContext).title(title)
                .items(arrayId)
                .itemsCallbackSingleChoice(selectedIndex, callback)
                .show();
    }

    @Override
    public void dialogListSingleChoice(String title, String[] strings, int selectedIndex,
            MaterialDialog.ListCallbackSingleChoice callback) {
        new MaterialDialog.Builder(mContext).title(title)
                .items(strings)
                .itemsCallbackSingleChoice(selectedIndex, callback)
                .show();
    }

    @Override
    public void dialogListMultiChoice(String title, @ArrayRes int arrayId,
            Integer[] selectedIndices, MaterialDialog.ListCallbackMultiChoice callback) {
        new MaterialDialog.Builder(mContext).title(title)
                .items(arrayId)
                .positiveText(R.string.choose)
                .autoDismiss(false)
                .neutralText(R.string.clear)
                .itemsCallbackMultiChoice(selectedIndices, callback)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog,
                            @NonNull DialogAction dialogAction) {
                        materialDialog.clearSelectedIndices();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog,
                            @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public DialogManager dialogMonthYearPicker(DatePickerDialog.OnDateSetListener onDateSetListener,
            int year, int month) {
        mDatePickerDialog =
                new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT, onDateSetListener,
                        year, month, -1);
        try {
            Field[] datePickerDialogFields = mDatePickerDialog.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals(DATE_PICKER)) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker =
                            (DatePicker) datePickerDialogField.get(mDatePickerDialog);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int daySpinnerId =
                                Resources.getSystem().getIdentifier(DAY_FIELD, ID, ANDROID);
                        if (daySpinnerId != 0) {
                            View daySpinner = datePicker.findViewById(daySpinnerId);
                            if (daySpinner != null) {
                                daySpinner.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException: ", e);
        }
        return this;
    }

    public void showMonthYearPickerDialog() {
        if (mDatePickerDialog == null) {
            return;
        }
        mDatePickerDialog.show();
    }

    @Override
    public DialogManager dialogDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener) {
        mDatePickerDialog =
                new DatePickerDialog(mContext, onDateSetListener, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        return this;
    }

    @Override
    public void showDatePickerDialog() {
        if (mDatePickerDialog == null) {
            return;
        }
        mDatePickerDialog.show();
    }

    @Override
    public DialogManager dialogTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        mTimePickerDialog = new TimePickerDialog(mContext, onTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        return this;
    }

    @Override
    public void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            return;
        }
        mTimePickerDialog.show();
    }
}
