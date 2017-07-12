package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ths on 11/07/2017.
 */

@Module
public class MemberRequestDetailModule {

    private Fragment mFragment;
    private DismissDialogListener mDismissDialogListener;

    public MemberRequestDetailModule(@NonNull Fragment fragment) {
        mFragment = fragment;
        mDismissDialogListener = (DismissDialogListener) fragment;
    }

    @FragmentScope
    @Provides
    public MemberRequestDetailContract.ViewModel provideViewModel(Context context,
            MemberRequestDetailContract.Presenter presenter, Navigator navigator) {
        return new MemberRequestDetailViewModel(context, mFragment, presenter, navigator);
    }

    @FragmentScope
    @Provides
    public MemberRequestDetailContract.Presenter providePresenter(
            BaseSchedulerProvider baseSchedulerProvider) {
        return new MemberRequestDetailPresenter(baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mFragment);
    }
}
