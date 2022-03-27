package com.jermyn.hima.interfaces;

import com.jermyn.hima.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/**
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/27
 */
public interface IPlayerPresenter extends IBasePresenter<IPlayerViewCallBack> {

    boolean isPlaying();

    int getPlayStatus();

    void setPlayList(List<Track> trackList, int index);

    /**
     * 播放
     */
    void play();

    /**
     * 暂定
     */
    void pause();

    /**
     * 停止
     */
    void stop();

    /**
     * 上一首
     */
    void previous();

    /**
     * 下一首
     */
    void next();

    /**
     * 切换播放模式
     * @param playMode
     */
    void switchPlayMode(XmPlayListControl.PlayMode playMode);

    /**
     * 获取播放列表
     */
    void getPlayList();

    /**
     * 根据序号播放
     *
     * @param index
     */
    void playByIndex(int index);

    /**
     * 切换播放进度
     * @param duration
     */
    void seekTo(int duration);
}
