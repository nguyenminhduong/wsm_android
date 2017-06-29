package com.framgia.wsm.screen.notification;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentNotificationBinding;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * Notification Screen.
 */
public class NotificationDialogFragment extends DialogFragment {
    public static final String TAG = "NotificationDialogFragment";
    @Inject
    NotificationContract.ViewModel mViewModel;

    public static NotificationDialogFragment newInstance() {
        return new NotificationDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        DaggerNotificationComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .notificationModule(new NotificationModule(this))
                .build()
                .inject(this);

        FragmentNotificationBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        binding.setViewModel((NotificationViewModel) mViewModel);

        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(Gravity.END | Gravity.TOP);
            WindowManager.LayoutParams params = window.getAttributes();
            params.y = getResources().getDimensionPixelSize(R.dimen.dp_56);
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
