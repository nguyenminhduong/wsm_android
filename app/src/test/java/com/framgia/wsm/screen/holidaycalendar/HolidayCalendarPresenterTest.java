package com.framgia.wsm.screen.holidaycalendar;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.source.HolidayCalendarRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.utils.rx.ImmediateSchedulerProvider;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by framgia on 30/08/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class HolidayCalendarPresenterTest {
    @Mock
    HolidayCalendarRepository mHolidayCalendarRepository;
    @InjectMocks
    private HolidayCalendarPresenter mPresenter;
    @InjectMocks
    private ImmediateSchedulerProvider mSchedulerProvider;
    @Mock
    private HolidayCalendarViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mSchedulerProvider);
    }

    @Test
    public void getHolidayCalendar_shouldReturnListHolidayCalendar_whenGetHolidayCalendarSuccess()
            throws Exception {
        //Give
        int year = 2017;
        List<HolidayCalendar> holidayCalendars = new ArrayList<>();
        BaseResponse<List<HolidayCalendar>> baseResponse = new BaseResponse<>(holidayCalendars);
        //When
        Mockito.when(mHolidayCalendarRepository.getHolidayCalendar(year))
                .thenReturn(Single.just(baseResponse));
        //Then
        mPresenter.getHolidayCalendar(year);
        //Actual
        Mockito.verify(mViewModel).onGetHolidayCalendarSuccess(holidayCalendars);
    }

    @Test(expected = BaseException.class)
    public void getHolidayCalendar_shouldReturnError_whenGetHolidayCalendarError()
            throws Exception {
        //Give
        int year = 2017;
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        //When
        Mockito.when(mHolidayCalendarRepository.getHolidayCalendar(year)).thenThrow(baseException);
        //Then
        mPresenter.getHolidayCalendar(year);
        //Actual
        Mockito.verify(mViewModel).onGetHolidayCalendarError(baseException);
    }
}
