package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.source.remote.TimeSheetRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duong on 5/24/2017.
 */

public class TimeSheetRepository {
    private TimeSheetDataSource.RemoteDataSource mRemoteDataSource;

    public TimeSheetRepository(TimeSheetRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<TimeSheetResponse> getTimeSheet(int month, int year) {
        //todo delete later
        List<TimeSheetDate> timeSheetDates = new ArrayList<>();
        timeSheetDates.add(
                new TimeSheetDate(String.format("2017/04/26"), 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/04/27", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(
                new TimeSheetDate("2017/04/28", 8, 10, TimeSheetDate.Status.IN_LATE_LEAVE_EARLY));
        timeSheetDates.add(new TimeSheetDate("2017/04/29", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/04/30", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/1", 8, 10, TimeSheetDate.Status.DAY_OFF_P));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/2", 8, 10, TimeSheetDate.Status.DAY_OFF_HALF_P));
        timeSheetDates.add(new TimeSheetDate("2017/05/3", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/4", 8, 10, TimeSheetDate.Status.HOLIDAY_DATE));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/5", 8, 10, TimeSheetDate.Status.HOLIDAY_DATE));
        timeSheetDates.add(new TimeSheetDate("2017/05/6", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/7", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/8", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/9", 8, 10, TimeSheetDate.Status.DAY_OFF_RO));
        timeSheetDates.add(new TimeSheetDate("2017/05/10", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(
                new TimeSheetDate("2017/05/11", 8, 10, TimeSheetDate.Status.DAY_OFF_HALF_RO));
        timeSheetDates.add(new TimeSheetDate("2017/05/12", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/13", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/14", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/15", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/16", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/17", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/18", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/19", 8, 10,
                TimeSheetDate.Status.IN_LATE_LEAVE_EARLY_HAVE_COMPENSATION));
        timeSheetDates.add(new TimeSheetDate("2017/05/20", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/21", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/22", 8, 10,
                TimeSheetDate.Status.FORGOT_CHECK_IN_CHECK_OUT));
        timeSheetDates.add(new TimeSheetDate("2017/05/23", 8, 10, TimeSheetDate.Status.NORMAL));
        timeSheetDates.add(new TimeSheetDate("2017/05/24", 8, 10,
                TimeSheetDate.Status.FORGOT_CHECK_IN_CHECK_OUT_MORE_FIVE_TIME));
        timeSheetDates.add(new TimeSheetDate("2017/05/25", 8, 10, TimeSheetDate.Status.NORMAL));
        TimeSheetResponse timeSheetResponse = new TimeSheetResponse();
        timeSheetResponse.setTimeSheetDates(timeSheetDates);
        return Observable.just(timeSheetResponse);
    }
}
