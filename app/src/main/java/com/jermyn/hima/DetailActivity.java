package com.jermyn.hima;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jermyn.hima.adapter.AlbumDetailAdapter;
import com.jermyn.hima.adapter.RecommendAdapter;
import com.jermyn.hima.base.HiMaActivity;
import com.jermyn.hima.interfaces.IAlbumDetailViewCallBack;
import com.jermyn.hima.presenter.AlbumDetailPresenter;
import com.jermyn.hima.utils.Constants;
import com.jermyn.hima.utils.LogUtils;
import com.jermyn.hima.view.UILoader;
import com.microsoft.fluentui.appbarlayout.AppBarLayout;
import com.microsoft.fluentui.listitem.ListItemDivider;
import com.microsoft.fluentui.persona.AvatarView;
import com.microsoft.fluentui.widget.Button;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;

public class DetailActivity extends HiMaActivity implements IAlbumDetailViewCallBack {

    private static final String TAG = "DETAIL_ACTIVITY";

    @BindView(R.id.root_view)
    View rootView;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.av)
    AvatarView avatarView;

    @BindView(R.id.tv_intro)
    TextView tvIntro;

    @BindView(R.id.tv_author)
    TextView tvAuthor;

    @BindView(R.id.btn_subscribe)
    Button btnSubscribe;

    @BindView(R.id.main_container)
    SwipeRefreshLayout mainContainer;

    private Album _album;
    private AlbumDetailPresenter _albumDetailPresenter;
    private UILoader _uiLoader;
    private View _recyclerViewLayout;
    private AlbumDetailAdapter _adapter;

    public static void open(Context context, Album album){
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.PARCELABLE_TAG_ALBUM, album);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _album = getIntent().getParcelableExtra(Constants.PARCELABLE_TAG_ALBUM);
        LogUtils.d(TAG, "Intent get album title : " + _album.getAlbumTitle());
        _adapter = new AlbumDetailAdapter(rootView, this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }

    private void initPresenter() {
        _albumDetailPresenter = AlbumDetailPresenter.getInstance();
        _albumDetailPresenter.registerViewCallBack(this);
        _albumDetailPresenter.setTargetAlbum(_album);
        _albumDetailPresenter.load4Beginning();
    }
    private void initView() {
        setTitle(_album.getAlbumTitle());
        tvIntro.setText(_album.getAlbumIntro());
        tvAuthor.setText("作者: " + _album.getAnnouncer().getNickname());
        Glide.with(this).load(_album.getCoverUrlLarge()).into(new CustomTarget<Drawable>(100, 100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                avatarView.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                avatarView.setImageDrawable(getDrawable(R.drawable.avatar_allan_munger));
            }
        });

        _uiLoader = new UILoader(this) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(container);
            }
        };
        mainContainer.addView(_uiLoader);
    }

    private View createSuccessView(ViewGroup container) {
        if (_recyclerViewLayout == null) {
            _recyclerViewLayout = LayoutInflater.from(this).inflate(R.layout.view_recycler, container, false);
        }

        RecyclerView recyclerView = _recyclerViewLayout.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(_adapter);

        recyclerView.addItemDecoration(new ListItemDivider(this, DividerItemDecoration.VERTICAL));

        _adapter.setOnItemClickListener((adapter, view, position) -> {
        });

        return _recyclerViewLayout;
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
    public void onDetailListLoadedBeginning(List<Track> tracks) {
        _uiLoader.updateUI(UILoader.UIStatus.SUCCESS);
        _adapter.setList(tracks);
    }

    @Override
    public void onDetailListLoadedMore(List<Track> tracks) {
        _uiLoader.updateUI(UILoader.UIStatus.SUCCESS);
        _adapter.addData(tracks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (_albumDetailPresenter != null) {
            _albumDetailPresenter.unRegisterViewCallBack(this);
        }
    }
}