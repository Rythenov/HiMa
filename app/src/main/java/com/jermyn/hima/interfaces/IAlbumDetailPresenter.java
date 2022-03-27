package com.jermyn.hima.interfaces;

import com.jermyn.hima.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

/**
 * 专辑详情提供器
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailViewCallBack> {
    /**
     * 下拉刷新
     */
    void pull2Refresh();

    /**
     * 上拉加载
     */
    void loadMore();

    /**
     * 获取专辑详情
     */
    void load4Beginning();

}
