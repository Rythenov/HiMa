package com.jermyn.hima.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jermyn.hima.DetailActivity;
import com.jermyn.hima.R;
import com.jermyn.hima.adapter.RecommendAdapter;
import com.jermyn.hima.interfaces.IRecommendViewCallBack;
import com.jermyn.hima.list.FluentListAdapter;
import com.jermyn.hima.presenter.RecommendPresenter;
import com.jermyn.hima.utils.Constants;
import com.jermyn.hima.view.UILoader;
import com.microsoft.fluentui.listitem.ListItemDivider;
import com.microsoft.fluentui.snackbar.Snackbar;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecommendFragment extends Fragment
        implements IRecommendViewCallBack
        , UILoader.RetryListener {

    @BindView(R.id.recycler_view)
    RecyclerView _recyclerView;

    private static final String TAG = "RECOMMEND_FRAGMENT";

    private View _rootView;
    private View _parentRootView;
    private RecommendAdapter _adapter;
    private RecommendPresenter _recommendPresenter;
    private UILoader _uiLoader;


    public RecommendFragment(View parentRootView) {
        _parentRootView = parentRootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (_uiLoader == null) {
            _uiLoader = new UILoader(getContext()) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(inflater, container);
                }
            };
            _uiLoader.setRetryListener(this);
        }

        _adapter = new RecommendAdapter(_parentRootView, getContext());



        _recommendPresenter = RecommendPresenter.getInstance();
        _recommendPresenter.registerViewCallBack(this);
        _recommendPresenter.getRecommendList();

        if (_uiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) _uiLoader.getParent()).removeView(_uiLoader);
        }

        return _uiLoader;
    }

    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        if (_rootView == null) {
            _rootView = inflater.inflate(R.layout.fragment_recommend, container, false);
        }
        ButterKnife.bind(this, _rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _recyclerView.setLayoutManager(linearLayoutManager);

        _recyclerView.setAdapter(_adapter);

        _recyclerView.addItemDecoration(new ListItemDivider(getContext(), DividerItemDecoration.VERTICAL));

        _adapter.setOnItemClickListener((adapter, view, position) -> {
            Album album = ((Album) adapter.getData().get(position));
            DetailActivity.open(getContext(), album);
        });

        return _rootView;
    }


    @Override
    public void onRecommendListLoaded(List<Album> result) {
        _uiLoader.updateUI(UILoader.UIStatus.SUCCESS);
        _adapter.setList(result);
        //_adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshed(List<Album> result) {
        _uiLoader.updateUI(UILoader.UIStatus.SUCCESS);
        _adapter.setList(result);
    }

    @Override
    public void onNetWorkError() {
        _uiLoader.updateUI(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        _uiLoader.updateUI(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        _uiLoader.updateUI(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (_recommendPresenter != null) {
            _recommendPresenter.unRegisterViewCallBack(this);
        }
    }

    @Override
    public void onRetry() {
        //重试
        if (_recommendPresenter != null) {
            _recommendPresenter.getRecommendList();
        }
    }
}