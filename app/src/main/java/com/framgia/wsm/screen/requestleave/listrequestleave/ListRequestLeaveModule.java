package com.framgia.wsm.screen.requestleave.listrequestleave;

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
 * the {@link ListRequestLeavePresenter}.
 */
@Module
public class ListRequestLeaveModule {

    private Activity mActivity;

    public ListRequestLeaveModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ListRequestLeaveContract.ViewModel provideViewModel(Context context,
            ListRequestLeaveContract.Presenter presenter, DialogManager dialogManager) {
        return new ListRequestLeaveViewModel(context, presenter, dialogManager);
    }

    @ActivityScope
    @Provides
    public ListRequestLeaveContract.Presenter providePresenter() {
        return new ListRequestLeavePresenter();
    }

    @ActivityScope
    @Provides
    public DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }
}
