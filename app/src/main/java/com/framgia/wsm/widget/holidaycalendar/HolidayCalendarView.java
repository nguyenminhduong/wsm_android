package com.framgia.wsm.widget.holidaycalendar;

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
import com.framgia.wsm.data.model.HolidayCalendarDate;
import com.framgia.wsm.utils.common.DateTimeUtils;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HolidayCalendarView extends View {

    private static final int SELECTED_CIRCLE_ALPHA = 128;
    private static final int DEFAULT_HEIGHT = 32;
    private int mDaySelectedCircleSize;
    private int mDaySeparatorWidth = 1;
    private int mMiniDayNumberTextSize;
    private int mMonthDayLabelTextSize;
    private int mMonthHeaderSize;
    private int mMonthLabelTextSize;
    private int mNumRows;

    private int mPadding = 0;

    private Paint mMonthDayLabelPaint;
    private Paint mMonthNumPaint;
    private Paint mReservationInquiryStatusPaint;
    private Paint mMonthTitlePaint;
    private Paint mSelectedCirclePaint;
    private int mMonthTextColor;
    private int mDayTextColor;
    private int mSaturdaySundayColor;
    private int mCompanyHoliday;
    private int mNormalHoliday;

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

    private HolidayDateClickListener mOnDayClickListener;

    private Typeface mDayOfMonthTypeface;

    private List<HolidayCalendarDate> mHolidayCalendarDates;

    public HolidayCalendarView(Context context) {
        this(context, null);
    }

    public HolidayCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HolidayCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.DatePickerView);

        Resources resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance(Locale.US);
        mCalendar = Calendar.getInstance(Locale.US);
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        mMonthTextColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.colorPrimary));
        mDayTextColor = typedArray.getColor(R.styleable.DatePickerView_colorDayName,
                resources.getColor(R.color.color_gray_cod));
        mNormalHoliday = typedArray.getColor(R.styleable.DatePickerView_colorDayInLateLeaveEarly,
                resources.getColor(R.color.color_red_fresh));
        mSaturdaySundayColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_gray));
        mCompanyHoliday = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_light_teal));

        mMiniDayNumberTextSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeDay,
                        resources.getDimensionPixelSize(R.dimen.sp_14));
        mMonthLabelTextSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeMonth,
                        resources.getDimensionPixelSize(R.dimen.sp_25));
        mMonthDayLabelTextSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_textSizeDayName,
                        resources.getDimensionPixelSize(R.dimen.sp_9));
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
                    Calendar.DAY_OF_WEEK)].toUpperCase(Locale.US);

            canvas.drawText(dayOfWeek.substring(0, 1), x, y, mMonthDayLabelPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (mMonthHeaderSize - mMonthDayLabelTextSize) / 2 + (mMonthLabelTextSize / 3);
        canvas.drawText(getMonthName(), x, y, mMonthTitlePaint);
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
    }

    private String getMonthName() {
        Date date = mCalendar.getTime();
        return DateTimeUtils.convertToString(date, DateTimeUtils.DATE_FORMAT_MMMM);
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (mRowHeight + mMiniDayNumberTextSize) / 2 - mDaySeparatorWidth + mMonthHeaderSize;
        int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 1;
        int month = mMonth;
        int year = mYear;

        while (day <= mNumCells) {
            int x = paddingDay * (1 + dayOffset * 2) + mPadding;

            Date date = new Date(year - 1900, month, day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                mMonthNumPaint.setColor(Color.WHITE);
                mMonthNumPaint.setTypeface(Typeface.create(mDayOfMonthTypeface, Typeface.NORMAL));
                mSelectedCirclePaint.setColor(mSaturdaySundayColor);
            } else {
                mMonthNumPaint.setColor(mDayTextColor);
                mSelectedCirclePaint.setColor(Color.TRANSPARENT);
            }

            if (mHolidayCalendarDates != null && mHolidayCalendarDates.size() > 0) {
                for (HolidayCalendarDate holidayCalendarDate : mHolidayCalendarDates) {
                    if (date.equals(holidayCalendarDate.getDate())) {
                        switch (holidayCalendarDate.getStatus()) {
                            case NORMAL:
                                mMonthNumPaint.setColor(mDayTextColor);
                                mSelectedCirclePaint.setColor(Color.TRANSPARENT);
                                break;
                            case NORMAL_HOLIDAY:
                                mMonthNumPaint.setColor(Color.WHITE);
                                mSelectedCirclePaint.setColor(mNormalHoliday);
                                break;
                            case COMPANY_HOLIDAY:
                                mMonthNumPaint.setColor(Color.WHITE);
                                mSelectedCirclePaint.setColor(mCompanyHoliday);
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                }
            }
            drawDayLabelNomal(canvas, y, day, x);
            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

    private void drawDayLabelNomal(Canvas canvas, int y, int day, int x) {
        mSelectedCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y - mMiniDayNumberTextSize / 3, mDaySelectedCircleSize,
                mSelectedCirclePaint);
        mMonthNumPaint.setTextSize(mMiniDayNumberTextSize);
        canvas.drawText(String.format(Locale.US, "%d",
                day > DateTimeUtils.getDaysInMonth(mMonth, mYear) ? day
                        - DateTimeUtils.getDaysInMonth(mMonth, mYear) : day), x, y, mMonthNumPaint);
    }

    public Date getDayFromLocation(float x, float y) {
        int padding = mPadding;
        if ((x < padding) || (x > mWidth - mPadding)) {
            return null;
        }
        int yDay = (int) (y - mMonthHeaderSize) / mRowHeight;
        int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding))
                - findDayOffset()) + yDay * mNumDays;
        if (mMonth > 11
                || mMonth < 0
                || DateTimeUtils.getDaysInMonth(mMonth, mYear) < day
                || day < 1) {
            return null;
        }
        return new Date(mYear - 1900, mMonth, day);
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
        mSelectedCirclePaint.setColor(Color.WHITE);
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
            performClick();
            Date date = getDayFromLocation(event.getX(), event.getY());
            if (date == null) {
                return false;
            }
            for (HolidayCalendarDate holidayCalendarDate : mHolidayCalendarDates) {
                if (date.equals(holidayCalendarDate.getDate()) && mOnDayClickListener != null) {
                    mOnDayClickListener.onDayClick(holidayCalendarDate);
                }
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    public void reuse() {
        mNumRows = calculateNumRows();
        requestLayout();
    }

    public void setTime(int month, int year) {
        mMonth = month;
        mYear = year;
        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);
        mWeekStart = mCalendar.getFirstDayOfWeek();
        mNumCells = DateTimeUtils.getDaysInMonth(mMonth, mYear);
        mNumRows = calculateNumRows();
    }

    public void setOnDayClickListener(HolidayDateClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public void setHolidayCalendarDates(List<HolidayCalendarDate> holidayCalendarDates) {
        mHolidayCalendarDates = holidayCalendarDates;
    }
}
