package com.framgia.wsm.widget.timesheet;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.utils.common.DateTimeUtils;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.framgia.wsm.utils.Constant.TimeConst.DAY_25_OF_MONTH;

/**
 * TimeSheetView
 */
public class TimeSheetView extends View {

    private static final int SELECTED_CIRCLE_ALPHA = 128;
    private static final int DEFAULT_HEIGHT = 32;
    private int mDaySelectedCircleSize;
    private int mDaySeparatorWidth = 1;
    private int mMiniDayNumberTextSize;
    private int mMonthDayLabelTextSize;
    private int mMonthHeaderSize;
    private int mMonthLabelTextSize;
    private int mDayLabelTextSizeSmall;
    private int mNumRows;

    private int mPadding = 0;

    private Paint mMonthDayLabelPaint;
    private Paint mMonthNumPaint;
    private Paint mReservationInquiryStatusPaint;
    private Paint mMonthTitlePaint;
    private Paint mSelectedCirclePaint;
    private int mCurrentDayTextColor;
    private int mMonthTextColor;
    private int mDayTextColor;
    private int mDayNumColor;
    private int mDayWeekendColor;
    private int mDayInLateLeaveEarlyColor;
    private int mDayHolidayColor;
    private int mDayForgotCheckInCheckOutFiveTimeColor;
    private int mDayForgotCheckInCheckOutColor;
    private int mDayInLateLeaveEarlyHaveCompensationColor;
    private int mCurrentDayColor;
    private int mDayOffStrokeColor;

    private StringBuilder mStringBuilder;

    private boolean mHasToday = false;
    private boolean mIsPrev = false;
    private int mToday = -1;
    private int mWeekStart = 1;
    private int mNumDays = 7;
    private int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    private int mRowHeight = DEFAULT_HEIGHT;
    private int mWidth;
    private int mYear;
    private int mMonth;
    private Time today;

    private Calendar mCalendar;
    private Calendar mDayLabelCalendar;

    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols(Locale.US);

    private OnDayClickListener mOnDayClickListener;

    private Typeface mDayOfMonthTypeface;

    private List<TimeSheetDate> mTimeSheetDates;

    public TimeSheetView(Context context) {
        this(context, null);
    }

    public TimeSheetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSheetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.DatePickerView);

        Resources resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance(Locale.US);
        mCalendar = Calendar.getInstance(Locale.US);
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        mCurrentDayTextColor = typedArray.getColor(R.styleable.DatePickerView_colorCurrentDay,
                resources.getColor(android.R.color.black));
        mMonthTextColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.colorPrimary));
        mDayTextColor = typedArray.getColor(R.styleable.DatePickerView_colorDayName,
                resources.getColor(R.color.color_gray_cod));
        mDayNumColor = typedArray.getColor(R.styleable.DatePickerView_colorNormalDay,
                resources.getColor(R.color.color_gray_cod));
        mDayWeekendColor = typedArray.getColor(R.styleable.DatePickerView_colorWeekendDay,
                resources.getColor(R.color.color_dark_gray));
        mDayInLateLeaveEarlyColor =
                typedArray.getColor(R.styleable.DatePickerView_colorDayInLateLeaveEarly,
                        resources.getColor(R.color.color_red_fresh));
        mDayForgotCheckInCheckOutFiveTimeColor =
                typedArray.getColor(R.styleable.DatePickerView_colorDayInLateLeaveEarly,
                        resources.getColor(R.color.color_red_fresh));
        mDayHolidayColor = typedArray.getColor(R.styleable.DatePickerView_colorHoliday,
                resources.getColor(R.color.color_yellow_gold));
        mDayForgotCheckInCheckOutColor =
                typedArray.getColor(R.styleable.DatePickerView_colorDayForgotCheckInCheckOut,
                        resources.getColor(R.color.color_green_lime));
        mDayInLateLeaveEarlyHaveCompensationColor = typedArray.getColor(
                R.styleable.DatePickerView_colorDayInLateLeaveEarlyHaveCompensation,
                resources.getColor(R.color.color_pink));
        mCurrentDayColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_aquamarine));
        mDayOffStrokeColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_dark_gray));

        mStringBuilder = new StringBuilder(50);

        mMiniDayNumberTextSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeDay,
                        resources.getDimensionPixelSize(R.dimen.sp_14));
        mMonthLabelTextSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeMonth,
                        resources.getDimensionPixelSize(R.dimen.sp_20));
        mMonthDayLabelTextSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeDayName,
                        resources.getDimensionPixelSize(R.dimen.sp_9));
        mDayLabelTextSizeSmall =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeDayName,
                        resources.getDimensionPixelSize(R.dimen.sp_11));
        mMonthHeaderSize =
                typedArray.getDimensionPixelOffset(R.styleable.DatePickerView_headerMonthHeight,
                        resources.getDimensionPixelOffset(R.dimen.dp_80));
        mDaySelectedCircleSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_selectedDayRadius,
                        resources.getDimensionPixelOffset(R.dimen.dp_20));

        mRowHeight = ((typedArray.getDimensionPixelSize(R.styleable.DatePickerView_calendarHeight,
                resources.getDimensionPixelOffset(R.dimen.dp_300)) - mMonthHeaderSize) / 5);

        typedArray.recycle();

        initView();
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = mMonthHeaderSize - (mMonthDayLabelTextSize / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            mMonthDayLabelPaint.setColor(mDayTextColor);

            String dayOfWeek = mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(
                    Calendar.DAY_OF_WEEK)].toUpperCase();

            canvas.drawText(dayOfWeek.substring(0, 1), x, y, mMonthDayLabelPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (mMonthHeaderSize - mMonthDayLabelTextSize) / 2 + (mMonthLabelTextSize / 3);
        canvas.drawText(getMonthAndYearString(), x, y, mMonthTitlePaint);
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
    }

    private String getMonthAndYearString() {
        mStringBuilder.setLength(0);
        Date date = mCalendar.getTime();
        return DateTimeUtils.convertToString(date, DateTimeUtils.DATE_FORMAT_YYYY_MM_JAPANESE);
    }

    private boolean sameDay(int monthDay, Time time) {
        return (mYear == time.year) && (mMonth == (time.monthDay > DAY_25_OF_MONTH ? time.month + 1
                : time.month)) && (monthDay == time.monthDay);
    }

    private boolean prevDay(int monthDay, Time time) {
        return ((mYear < time.year)) || (mYear == time.year && mMonth < time.month) || (mMonth
                == time.month && monthDay < time.monthDay);
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (mRowHeight + mMiniDayNumberTextSize) / 2 - mDaySeparatorWidth + mMonthHeaderSize;
        int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 26;

        int dayCheck;
        int month = mMonth;
        int year = mYear;

        while (day <= mNumCells) {
            int x = paddingDay * (1 + dayOffset * 2) + mPadding;

            int range = DateTimeUtils.getDaysInMonth(mMonth - 1, mYear);
            if (day > range) {
                dayCheck = day - range;
                month = mMonth + 1;
                if (month > 12) {
                    month = 1;
                    year = mYear + 1;
                }
            } else {
                dayCheck = day;
            }

            Date date = new Date(year - 1900, month - 1, dayCheck);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (mHasToday && (mToday == dayCheck)) {
                mMonthNumPaint.setColor(mCurrentDayTextColor);
                mMonthNumPaint.setTypeface(Typeface.create(mDayOfMonthTypeface, Typeface.NORMAL));
                mSelectedCirclePaint.setColor(mCurrentDayColor);
                canvas.drawCircle(x, y - mMiniDayNumberTextSize / 3, mDaySelectedCircleSize,
                        mSelectedCirclePaint);
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                mMonthNumPaint.setColor(mDayWeekendColor);
                mMonthNumPaint.setTypeface(Typeface.create(mDayOfMonthTypeface, Typeface.NORMAL));
            } else {
                mSelectedCirclePaint.setColor(Color.TRANSPARENT);
                mMonthNumPaint.setColor(mDayNumColor);
                mMonthNumPaint.setTypeface(Typeface.create(mDayOfMonthTypeface, Typeface.NORMAL));
            }

            if (mTimeSheetDates != null && mTimeSheetDates.size() > 0) {
                for (TimeSheetDate timeSheetDate : mTimeSheetDates) {
                    if (date.equals(timeSheetDate.getDate())) {
                        if (timeSheetDate.getStatus() == TimeSheetDate.Status.NORMAL) {
                            mSelectedCirclePaint.setColor(Color.TRANSPARENT);
                            drawDayLabelNomal(canvas, y, day, x);
                            break;
                        }
                        switch (timeSheetDate.getStatus()) {
                            case IN_LATE_LEAVE_EARLY:
                                drawDayLabelNomal(canvas, y, day, x);
                                mSelectedCirclePaint.setColor(mDayInLateLeaveEarlyColor);
                                break;
                            case HOLIDAY_DATE:
                                mSelectedCirclePaint.setColor(mDayHolidayColor);
                                drawDayLabelNomal(canvas, y, day, x);
                                break;
                            case FORGOT_CHECK_IN_CHECK_OUT:
                                mSelectedCirclePaint.setColor(mDayForgotCheckInCheckOutColor);
                                drawDayLabelNomal(canvas, y, day, x);
                                break;
                            case IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION:
                                mSelectedCirclePaint.setColor(
                                        mDayInLateLeaveEarlyHaveCompensationColor);
                                drawDayLabelNomal(canvas, y, day, x);
                                break;
                            case FORGOT_CHECK_IN_CHECK_OUT_MORE_FIVE_TIME:
                                mSelectedCirclePaint.setColor(
                                        mDayForgotCheckInCheckOutFiveTimeColor);
                                drawDayLabelNomal(canvas, y, day, x);
                                break;
                            case DAY_OFF_RO:
                                mSelectedCirclePaint.setColor(mDayOffStrokeColor);
                                drawDayLabelRoP(canvas, y, day, x,
                                        getResources().getString(R.string.day_off_ro));
                                break;
                            case DAY_OFF_HALF_RO:
                                mSelectedCirclePaint.setColor(mDayOffStrokeColor);
                                drawDayLabelRoP(canvas, y, day, x,
                                        getResources().getString(R.string.day_off_half_ro));
                                break;
                            case DAY_OFF_P:
                                mSelectedCirclePaint.setColor(mDayOffStrokeColor);
                                drawDayLabelRoP(canvas, y, day, x,
                                        getResources().getString(R.string.day_off_p));
                                break;
                            case DAY_OFF_HALF_P:
                                mSelectedCirclePaint.setColor(mDayOffStrokeColor);
                                drawDayLabelRoP(canvas, y, day, x,
                                        getResources().getString(R.string.day_off_half_p));
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                }
            } else {
                drawDayLabelNomal(canvas, y, day, x);
            }
            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

    private void drawDayLabelRoP(Canvas canvas, int y, int day, int x, String label) {
        mSelectedCirclePaint.setStyle(Paint.Style.STROKE);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setStrokeWidth(2);
        mSelectedCirclePaint.setStyle(Paint.Style.STROKE);
        mSelectedCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mSelectedCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawCircle(x, y - mMiniDayNumberTextSize / 3, mDaySelectedCircleSize,
                mSelectedCirclePaint);
        mMonthNumPaint.setTextSize(mDayLabelTextSizeSmall);
        canvas.drawText(String.format("%d", day > DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) ?
                        day
                                - DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) : day), x,
                y - (float) (mMiniDayNumberTextSize / 2), mMonthNumPaint);
        mMonthNumPaint.setTextSize(mMonthDayLabelTextSize);
        canvas.drawText(label, x, y + mMiniDayNumberTextSize / 3, mMonthNumPaint);
    }

    private void drawDayLabelNomal(Canvas canvas, int y, int day, int x) {
        mSelectedCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y - mMiniDayNumberTextSize / 3, mDaySelectedCircleSize,
                mSelectedCirclePaint);
        mMonthNumPaint.setTextSize(mMiniDayNumberTextSize);
        canvas.drawText(String.format("%d", day > DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) ?
                        day
                                - DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) : day), x, y,
                mMonthNumPaint);
    }

    public Date getDayFromLocation(float x, float y) {
        int padding = mPadding;
        if ((x < padding) || (x > mWidth - mPadding)) {
            return null;
        }

        int yDay = (int) (y - mMonthHeaderSize) / mRowHeight;
        int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding))
                - findDayOffset()) + yDay * mNumDays;
        int month = mMonth;
        int year = mYear;

        int range = DateTimeUtils.getDaysInMonth(mMonth - 1, mYear);
        int delta = range - 25;
        if (day <= delta) {
            day = day + 25;
        } else {
            day = day - delta;
            month = month + 1;
            if (month > 12) {
                month = 1;
                year += 1;
            }
        }

        return new Date(year - 1900, month - 1, day);
    }

    protected void initView() {
        Typeface monthTitleTypeface =
                Typeface.createFromAsset(getContext().getAssets(), "fonts/HiraginoSans-W5.ttc");
        mDayOfMonthTypeface =
                Typeface.createFromAsset(getContext().getAssets(), "fonts/HiraginoSans-W3.ttc");

        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(mMonthLabelTextSize);
        mMonthTitlePaint.setTypeface(Typeface.create(monthTitleTypeface, Typeface.BOLD));
        mMonthTitlePaint.setColor(mMonthTextColor);
        mMonthTitlePaint.setTextAlign(Paint.Align.CENTER);
        mMonthTitlePaint.setStyle(Paint.Style.FILL);

        mReservationInquiryStatusPaint = new Paint();
        mReservationInquiryStatusPaint.setFakeBoldText(true);
        mReservationInquiryStatusPaint.setAntiAlias(true);
        mReservationInquiryStatusPaint.setTextAlign(Paint.Align.CENTER);
        mReservationInquiryStatusPaint.setStyle(Paint.Style.FILL);
        mReservationInquiryStatusPaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setTextAlign(Paint.Align.CENTER);
        mSelectedCirclePaint.setStyle(Paint.Style.FILL);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(mMonthDayLabelTextSize);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setTypeface(Typeface.create(monthTitleTypeface, Typeface.NORMAL));
        mMonthDayLabelPaint.setStyle(Paint.Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Paint.Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(mMiniDayNumberTextSize);
        mMonthNumPaint.setStyle(Paint.Style.FILL);
        mMonthNumPaint.setTextAlign(Paint.Align.CENTER);
        mMonthNumPaint.setFakeBoldText(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                mRowHeight * mNumRows + mMonthHeaderSize + mMonthHeaderSize / 8);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Date date = getDayFromLocation(event.getX(), event.getY());
            if (date == null) {
                return false;
            }
            for (TimeSheetDate timeSheetDate : mTimeSheetDates) {
                if (date.equals(timeSheetDate.getDate())) {
                    if (mOnDayClickListener != null) {
                        mOnDayClickListener.onDayClick(timeSheetDate);
                    }
                }
            }
        }
        return true;
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells - 25) / mNumDays;
        int remainder = (offset + mNumCells - 25) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    public void reuse() {
        mNumRows = calculateNumRows();
        requestLayout();
    }

    public void setTime(int month, int year) {

        mMonth = month;
        mYear = year;

        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.MONTH, mMonth == 0 ? 12 : mMonth - 1);
        calendar2.set(Calendar.YEAR, mYear);
        calendar2.set(Calendar.DAY_OF_MONTH, 26);
        mDayOfWeekStart = calendar2.get(Calendar.DAY_OF_WEEK);

        mWeekStart = mCalendar.getFirstDayOfWeek();

        mNumCells = DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) + 25;
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }

            mIsPrev = prevDay(day, today);
        }
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public void setTimeSheetDates(List<TimeSheetDate> timeSheetDates) {
        mTimeSheetDates = timeSheetDates;
    }
}
