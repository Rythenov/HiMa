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
    void onLoadedMore(List<Album> result);

    /**
     * 刷新
     * @param result
     */
    void onRefreshed(List<Album> result);
}