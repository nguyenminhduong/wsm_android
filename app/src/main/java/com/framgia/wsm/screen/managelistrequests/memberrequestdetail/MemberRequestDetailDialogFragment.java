package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.framgia.wsm.utils.Constant;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberRequestDetailDialogFragment extends DialogFragment
        implements DismissDialogListener {

    public static final String TAG = "MemberRequestDetailDialogFragment";
    private MemberRequestDetailViewModel.ApproveOrRejectListener mApproveOrRejectListener;

    @Inject
    MemberRequestDetailContract.ViewModel mViewModel;

    public static MemberRequestDetailDialogFragment newInstance(Object item, int position) {
        MemberRequestDetailDialogFragment memberRequestDetailDialogFragment =
                new MemberRequestDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_MEMBER_REQUEST, position);
        bundle.putParcelable(TAG, (Parcelable) item);
        memberRequestDetailDialogFragment.setArguments(bundle);
        return memberRequestDetailDialogFragment;
    }

    public void setApproveOrRejectListener(
            MemberRequestDetailViewModel.ApproveOrRejectListener approveOrRejectListener) {
        mApproveOrRejectListener = approveOrRejectListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int position = getArguments().getInt(Constant.EXTRA_MEMBER_REQUEST);
        Object item = getArguments().getParcelable(TAG);
        DaggerMemberRequestDetailComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .memberRequestDetailModule(new MemberRequestDetailModule(this, item, position,
                        mApproveOrRejectListener))
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
