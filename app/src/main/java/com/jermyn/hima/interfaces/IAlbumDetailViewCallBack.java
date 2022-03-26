package com.jermyn.hima.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/**
 * 专辑详情界面回调
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public interface IAlbumDetailViewCallBack {

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
    /**
     * 专辑详情加载成功
     * @param tracks
     */
    void onDetailListLoadedBeginning(List<Track> tracks);

    void onDetailListLoadedMore(List<Track> tracks);


}
