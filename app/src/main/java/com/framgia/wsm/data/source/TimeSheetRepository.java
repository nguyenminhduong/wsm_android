package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.model.UserTimeSheet;
import com.framgia.wsm.data.source.remote.TimeSheetRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duong on 6/24/2017.
 */

public class TimeSheetRepository {
    private TimeSheetDataSource.RemoteDataSource mRemoteDataSource;

    public TimeSheetRepository(TimeSheetRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<BaseResponse<TimeSheetResponse>> getTimeSheet(int month, int year) {
        // todo edit later
        TimeSheetResponse timeSheetResponse = new TimeSheetResponse();
        UserTimeSheet userTimeSheet = new UserTimeSheet();
        List<TimeSheetDate> timeSheetDates = new ArrayList<>();
        timeSheetDates.add(
                new TimeSheetDate("2017/06/26", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/06/27", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/06/28", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/06/29", "8:00", "16:00", "Ro/2", null, "#00ff00", null));
        timeSheetDates.add(
                new TimeSheetDate("2017/06/30", "8:00", "16:00", "Ro", "Ro", null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/1", "8:00", "16:00", null, null, "#ff0000", "#00ff00"));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/2", "8:00", "16:00", null, null, "#ff0000", null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/3", "8:00", "16:00", null, null, null, "#00ff00"));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/4", "8:00", "16:00", null, null, "#00ff00", "#ff0000"));
        timeSheetDates.add(new TimeSheetDate("2017/07/5", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(new TimeSheetDate("2017/07/6", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(new TimeSheetDate("2017/07/7", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(new TimeSheetDate("2017/07/8", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(new TimeSheetDate("2017/07/9", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/10", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/11", "8:00", "16:00", "P/2", null, null, "#00ff00"));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/12", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/13", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/14", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/15", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/16", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/17", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/18", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/19", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/20", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/21", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/22", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/23", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/24", "8:00", "16:00", null, null, null, null));
        timeSheetDates.add(
                new TimeSheetDate("2017/07/25", "8:00", "16:00", null, null, null, null));
        userTimeSheet.setTimeSheetDates(timeSheetDates);
        timeSheetResponse.setUserTimeSheet(userTimeSheet);
        BaseResponse<TimeSheetResponse> timeSheetResponseBaseResponse =
                new BaseResponse<>(timeSheetResponse);
        return Observable.just(timeSheetResponseBaseResponse);
    }
}
