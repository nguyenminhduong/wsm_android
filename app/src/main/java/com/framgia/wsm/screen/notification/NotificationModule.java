package com.framgia.wsm.screen.notification;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link NotificationPresenter}.
 */
@Module
public class NotificationModule {

    private Fragment mFragment;

    public NotificationModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public NotificationContract.ViewModel provideViewModel(NotificationContract.Presenter presenter,
            NotificationAdapter notificationAdapter) {
        return new NotificationViewModel(presenter, notificationAdapter);
    }

    @FragmentScope
    @Provides
    public NotificationContract.Presenter providePresenter() {
        return new NotificationPresenter();
    }

    @FragmentScope
    @Provides
    public NotificationAdapter provideNotificationAdapter() {
        return new NotificationAdapter(mFragment.getActivity());
    }
}
