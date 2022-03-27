package com.jermyn.hima;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jermyn.hima.adapter.TabPagerAdapter;
import com.jermyn.hima.base.HiMaActivity;
import com.jermyn.hima.base.HiMaApplication;
import com.jermyn.hima.fragment.HistoryFragment;
import com.jermyn.hima.fragment.RecommendFragment;
import com.jermyn.hima.fragment.SubscriptionFragment;
import com.jermyn.hima.utils.LogUtils;
import com.microsoft.fluentui.appbarlayout.AppBarLayout;
import com.microsoft.fluentui.search.Searchbar;
import com.microsoft.fluentui.tablayout.TabLayout;
import com.microsoft.fluentui.util.ThemeUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends HiMaActivity {

    private static final String[] permissionsGroup = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.FOREGROUND_SERVICE};

    private static final String TAG = "MAIN_ACTIVITY";

    @BindView(R.id.root_view)
    View rootView;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;


    private TabPagerAdapter _adapter;
    private com.google.android.material.tabs.TabLayout _tbInside;
    private Searchbar _searchBar;
    Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBar.setScrollBehavior(AppBarLayout.ScrollBehavior.PIN);
        //getCategoryList();
        initTabLayout();
        getPermission();
        XmPlayerManager.getInstance(HiMaApplication.getContext()).init();

    }

    private void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions.request(permissionsGroup)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            //Toast.makeText(RxPermissionsActivity.this, "已获取权限，可以干想干的咯", Toast.LENGTH_LONG).show();
                        } else {
                            //只有用户拒绝开启权限，且选了不再提示时，才会走这里，否则会一直请求开启
                            //Toast.makeText(RxPermissionsActivity.this, "主人，我被禁止啦，去设置权限设置那把我打开哟", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        createSearchbar();
        _searchBar.setActionMenuView(true);
        getMenuInflater().inflate(R.menu.menu_app_bar_layout, menu);
        optionsMenu = menu;
        for (int index = 0; index < menu.size(); index++) {
            MenuItem menuItem = menu.getItem(index);
            if (menuItem.getItemId() == R.id.action_search) {
                menuItem.setActionView(_searchBar);
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            Drawable drawable = menuItem.getIcon();
            if (drawable != null) {
                //设置颜色蒙版
                drawable.setColorFilter(
                        ThemeUtil.INSTANCE.getThemeAttrColor(this, com.microsoft.fluentui.topappbars.R.attr.fluentuiToolbarIconColor)
                        , PorterDuff.Mode.SRC_IN);
            }
        }
        return true;
    }

    private Searchbar createSearchbar() {
        //按主题构造
        _searchBar = new Searchbar(new ContextThemeWrapper(this, R.style.AppTheme_Morandi));
        //设置焦点回调
        _searchBar.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (optionsMenu != null) {
                    optionsMenu.performIdentifierAction(R.id.app_bar_layout_action_search, 0);
                }
                _searchBar.requestSearchViewFocus();
            }
        });
        return _searchBar;
    }

    private void initTabLayout() {
        List<String> titleList = new ArrayList<>();
        titleList.add(getString(R.string.str_recommend));
        titleList.add(getString(R.string.str_subscription));
        titleList.add(getString(R.string.str_history));


        List<Fragment> viewList = new ArrayList<>();
        viewList.add(new RecommendFragment(rootView));
        viewList.add(new SubscriptionFragment());
        viewList.add(new HistoryFragment());

        _tbInside = tabLayout.getTabLayout();

        _adapter = new TabPagerAdapter(getSupportFragmentManager());
        _adapter.setData(viewList);
        _adapter.setTitle(titleList);
        viewPager.setAdapter(_adapter);
        _tbInside.setupWithViewPager(viewPager);
    }

    private View createPageView(int color) {
        View view = new View(this);
        view.setBackgroundColor(ContextCompat.getColor(this, color));
        return view;
    }
/*
    private void getCategoryList() {
        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList categoryList) {
                List<Category> categories = categoryList.getCategories();
                if (categories != null) {
                    int size = categories.size();
                    LogUtils.d(TAG,"Categories Size: " + size);
                    for (Category category : categories) {
                        LogUtils.d(TAG, "category: " + category.getCategoryName());
                        LogUtils.d(TAG, "category id: " + category.getId());
                    }
                }
            }

            @Override
            public void onError(int code, String message) {
                LogUtils.e(TAG, "onError: " + code + " ErrorMessage: " + message);
            }
        });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XmPlayerManager.release();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int width = appBar.getWidth();
        tabLayout.setMinimumWidth(width);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: {
                if (item.getActionView() instanceof Searchbar) {
                    ((Searchbar) item.getActionView()).requestSearchViewFocus();
                }
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}