package com.framgia.wsm.screen.updateprofile;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link UpdateProfilePresenter}.
 */
@Module
public class UpdateProfileModule {

    private Activity mActivity;

    public UpdateProfileModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public UpdateProfileContract.ViewModel provideViewModel(Context context, Navigator navigator,
            DialogManager dialogManager, UpdateProfileContract.Presenter presenter) {
        User user = mActivity.getIntent().getParcelableExtra(Constant.EXTRA_USER);
        return new UpdateProfileViewModel(context, user, navigator, dialogManager, presenter);
    }

    @ActivityScope
    @Provides
    public UpdateProfileContract.Presenter providePresenter() {
        return new UpdateProfilePresenter();
    }

    @ActivityScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }
}
