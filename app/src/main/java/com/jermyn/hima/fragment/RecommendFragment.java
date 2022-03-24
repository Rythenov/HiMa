package com.jermyn.hima.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jermyn.hima.R;
import com.jermyn.hima.adapter.RecommendAdapter;
import com.jermyn.hima.list.FluentListAdapter;
import com.jermyn.hima.presenter.RecommendPresenter;
import com.jermyn.hima.utils.Constants;
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


public class RecommendFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView _recyclerView;

    private static final String TAG = "RECOMMEND_FRAGMENT";

    private View _rootView;
    private RecommendAdapter _adapter;
    private RecommendPresenter _recommendPresenter;


    public RecommendFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (_rootView == null) {
            _rootView = inflater.inflate(R.layout.fragment_recommend, container, false);
        }
        ButterKnife.bind(this, _rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _recyclerView.setLayoutManager(linearLayoutManager);

        _adapter = new RecommendAdapter();

        _recyclerView.setAdapter(_adapter);

        _recommendPresenter = RecommendPresenter.getInstance();
        _recommendPresenter.getRecommendList();

        return _rootView;
    }

    /**
     * 获取推荐内容
     * 3.10.6
     */
    private void getRecommendData() {







        Map<String, String> map = new HashMap<>();
        //获取条数
        map.put(DTransferConstants.LIKE_COUNT, Constants.GUESS_LIKE_COUNT);
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {

            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if (albumList != null) {
                        Log.d(TAG, "GET GUESS LIKE ALBUM SUCC:" + albumList.size());
                        updateUI(albumList);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "ERROR:" + i);
                Log.e(TAG, "ERROR MSG:" + s);
            }
        });
    }

    private void updateUI(List<Album> albumList) {

    }


}