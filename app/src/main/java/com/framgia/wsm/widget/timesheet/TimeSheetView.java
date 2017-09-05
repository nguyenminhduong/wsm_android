package com.framgia.wsm.widget.timesheet;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

/**
 * TimeSheetView
 */
public class TimeSheetView extends View {

    private static final int SELECTED_CIRCLE_ALPHA = 128;
    private static final int DEFAULT_HEIGHT = 32;
    private static final int CALENDAR_DECEMBER = 11;
    private static final String FORMAT_COLOR_HEX = "#%06X";
    private static final Character PREFIX_DAY_OFF_P = 'P';
    private static final Character PREFIX_DAY_OFF_RO = 'R';

    private int mDayCircleSize;
    private int mCurrentDayCircleSize;
    private int mDaySeparatorWidth = 1;
    private int mMiniDayNumberTextSize;
    private int mMonthDayLabelTextSize;
    private int mMonthHeaderSize;
    private int mMonthLabelTextSize;
    private int mDayLabelTextSizeSmall;
    private int mNumRows;
    private int mCutOffDate;
    private int mFirstDateOfMonth;

    private Paint mMonthDayLabelPaint;
    private Paint mMonthNumPaint;
    private Paint mReservationInquiryStatusPaint;
    private Paint mMonthTitlePaint;
    private Paint mCircleMorningPaint;
    private Paint mCircleAfternoonPaint;
    private Paint mCurrentDayCirclePaint;
    private Paint mCircleStrokeOutPaint;
    private Paint mCircleOvertimePaint;
    private int mCurrentDayTextColor;
    private int mMonthTextColor;
    private int mDayTextColor;
    private int mDayNumColor;
    private int mDayWeekendColor;
    private int mCurrentDayColor;
    private int mDayOffStrokeColor;
    private int mDayOffRoColor;
    private int mDayOffPColor;
    private int mOvertimeColor;

    private StringBuilder mStringBuilder;

    private boolean mHasToday = false;
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
                resources.getColor(R.color.color_gray_light));
        mCurrentDayColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.colorPrimary));
        mDayOffStrokeColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_dark_gray));
        mDayOffPColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_green_teal));
        mDayOffRoColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_orange));
        mOvertimeColor = typedArray.getColor(R.styleable.DatePickerView_colorMonthName,
                resources.getColor(R.color.color_blue));

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
        mDayCircleSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_selectedDayRadius,
                        resources.getDimensionPixelOffset(R.dimen.dp_20));
        mCurrentDayCircleSize =
                typedArray.getDimensionPixelSize(R.styleable.DatePickerView_selectedDayRadius,
                        resources.getDimensionPixelOffset(R.dimen.dp_3));
        mRowHeight = ((typedArray.getDimensionPixelSize(R.styleable.DatePickerView_calendarHeight,
                resources.getDimensionPixelOffset(R.dimen.dp_300)) - mMonthHeaderSize) / 5);

        typedArray.recycle();

        initView();
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = mMonthHeaderSize - (mMonthDayLabelTextSize / 2);
        int dayWidthHalf = (mWidth) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            mMonthDayLabelPaint.setColor(mDayTextColor);

            String dayOfWeek = mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(
                    Calendar.DAY_OF_WEEK)].toUpperCase();

            canvas.drawText(dayOfWeek.substring(0, 1), x, y, mMonthDayLabelPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2) / 2;
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
        if (mFirstDateOfMonth == 1) {
            date.setMonth(date.getMonth() - 1);
        }
        return DateTimeUtils.convertToString(date, DateTimeUtils.DATE_FORMAT_YYYY_MM_JAPANESE);
    }

    private boolean sameDay(int monthDay, Time time) {
        if (mFirstDateOfMonth == 1) {
            return (mYear == time.year) && (mMonth
                    == (time.monthDay > mCutOffDate ? time.month + 1 : time.month) + 1) && (monthDay
                    == time.monthDay);
        }
        return (mYear == time.year) && (mMonth == (time.monthDay > mCutOffDate ? time.month + 1
                : time.month)) && (monthDay == time.monthDay);
    }

    private boolean prevDay(int monthDay, Time time) {
        return ((mYear < time.year)) || (mYear == time.year && mMonth < time.month) || (mMonth
                == time.month && monthDay < time.monthDay);
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (mRowHeight + mMiniDayNumberTextSize) / 2 - mDaySeparatorWidth + mMonthHeaderSize;
        int paddingDay = (mWidth - 2) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = mFirstDateOfMonth;

        int dayCheck;
        int month = mMonth;
        int year = mYear;
        boolean isWeekend = false;

        while (day <= mNumCells) {
            int x = paddingDay * (1 + dayOffset * 2);

            int range;
            if (mMonth == 0) {
                range = DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1);
            } else {
                range = DateTimeUtils.getDaysInMonth(mMonth - 1, mYear);
            }
            if (day > range) {
                dayCheck = day - range;
                month = mMonth + 1;
                if (month > 12) {
                    month = 0;
                    year = mYear + 1;
                }
            } else {
                dayCheck = day;
            }
            if (day > range
                    && month == Calendar.JANUARY
                    && cutOffDateIsLessThanHalfTotalDateOfMonth(month, mYear)) {
                month = 1;
            }
            Date date = new Date(year - 1900, month - 1, dayCheck);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (mHasToday && (mToday == dayCheck)) {
                mMonthNumPaint.setColor(mCurrentDayTextColor);
                mMonthNumPaint.setTypeface(Typeface.create(mDayOfMonthTypeface, Typeface.BOLD));
                mCurrentDayCirclePaint.setColor(mCurrentDayColor);
            } else {
                mCurrentDayCirclePaint.setColor(Color.TRANSPARENT);
                mMonthNumPaint.setColor(mDayNumColor);
                mMonthNumPaint.setTypeface(Typeface.create(mDayOfMonthTypeface, Typeface.NORMAL));
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                isWeekend = true;
            } else {
                mCircleMorningPaint.setColor(Color.TRANSPARENT);
                mCircleAfternoonPaint.setColor(Color.TRANSPARENT);
                isWeekend = false;
            }

            if (mTimeSheetDates != null && mTimeSheetDates.size() > 0) {
                for (TimeSheetDate timeSheetDate : mTimeSheetDates) {
                    if (date.equals(timeSheetDate.getDate())) {
                        if (isWeekend && !timeSheetDate.isCompensationHoliday()) {
                            mCircleMorningPaint.setColor(
                                    Color.parseColor(timeSheetDate.getColorSpecialDate()));
                            mCircleAfternoonPaint.setColor(
                                    Color.parseColor(timeSheetDate.getColorSpecialDate()));
                            timeSheetDate.setColorMorning(timeSheetDate.getColorSpecialDate());
                            timeSheetDate.setColorAfternoon(timeSheetDate.getColorSpecialDate());
                        } else {
                            mCircleMorningPaint.setColor(
                                    Color.parseColor(timeSheetDate.getColorMorning()));
                            mCircleAfternoonPaint.setColor(
                                    Color.parseColor(timeSheetDate.getColorAfternoon()));
                        }
                        if (timeSheetDate.isDayOffMorning()) {
                            if (timeSheetDate.getTextMorning().charAt(0) == PREFIX_DAY_OFF_P) {
                                mCircleMorningPaint.setColor(mDayOffPColor);
                                timeSheetDate.setColorMorning(String.format(FORMAT_COLOR_HEX,
                                        (0xFFFFFF & mDayOffPColor)));
                            }
                            if (timeSheetDate.getTextMorning().charAt(0) == PREFIX_DAY_OFF_RO) {
                                mCircleMorningPaint.setColor(mDayOffRoColor);
                                timeSheetDate.setColorMorning(String.format(FORMAT_COLOR_HEX,
                                        (0xFFFFFF & mDayOffRoColor)));
                            }
                        }
                        if (timeSheetDate.isDayOffAfternoon()) {
                            if (timeSheetDate.getTextAfternoon().charAt(0) == PREFIX_DAY_OFF_P) {
                                mCircleAfternoonPaint.setColor(mDayOffPColor);
                                timeSheetDate.setColorAfternoon(String.format(FORMAT_COLOR_HEX,
                                        (0xFFFFFF & mDayOffPColor)));
                            }
                            if (timeSheetDate.getTextAfternoon().charAt(0) == PREFIX_DAY_OFF_RO) {
                                mCircleAfternoonPaint.setColor(mDayOffRoColor);
                                timeSheetDate.setColorAfternoon(String.format(FORMAT_COLOR_HEX,
                                        (0xFFFFFF & mDayOffRoColor)));
                            }
                        }
                        drawCircleDay(canvas, y, x);
                        drawCircleStrokeOut(canvas, y, x);
                        if (timeSheetDate.getNumberOfOvertime() > 0) {
                            drawCircleOvertime(canvas, y, x);
                        }
                        drawCircleCurrentDay(canvas, y, x);
                        mCircleMorningPaint.setColor(Color.TRANSPARENT);
                        mCircleAfternoonPaint.setColor(Color.TRANSPARENT);
                        if (timeSheetDate.isSpecialCase()) {
                            drawTextSpecialCase(canvas, y, day, x, timeSheetDate.getTextMorning());
                        } else {
                            drawTextNormal(canvas, y, day, x);
                        }
                        break;
                    }
                }
            } else {
                drawCircleStrokeOut(canvas, y, x);
                drawCircleCurrentDay(canvas, y, x);
                drawTextNormal(canvas, y, day, x);
            }
            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

    private void drawCircleStrokeOut(Canvas canvas, int y, int x) {
        canvas.drawCircle(x, y - mMiniDayNumberTextSize / 3, mDayCircleSize, mCircleStrokeOutPaint);
    }

    private void drawTextNormal(Canvas canvas, int y, int day, int x) {
        mMonthNumPaint.setTextSize(mMiniDayNumberTextSize);
        if (mMonth == 0) {
            canvas.drawText(String.format(Locale.US, "%d",
                    day > DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1) ? day
                            - DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1) : day), x,
                    y, mMonthNumPaint);
        } else {
            canvas.drawText(String.format(Locale.US, "%d",
                    day > DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) ? day
                            - DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) : day), x, y,
                    mMonthNumPaint);
        }
    }

    private void drawTextSpecialCase(Canvas canvas, int y, int day, int x, String label) {
        mMonthNumPaint.setTextSize(mDayLabelTextSizeSmall);
        if (mMonth == 0) {
            canvas.drawText(String.format(Locale.US, "%d",
                    day > DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1) ? day
                            - DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1) : day), x,
                    y - (float) (mMiniDayNumberTextSize / 2), mMonthNumPaint);
        } else {
            canvas.drawText(String.format(Locale.US, "%d",
                    day > DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) ? day
                            - DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) : day), x,
                    y - (float) (mMiniDayNumberTextSize / 2), mMonthNumPaint);
        }
        mMonthNumPaint.setTextSize(mMonthDayLabelTextSize);
        canvas.drawText(label, x, y + mMiniDayNumberTextSize / 3, mMonthNumPaint);
    }

    private void drawCircleCurrentDay(Canvas canvas, int y, int x) {
        mCurrentDayCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y - mMiniDayNumberTextSize * 4 / 3, mCurrentDayCircleSize,
                mCurrentDayCirclePaint);
    }

    private void drawCircleOvertime(Canvas canvas, int y, int x) {
        canvas.drawCircle(x, y - mMiniDayNumberTextSize / 3, mDayCircleSize, mCircleOvertimePaint);
    }

    private void drawCircleDay(Canvas canvas, int y, int x) {
        mCircleMorningPaint.setStyle(Paint.Style.FILL);
        mCircleAfternoonPaint.setStyle(Paint.Style.FILL);
        RectF rectf = new RectF(x - mDayCircleSize, y - mDayCircleSize - mMiniDayNumberTextSize / 3,
                x + mDayCircleSize, y + mDayCircleSize / 2 + mMiniDayNumberTextSize / 3);
        canvas.drawArc(rectf, 90, 180, true, mCircleMorningPaint);
        canvas.drawArc(rectf, -90, 180, true, mCircleAfternoonPaint);
    }

    public Date getDayFromLocation(float x, float y) {
        if ((x < 0) || (x > mWidth)) {
            return null;
        }

        int yDay = (int) (y - mMonthHeaderSize) / mRowHeight;
        int day = 1 + ((int) ((x) * mNumDays / (mWidth)) - findDayOffset()) + yDay * mNumDays;
        int month = mMonth;
        if (mFirstDateOfMonth == 1) {
            month = month - 1;
        }
        int year = mYear;

        int range;
        if (month == 0) {
            range = DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1);
        } else {
            range = DateTimeUtils.getDaysInMonth(month - 1, mYear);
        }
        int delta = range - mCutOffDate;
        if (delta < 0) {
            delta = 0;
        }
        if (day <= delta) {
            day = day + mCutOffDate;
        } else {
            day = day - delta;
            month = month + 1;
            if (month > 12) {
                month = 1;
                year += 1;
            }
        }

        if (mFirstDateOfMonth == 1 && day > DateTimeUtils.getDaysInMonth(month - 1, year)) {
            return null;
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

        mCircleMorningPaint = new Paint();
        mCircleMorningPaint.setFakeBoldText(true);
        mCircleMorningPaint.setAntiAlias(true);
        mCircleMorningPaint.setTextAlign(Paint.Align.CENTER);
        mCircleMorningPaint.setStyle(Paint.Style.FILL);

        mCircleAfternoonPaint = new Paint();
        mCircleAfternoonPaint.setFakeBoldText(true);
        mCircleAfternoonPaint.setAntiAlias(true);
        mCircleAfternoonPaint.setTextAlign(Paint.Align.CENTER);
        mCircleAfternoonPaint.setStyle(Paint.Style.FILL);

        mCircleStrokeOutPaint = new Paint();
        mCircleStrokeOutPaint.setFakeBoldText(true);
        mCircleStrokeOutPaint.setAntiAlias(true);
        mCircleStrokeOutPaint.setTextAlign(Paint.Align.CENTER);
        mCircleStrokeOutPaint.setStyle(Paint.Style.STROKE);
        mCircleStrokeOutPaint.setColor(mDayOffStrokeColor);
        mCircleStrokeOutPaint.setStrokeWidth(2);
        mCircleStrokeOutPaint.setStrokeJoin(Paint.Join.ROUND);
        mCircleStrokeOutPaint.setStrokeCap(Paint.Cap.ROUND);

        mCircleOvertimePaint = new Paint();
        mCircleOvertimePaint.setFakeBoldText(true);
        mCircleOvertimePaint.setAntiAlias(true);
        mCircleOvertimePaint.setTextAlign(Paint.Align.CENTER);
        mCircleOvertimePaint.setStyle(Paint.Style.STROKE);
        mCircleOvertimePaint.setColor(mOvertimeColor);
        mCircleOvertimePaint.setStrokeWidth(5);
        mCircleOvertimePaint.setStrokeJoin(Paint.Join.ROUND);
        mCircleOvertimePaint.setStrokeCap(Paint.Cap.ROUND);

        mCurrentDayCirclePaint = new Paint();
        mCurrentDayCirclePaint.setFakeBoldText(true);
        mCurrentDayCirclePaint.setAntiAlias(true);
        mCurrentDayCirclePaint.setTextAlign(Paint.Align.CENTER);
        mCurrentDayCirclePaint.setStyle(Paint.Style.FILL);

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
                if (timeSheetDate.getDate() == null) {
                    return false;
                }
                if (date.equals(timeSheetDate.getDate())) {
                    if (mOnDayClickListener != null) {
                        mOnDayClickListener.onDayClick(timeSheetDate);
                        break;
                    }
                }
            }
        }
        return true;
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells - mCutOffDate) / mNumDays;
        int remainder = (offset + mNumCells - mCutOffDate) % mNumDays;
        if (mFirstDateOfMonth == 1) {
            dividend = (offset + mNumCells) / mNumDays;
            remainder = (offset + mNumCells) % mNumDays;
        }
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    public void reuse() {
        mNumRows = calculateNumRows();
        requestLayout();
    }

    public void setCutOffDate(int month, int year, int cutOffDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        int totalDateOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (totalDateOfMonth > cutOffDate) {
            mCutOffDate = cutOffDate;
            return;
        }
        mCutOffDate = totalDateOfMonth;
    }

    public void setFirstDateOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (month == Calendar.JANUARY) {
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.YEAR, year - 1);
        } else {
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.YEAR, year);
        }

        int totalDateOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (totalDateOfMonth <= mCutOffDate) {
            mFirstDateOfMonth = 1;
            return;
        }
        mFirstDateOfMonth = mCutOffDate + 1;
    }

    public void setTime(int month, int year, int cutOffDate, int totalDateCompensationInMonth) {

        setCutOffDate(month, year, cutOffDate);
        setFirstDateOfMonth(month, year);

        mMonth = month;
        if (mFirstDateOfMonth == 1) {
            mMonth = mMonth + 1;
        }
        mYear = year;

        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar calendarFirstDateOfMonth = Calendar.getInstance();
        int addDay = mFirstDateOfMonth == 1 ? 0 : mCutOffDate;
        int monthCheck = mFirstDateOfMonth == 1 ? mMonth - 1 : mMonth;

        if (monthCheck == 0) {
            calendarFirstDateOfMonth.set(Calendar.MONTH, CALENDAR_DECEMBER);
            if (cutOffDateIsLessThanHalfTotalDateOfMonth(monthCheck, mYear)) {
                calendarFirstDateOfMonth.set(Calendar.YEAR, mYear);
            } else {
                calendarFirstDateOfMonth.set(Calendar.YEAR, mYear - 1);
            }
            mNumCells = DateTimeUtils.getDaysInMonth(CALENDAR_DECEMBER, mYear - 1) + addDay;
            if (mFirstDateOfMonth == 1) {
                calendarFirstDateOfMonth.set(Calendar.MONTH, 0);
                calendarFirstDateOfMonth.set(Calendar.YEAR, mYear);
                mNumCells = DateTimeUtils.getDaysInMonth(0, mYear) + addDay;
            }
        } else {
            calendarFirstDateOfMonth.set(Calendar.MONTH, mMonth - 1);
            calendarFirstDateOfMonth.set(Calendar.YEAR, mYear);
            mNumCells = DateTimeUtils.getDaysInMonth(mMonth - 1, mYear) + addDay;
        }

        int totalDateOfMonth = DateTimeUtils.getDaysInMonth(monthCheck, mYear);
        if (mFirstDateOfMonth == 1 && totalDateOfMonth > mCutOffDate) {
            mNumCells = mCutOffDate;
        }

        calendarFirstDateOfMonth.set(Calendar.DAY_OF_MONTH, mFirstDateOfMonth);

        mWeekStart = mCalendar.getFirstDayOfWeek();

        if (cutOffDateIsLessThanHalfTotalDateOfMonth(monthCheck, mYear)) {
            calendarFirstDateOfMonth.set(Calendar.MONTH, mMonth);
            mNumCells = DateTimeUtils.getDaysInMonth(mMonth, mYear) + addDay;
            mMonth++;
        }
        mDayOfWeekStart = calendarFirstDateOfMonth.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }
        }

        mNumCells += totalDateCompensationInMonth;
    }

    private boolean cutOffDateIsLessThanHalfTotalDateOfMonth(int month, int year) {
        int totalDateOfMonth = DateTimeUtils.getDaysInMonth(month, year);
        return mCutOffDate <= totalDateOfMonth / 2f;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public void setTimeSheetDates(List<TimeSheetDate> timeSheetDates) {
        mTimeSheetDates = timeSheetDates;
    }
}
