package com.framgia.wsm.screen.listrequest;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
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
        initView(binding);
        binding.setViewModel((ListRequestViewModel) mViewModel);
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            boolean isOpenAppByClickNotification = mainActivity.isOpenAppByClickNotification();
            if (isOpenAppByClickNotification) {
                mViewModel.onReloadData(getArguments().getInt(Constant.EXTRA_REQUEST_TYPE));
            }
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            return;
        }
        int typeRequest = getArguments().getInt(Constant.EXTRA_REQUEST_TYPE);
        mViewModel.onReloadData(typeRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mViewModel.onReloadData(data.getExtras().getInt(Constant.EXTRA_REQUEST_TYPE_CODE));
        }
    }

    private void initView(FragmentListRequestBinding binding) {
        RecyclerView recyclerView = binding.recyclerview;
        final FloatingActionButton actionButton = binding.floatingButtonCreate;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    actionButton.hide();
                    return;
                }
                actionButton.show();
            }
        });
    }
}
