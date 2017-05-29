package com.framgia.wsm.utils.binding;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.NavHeaderMainBinding;
import com.framgia.wsm.screen.main.MainViewModel;
import com.framgia.wsm.utils.Constant;

/**
 * Created by le.quang.dao on 20/03/2017.
 */

public final class BindingUtils {

    private BindingUtils() {
        // No-op
    }

    /**
     * setAdapter For RecyclerView
     */
    @BindingAdapter({ "recyclerAdapter" })
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({ "itemSelected", "currentItem", "viewModel" })
    public static void setNavigationItemSelected(NavigationView navigationView,
            NavigationView.OnNavigationItemSelectedListener listen, int currentItem,
            MainViewModel viewModel) {
        navigationView.setNavigationItemSelectedListener(listen);
        navigationView.setCheckedItem(currentItem);
        if (navigationView.getHeaderCount() == 0) {
            NavHeaderMainBinding binding =
                    NavHeaderMainBinding.inflate(LayoutInflater.from(navigationView.getContext()));
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
            navigationView.addHeaderView(binding.getRoot());
        }
    }

    @BindingAdapter({ "statusDrawerLayout" })
    public static void setStatusDrawerLayout(DrawerLayout drawerLayout, final String status) {
        if (status != null) {
            if (status.equals(Constant.DRAWER_IS_CLOSE)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (status.equals(Constant.DRAWER_IS_OPEN)) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }

    @BindingAdapter({ "currentFragment" })
    public static void setCurrentViewPager(final ViewPager viewPager, final int currentPage) {
        viewPager.setCurrentItem(currentPage);
    }

    @BindingAdapter({ "adapter" })
    public static void setViewPagerAdapter(final ViewPager viewPager,
            final FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("errorText")
    public static void setErrorText(EditText editText, String text) {
        editText.setError(text);
    }

    @BindingAdapter("imageUrlUser")
    public static void loadImageUser(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        Uri uri = Uri.parse(url);
        Glide.with(imageView.getContext())
                .load(uri)
                .placeholder(R.drawable.ic_placeholder_user)
                .into(imageView);
    }
}
