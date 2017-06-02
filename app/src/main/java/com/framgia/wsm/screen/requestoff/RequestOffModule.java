package com.framgia.wsm.screen.requestoff;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link RequestOffPresenter}.
 */
@Module
public class RequestOffModule {

    private Activity mActivity;

    public RequestOffModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public RequestOffContract.ViewModel provideViewModel(Context context,
            RequestOffContract.Presenter presenter, DialogManager dialogManager) {
        return new RequestOffViewModel(context, presenter, dialogManager);
    }

    @ActivityScope
    @Provides
    public RequestOffContract.Presenter providePresenter() {
        return new RequestOffPresenter();
    }

    @ActivityScope
    @Provides
    public DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }
}
