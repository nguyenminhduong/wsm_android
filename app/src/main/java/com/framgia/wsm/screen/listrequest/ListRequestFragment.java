package com.framgia.wsm.screen.listrequest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentListRequestBinding;
import com.framgia.wsm.screen.BaseFragment;
import com.framgia.wsm.screen.main.MainActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.RequestType;
import javax.inject.Inject;

/**
 * Created by ASUS on 12/06/2017.
 */

public class ListRequestFragment extends BaseFragment {
    public static final String TAG = "ListRequestFragment";

    @Inject
    ListRequestContract.ViewModel mViewModel;

    public static ListRequestFragment newInstance(@RequestType int requestType) {
        ListRequestFragment listRequestFragment = new ListRequestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_REQUEST_TYPE, requestType);
        listRequestFragment.setArguments(bundle);
        return listRequestFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        DaggerListRequestComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .listRequestModule(new ListRequestModule(this))
                .build()
                .inject(this);

        FragmentListRequestBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_list_request, container, false);
        binding.setViewModel((ListRequestViewModel) mViewModel);
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
