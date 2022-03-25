package com.jermyn.hima.presenter;

import android.util.Log;

import androidx.annotation.Nullable;

import com.jermyn.hima.interfaces.IRecommendPresenter;
import com.jermyn.hima.interfaces.IRecommendViewCallBack;
import com.jermyn.hima.utils.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RECOMEND_PRESENTER";


    private RecommendPresenter() {
    }

    private volatile static RecommendPresenter _instance = null;

    private List<IRecommendViewCallBack> _callBackList = new ArrayList<>();

    /**
     * 获取单例
     *
     * @return
     */
    public static RecommendPresenter getInstance() {
        if (_instance == null) {
            synchronized (RecommendPresenter.class) {
                if (_instance == null) {
                    _instance = new RecommendPresenter();
                }
            }
        }
        return _instance;
    }

    /**
     * 获取推荐内容
     * 3.10.6
     */
    @Override
    public void getRecommendList() {
        updateLoading();
        Map<String, String> map = new HashMap<>();
        //获取条数
        map.put(DTransferConstants.LIKE_COUNT, Constants.GUESS_LIKE_COUNT);
        map.put(DTransferConstants.CATEGORY_ID, Constants.GUESS_LIKE_CATEGORY_ID);
        map.put("show_type", Constants.GUESS_LIKE_SHOW_TYPE);
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if (albumList != null) {
                        Log.d(TAG, "GET GUESS LIKE ALBUM SUCC:" + albumList.size());
                        handlerRecommendResult(albumList);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "ERROR:" + i);
                Log.e(TAG, "ERROR MSG:" + s);
                handlerError();
            }
        });
    }

    private void handlerError() {
        //通知UI
        if (_callBackList != null) {
            for (IRecommendViewCallBack callBack : _callBackList) {
                callBack.onNetWorkError();
            }
        }
    }

    private void handlerRecommendResult(List<Album> albumList) {
        if (_callBackList != null && albumList != null) {
            if (albumList.size() == 0) {
                for (IRecommendViewCallBack callBack : _callBackList) {
                    callBack.onEmpty();
                }
            } else {
                for (IRecommendViewCallBack callBack : _callBackList) {
                    callBack.onRecommendListLoaded(albumList);
                }
            }
        }
    }

    private void updateLoading(){
        for (IRecommendViewCallBack callBack : _callBackList) {
            callBack.onLoading();
        }
    }

    @Override
    public void pull2Refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallBack(IRecommendViewCallBack callBack) {
        if (_callBackList != null && !_callBackList.contains(callBack)) {
            _callBackList.add(callBack);
        }
    }

    @Override
    public void unRegisterViewCallBack(IRecommendViewCallBack callBack) {
        if (_callBackList != null) {
            _callBackList.remove(callBack);
        }
    }
}
