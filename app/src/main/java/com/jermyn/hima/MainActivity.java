package com.jermyn.hima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.jermyn.hima.adapter.TabPagerAdapter;
import com.jermyn.hima.fragment.HistoryFragment;
import com.jermyn.hima.fragment.RecommendFragment;
import com.jermyn.hima.fragment.SubscriptionFragment;
import com.jermyn.hima.utils.LogUtils;
import com.microsoft.fluentui.appbarlayout.AppBarLayout;
import com.microsoft.fluentui.search.Searchbar;
import com.microsoft.fluentui.tablayout.TabLayout;
import com.microsoft.fluentui.util.ThemeUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appBar.setScrollBehavior(AppBarLayout.ScrollBehavior.PIN);
        getCategoryList();
        initTabLayout();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        createSearchbar();
        _searchBar.setActionMenuView(true);
        getMenuInflater().inflate(R.menu.menu_app_bar_layout, menu);
        optionsMenu = menu;
        for (int index = 0; index < menu.size(); index++) {
            MenuItem menuItem = menu.getItem(index);
            if (menuItem.getItemId() == R.id.action_search){
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

    private View createPageView(int color){
        View view = new View(this);
        view.setBackgroundColor(ContextCompat.getColor(this, color));
        return view;
    }

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
            case android.R.id.home: {
                //navigateUpTo(new Intent(this, MainActivity.class));
                finish();
            }
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