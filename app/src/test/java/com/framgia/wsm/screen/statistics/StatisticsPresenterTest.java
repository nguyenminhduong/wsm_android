package com.framgia.wsm.screen.statistics;

import com.framgia.wsm.data.model.Chart;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.data.source.StatisticsRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.Type;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.ErrorResponse;
import com.framgia.wsm.data.source.remote.api.response.StatisticsResponse;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nguyenhuy95dn on 8/25/2017.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class StatisticsPresenterTest {

    @InjectMocks
    StatisticsPresenter mPresenter;
    @Mock
    StatisticsViewModel mViewModel;
    @Mock
    StatisticsRepository mStatisticsRepository;
    @InjectMocks
    ImmediateSchedulerProvider mBaseSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        mPresenter.setViewModel(mViewModel);
        mPresenter.setBaseSchedulerProvider(mBaseSchedulerProvider);
    }

    @Test
    public void getStatistics_shouldSuccesss_whenInputValidData() throws Exception {
        // Give
        int currentYear = 2017;
        Chart chart = new Chart();
        chart.setName("Jun");
        chart.setDataChart(new float[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

        List<Chart> charts = new ArrayList<>();
        charts.add(chart);

        StatisticOfPersonal statisticOfPersonal = new StatisticOfPersonal();
        statisticOfPersonal.setDaysOff(new StatisticOfPersonal.DaysOff());

        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setStatisticOfChart(charts);
        statisticsResponse.setStatisticOfPersonal(statisticOfPersonal);
        BaseResponse<StatisticsResponse> response = new BaseResponse<>();
        response.setData(statisticsResponse);
        // When
        when(mStatisticsRepository.getStatistic(currentYear)).thenReturn(Single.just(response));
        // Then
        mPresenter.getStatistics(currentYear);

        verify(mViewModel, Mockito.never()).onGetStatisticsError(null);
        verify(mViewModel).onGetStatisticsSuccess(charts);
        verify(mViewModel).onGetStatisticsByMonthSuccess(statisticOfPersonal);
        verify(mViewModel).onGetStatisticsByYearSuccess(statisticOfPersonal);
    }

    @Test
    public void getStatistics_shouldError_whenInputValidDataAndNetworkEError()
            throws IllegalAccessException {
        // Give
        int currentYear = 2017;
        BaseException baseException = new BaseException(Type.HTTP, new ErrorResponse());
        // When
        when(mStatisticsRepository.getStatistic(currentYear)).thenReturn(
                Single.<BaseResponse<StatisticsResponse>>error(baseException));
        // Then
        mPresenter.getStatistics(currentYear);

        verify(mViewModel).onGetStatisticsError(baseException);
    }
}
