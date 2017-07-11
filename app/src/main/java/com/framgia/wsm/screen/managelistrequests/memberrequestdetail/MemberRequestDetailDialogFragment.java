package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentMemberRequestDetailBinding;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberRequestDetailDialogFragment extends DialogFragment
        implements DismissDialogListener {

    public static final String TAG = "MemberRequestDetailDialogFragment";

    @Inject
    MemberRequestDetailContract.ViewModel mViewModel;

    public static MemberRequestDetailDialogFragment newInstance() {
        return new MemberRequestDetailDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        DaggerMemberRequestDetailComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .memberRequestDetailModule(new MemberRequestDetailModule(this))
                .build()
                .inject(this);

        FragmentMemberRequestDetailBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_member_request_detail,
                        container, false);
        binding.setViewModel((MemberRequestDetailViewModel) mViewModel);

        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER_VERTICAL);
            window.requestFeature(Window.FEATURE_NO_TITLE);
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

    @Override
    public void onDismissDialog() {
        dismiss();
    }
}
