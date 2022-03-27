package com.jermyn.hima.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendViewCallBack {

    /**
     * 获取推荐内容的结果
     * @param result
     */
    void onRecommendListLoaded(List<Album> result);

    /**
     * 加载更多
     * @param result
     */
    //void onLoadedMore(List<Album> result);

    /**
     * 网络错误
     */
    void onNetWorkError();

    /**
     * 获取为空
     */
    void onEmpty();

    /**
     * 加载中
     */
    void onLoading();
}
