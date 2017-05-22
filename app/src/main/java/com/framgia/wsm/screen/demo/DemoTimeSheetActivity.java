package com.framgia.wsm.screen.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.widget.timesheet.OnDayClickListener;
import com.framgia.wsm.widget.timesheet.TimeSheetView;
import java.util.ArrayList;
import java.util.List;

/**
 * DemoTimeSheetActivity
 */
public class DemoTimeSheetActivity extends AppCompatActivity {

    private static final String TAG = "DemoTimeSheetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_time_sheet);

        TimeSheetView timeSheetView = (TimeSheetView) findViewById(R.id.timeSheetView);

        timeSheetView.reuse();

        timeSheetView.setTime(4, 2017);

        List<TimeSheetDate> timeSheetDates = new ArrayList<>();
        timeSheetDates.add(
                new TimeSheetDate("2017/04/28", 8, 10, TimeSheetDate.Status.IN_LATE_LEAVE_EARLY));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/2", 8, 10, TimeSheetDate.Status.HOLIDAY_DATE));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/8", 8, 10, TimeSheetDate.Status.DATE_OFF_HAVE_SALARY));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/10", 8, 10, TimeSheetDate.Status.DATE_OFF_NO_SALARY));
        timeSheetDates.add(new TimeSheetDate("2017/05/12", 8, 10,
                TimeSheetDate.Status.FORGOT_CHECK_IN_CHECK_OUT));
        timeSheetDates.add(new TimeSheetDate("2017/05/15", 8, 10,
                TimeSheetDate.Status.IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION));

        timeSheetView.setTimeSheetDates(timeSheetDates);
        timeSheetView.invalidate();

        timeSheetView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(TimeSheetDate timeSheetDate) {
                Toast.makeText(DemoTimeSheetActivity.this, timeSheetDate.getDateString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
