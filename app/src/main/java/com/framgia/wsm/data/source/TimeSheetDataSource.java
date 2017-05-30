package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import io.reactivex.Observable;

/**
 * Created by Duong on 5/24/2017.
 */

public interface TimeSheetDataSource {

    interface RemoteDataSource {
        Observable<TimeSheetResponse> getTimeSheet(int month, int year);
    }
}
