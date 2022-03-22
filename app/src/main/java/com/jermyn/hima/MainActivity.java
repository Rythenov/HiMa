package com.jermyn.hima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.microsoft.fluentui.appbarlayout.AppBarLayout;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";

    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appBar.setScrollBehavior(AppBarLayout.ScrollBehavior.PIN);

        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList categoryList) {
                List<Category> categories = categoryList.getCategories();
                if (categories != null) {
                    int size = categories.size();
                    Log.d(TAG, "Categories Size: " + size);
                    for (Category category : categories) {
                        Log.d(TAG, "category: " + category.getCategoryName());
                    }
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "onError: " + code + " ErrorMessage: " + message);
            }
        });
    }
}