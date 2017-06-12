package com.framgia.wsm.screen.listrequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ListRequestPresenter}.
 */
@Module
public class ListRequestModule {

    private Fragment mFragment;

    public ListRequestModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public ListRequestContract.ViewModel provideViewModel(Context context,
            ListRequestContract.Presenter presenter, DialogManager dialogManager) {
        return new ListRequestViewModel(context, presenter, dialogManager);
    }

    @FragmentScope
    @Provides
    public ListRequestContract.Presenter providePresenter() {
        return new ListRequestPresenter();
    }

    @FragmentScope
    @Provides
    public DialogManager provideDialogManager() {
        return new DialogManagerImpl(mFragment.getActivity());
    }
}
