package com.jermyn.hima.interfaces;

import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.List;

/**
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/27
 */
public interface IPlayerViewCallBack {

    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 暂停播放
     */
    void onPlayPause();

    /**
     * 停止播放
     */
    void onPlayStop();

    /**
     * 播放错误
     */
    void onPlayError();

    void onNextPlay(Track track);

    void onPrePlay(Track track);

    void onListLoaded(List<Track> trackList);

    void onPlayModeChanged(XmPlayListControl.PlayMode mode);

    void onProgressChanged(int current, int total);

    void onBufferingPercentChanged(int percent);

    void onAdsLoading();

    void onAdsFinished();


//    /**
//     * 播放完成
//     */
//    void onSoundPlayComplete();
//
//    /**
//     * 播放器准备完毕
//     */
//    void onSoundPrepared();
//
//    /**
//     * 切歌
//     *
//     * @param lastModel 上一首model,可能为空
//     * @param curModel 下一首model
//     *
//     * 请通过model中的kind字段来判断是track、radio和schedule；
//     * 上一首的播放时间请通过lastPlayedMills字段来获取;
//     */
//    void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel);
//
//    /**
//     * 开始缓冲
//     */
//    void onBufferingStart();
//
//    /**
//     * 缓冲完毕
//     */
//    void onBufferingStop();
//
//    /**
//     * 缓冲进度回调
//     * @param percent
//     */
//    void onBufferProgress(int percent);
//
//    /**
//     * 播放进度回调
//     * @param currPos
//     * @param duration
//     */
//    void onPlayProgress(int currPos, int duration);
//
//    /**
//     * 播放器错误
//     * @param exception
//     */
//    void onError(XmPlayerException exception);
//
//    /**
//     * 开始获取广告物料
//     */
//    void onStartGetAdsInfo();
//
//    /**
//     * 获取广告物料成功
//     * @param ads
//     */
//    void onGetAdsInfo(AdvertisList ads);
//
//    /**
//     * 广告开始缓冲
//     */
//    void onAdsStartBuffering();
//
//    /**
//     * 广告结束缓冲
//     */
//    void onAdsStopBuffering();
//
//    /**
//     * 开始播放广告
//     * @param ad 当前播放广告
//     * @param position 当前播放的广告在广告列表中的索引
//     */
//    void onStartPlayAds(Advertis ad, int position);
//
//    /**
//     * 广告播放完毕
//     */
//    void onCompletePlayAds();
//
//    /**
//     * 播放广告错误
//     * @param what 错误类型
//     * @param extra 错误的额外信息
//     */
//    void onAdsError(int what, int extra);
}
