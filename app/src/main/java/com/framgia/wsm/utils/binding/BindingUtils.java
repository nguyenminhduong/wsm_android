package com.framgia.wsm.utils.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.HolidayCalendarDate;
import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.databinding.NavHeaderMainBinding;
import com.framgia.wsm.screen.changepassword.ChangePasswordViewModel;
import com.framgia.wsm.screen.forgotpassword.ForgotPasswordViewModel;
import com.framgia.wsm.screen.login.LoginViewModel;
import com.framgia.wsm.screen.main.MainViewModel;
import com.framgia.wsm.screen.requestoff.RequestOffViewModel;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.widget.holidaycalendar.HolidayCalendarView;
import com.framgia.wsm.widget.holidaycalendar.HolidayDateClickListener;
import com.framgia.wsm.widget.timesheet.OnDayClickListener;
import com.framgia.wsm.widget.timesheet.TimeSheetView;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.util.List;

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

    @BindingAdapter({ "itemSelected", "currentItem", "viewModel", "staffType", "isManage" })
    public static void setNavigationItemSelected(NavigationView navigationView,
            NavigationView.OnNavigationItemSelectedListener listen, int currentItem,
            MainViewModel viewModel, String staffType, boolean isManage) {

        //TODO edit version later
        navigationView.getMenu().findItem(R.id.item_statistic_of_personal).setVisible(false);
        navigationView.getMenu().findItem(R.id.item_workspace_data).setVisible(false);

        MenuItem menuDayOff = navigationView.getMenu().findItem(R.id.item_off);
        if (Constant.STAFF_OFFICIAL.equals(staffType)) {
            menuDayOff.setVisible(true);
        } else {
            menuDayOff.setVisible(false);
        }

        MenuItem menuOffManage = navigationView.getMenu().findItem(R.id.item_manage_off);
        MenuItem menuLeaveManage =
                navigationView.getMenu().findItem(R.id.item_manage_come_late_leave_early);
        MenuItem menuOverTimeManage = navigationView.getMenu().findItem(R.id.item_manage_overtime);
        if (isManage) {
            menuOffManage.setVisible(true);
            menuLeaveManage.setVisible(true);
            menuOverTimeManage.setVisible(true);
        } else {
            menuOffManage.setVisible(false);
            menuLeaveManage.setVisible(false);
            menuOverTimeManage.setVisible(false);
        }

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

    @BindingAdapter("backgroundImage")
    public static void setBackgroundImage(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    @BindingAdapter({ "adapter" })
    public static void setViewPagerAdapter(final ViewPager viewPager,
            final FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("pager")
    public static void setViewPagerTabs(final TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager, true);
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
                .dontAnimate()
                .placeholder(R.drawable.ic_user)
                .into(imageView);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        Uri uri = Uri.parse(url);
        Glide.with(imageView.getContext()).load(uri).dontAnimate().into(imageView);
    }

    @BindingAdapter("cardBackground")
    public static void setBackground(CardView cardView, String color) {
        if (TextUtils.isEmpty(color)) {
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            return;
        }
        cardView.setCardBackgroundColor(Color.parseColor(color));
    }

    @BindingAdapter({ "timeSheetDates", "month", "year" })
    public static void setTimeSheetDates(TimeSheetView timeSheetView,
            List<TimeSheetDate> timeSheetDates, int month, int year) {
        timeSheetView.setTime(month, year);
        timeSheetView.reuse();
        timeSheetView.invalidate();
        timeSheetView.setTimeSheetDates(timeSheetDates);
    }

    @BindingAdapter({ "holidayCalendarDates", "month", "year" })
    public static void setHolidayCalendarDates(HolidayCalendarView holidayCalendarView,
            List<HolidayCalendarDate> holidayCalendarDates, int month, int year) {
        holidayCalendarView.setTime(month, year);
        holidayCalendarView.reuse();
        holidayCalendarView.invalidate();
        holidayCalendarView.setHolidayCalendarDates(holidayCalendarDates);
    }

    @BindingAdapter({ "onDayClick" })
    public static void setOnDayClick(TimeSheetView timeSheetView,
            OnDayClickListener onDayClickListener) {
        timeSheetView.setOnDayClickListener(onDayClickListener);
    }

    @BindingAdapter({ "onHolidayDayClick" })
    public static void setHolidayOnDayClick(HolidayCalendarView holidayCalendarView,
            HolidayDateClickListener onDayClickListener) {
        holidayCalendarView.setOnDayClickListener(onDayClickListener);
    }

    @BindingAdapter({ "toolbar" })
    public static void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
    }

    @BindingAdapter({ "visibleFloatingActionMenu" })
    public static void setVisibleFloatingActionMenu(FloatingActionMenu floatingActionMenu,
            boolean visible) {
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.toggle(visible);
        }
    }

    @BindingAdapter({ "closedOnTouchOutside" })
    public static void setClosedOnTouchOutside(FloatingActionMenu floatingActionMenu,
            boolean close) {
        floatingActionMenu.setClosedOnTouchOutside(close);
    }

    @BindingAdapter("errorTextInputLayout")
    public static void setErrorTextInputLayout(final TextInputLayout textInputLayout,
            final String text) {
        textInputLayout.setError(text);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No-Op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) {
                    textInputLayout.setError(text);
                }
                if (s.length() > 0) {
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //No-Op
            }
        });
    }

    @BindingAdapter({ "errorTextInputLayoutEmail", "viewModel" })
    public static void setErrorTextInputLayoutEmail(final TextInputLayout textInputLayout,
            final String text, final LoginViewModel loginViewModel) {
        textInputLayout.setError(text);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No-Op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No-Op
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.validateEmail(s.toString());
            }
        });
    }

    @BindingAdapter({ "errorTextInputLayoutNewPassword", "viewModel" })
    public static void setErrorTextNewPassword(final TextInputLayout textInputLayout,
            final String text, final ChangePasswordViewModel changePasswordViewModel) {
        textInputLayout.setError(text);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No-Op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No-Op
            }

            @Override
            public void afterTextChanged(Editable s) {
                changePasswordViewModel.validateNewPassword(s.toString());
            }
        });
    }

    @BindingAdapter({ "errorTextInputLayoutConfirmPassword", "viewModel" })
    public static void setErrorTextConfirmPassword(final TextInputLayout textInputLayout,
            final String text, final ChangePasswordViewModel changePasswordViewModel) {
        textInputLayout.setError(text);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No-Op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No-Op
            }

            @Override
            public void afterTextChanged(Editable s) {
                changePasswordViewModel.validateConfirmPassword(s.toString());
            }
        });
    }

    @BindingAdapter({ "errorTextInputLayoutSendEmail", "viewModel" })
    public static void setErrorTextInputLayoutSendEmail(final TextInputLayout textInputLayout,
            final String text, final ForgotPasswordViewModel forgotPasswordViewModel) {
        textInputLayout.setError(text);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No-Op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No-Op
            }

            @Override
            public void afterTextChanged(Editable s) {
                forgotPasswordViewModel.validateEmail(s.toString());
            }
        });
    }

    @BindingAdapter({ "errorText", "viewModel" })
    public static void setErrorText(final TextInputLayout textInputLayout, final String text,
            final RequestOffViewModel viewModel) {
        textInputLayout.setError(text);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No-Op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.validateNumberDayHaveSalary();
            }
        });
    }

    @BindingAdapter("colorRes")
    public static void setColorRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout,
            int colorResIds) {
        swipeRefreshLayout.setColorSchemeColors(colorResIds);
    }

    @BindingAdapter("enableSwipe")
    public static void setEnableSwipeLayout(final SwipeRefreshLayout swipeRefreshLayout,
            boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @BindingAdapter("onRefresh")
    public static void setRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout,
            SwipeRefreshLayout.OnRefreshListener refreshListener) {
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
    }

    @BindingAdapter({ "setCombinedChar", "setMonths" })
    public static void combinedChar(final CombinedChart combinedChart, LineData lineDatas,
            final List<String> months) {
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);
        combinedChart.animateXY(3000, 3000);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0);
        rightAxis.setTextSize(combinedChart.getContext().getResources().getDimension(R.dimen.sp_4));
        rightAxis.setGranularity(1);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextSize(combinedChart.getContext().getResources().getDimension(R.dimen.sp_4));
        leftAxis.setGranularity(1);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(combinedChart.getContext().getResources().getDimension(R.dimen.sp_4));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months.get((int) value % months.size());
            }
        });

        CombinedData data = new CombinedData();
        data.setData(lineDatas);
        xAxis.setAxisMaximum(data.getXMax());
        combinedChart.setData(data);
        combinedChart.invalidate();
    }
}
