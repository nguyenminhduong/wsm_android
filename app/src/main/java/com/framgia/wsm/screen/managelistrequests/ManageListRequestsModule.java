package com.framgia.wsm.screen.managelistrequests;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ManageListRequestsPresenter}.
 */
@Module
public class ManageListRequestsModule {

    private Fragment mFragment;

    public ManageListRequestsModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public ManageListRequestsContract.ViewModel provideViewModel(
            ManageListRequestsContract.Presenter presenter) {
        return new ManageListRequestsViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public ManageListRequestsContract.Presenter providePresenter() {
        return new ManageListRequestsPresenter();
    }
}
