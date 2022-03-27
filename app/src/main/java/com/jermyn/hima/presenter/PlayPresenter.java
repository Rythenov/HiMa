package com.jermyn.hima.presenter;

import com.jermyn.hima.base.HiMaApplication;
import com.jermyn.hima.interfaces.IPlayerPresenter;
import com.jermyn.hima.interfaces.IPlayerViewCallBack;
import com.jermyn.hima.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.CommonTrackList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放器逻辑层
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/27
 */
public class PlayPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

    private static final String TAG = "PLAY_PRESENTER";

    private static PlayPresenter PLAY_PRESENTER = null;

    private PlayPresenter(){
        _xmPlayerManager = XmPlayerManager.getInstance(HiMaApplication.getContext());
        //广告相关
        _xmPlayerManager.addAdsStatusListener(this);
        //播放器状态
        _xmPlayerManager.addPlayerStatusListener(this);
    }

    public static PlayPresenter getInstance(){
        if (PLAY_PRESENTER == null) {
            synchronized (PlayPresenter.class){
                if (PLAY_PRESENTER == null) {
                    PLAY_PRESENTER = new PlayPresenter();
                }
            }
        }
        return PLAY_PRESENTER;
    }

    private XmPlayerManager _xmPlayerManager;
    private List<IPlayerViewCallBack> _callBackList = new ArrayList<>();
    private boolean _isPlayListSet = false;

    @Override
    public void registerViewCallBack(IPlayerViewCallBack callBack) {
        if (_callBackList != null && !_callBackList.contains(callBack)) {
            _callBackList.add(callBack);
        }
    }

    @Override
    public void unRegisterViewCallBack(IPlayerViewCallBack callBack) {
        if (_callBackList != null) {
            _callBackList.remove(callBack);
        }
    }

    @Override
    public boolean isPlaying() {
        return _xmPlayerManager.isPlaying();
    }

    @Override
    public int getPlayStatus() {
        return _xmPlayerManager.getPlayerStatus();
    }

    @Override
    public void setPlayList(List<Track> trackList, int index) {
        if (trackList == null) {
            return;
        }
        CommonTrackList commonTrackList = CommonTrackList.newInstance();
        commonTrackList.setTracks(trackList);
        _xmPlayerManager.setPlayList(commonTrackList, index);
        _isPlayListSet = true;
    }

    @Override
    public void play() {
        if (_isPlayListSet){
            _xmPlayerManager.play();
        }
    }

    @Override
    public void pause() {
        if (_isPlayListSet) {
            _xmPlayerManager.pause();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void next() {

    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode playMode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int duration) {

    }


    //--------------XMManager, IXmAdsStatusListener广告模块回调 START-----------------------
    @Override
    public void onStartGetAdsInfo() {
        LogUtils.d(TAG, "onStartGetAdsInfo");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        LogUtils.d(TAG, "onGetAdsInfo");

    }

    @Override
    public void onAdsStartBuffering() {
        LogUtils.d(TAG, "onAdsStartBuffering");

    }

    @Override
    public void onAdsStopBuffering() {
        LogUtils.d(TAG, "onAdsStopBuffering");

    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtils.d(TAG, "onStartPlayAds");

    }

    @Override
    public void onCompletePlayAds() {
        LogUtils.d(TAG, "onCompletePlayAds");

    }

    @Override
    public void onError(int what, int extra) {
        LogUtils.d(TAG, "onError");

    }
    //--------------XMManager, IXmAdsStatusListener广告模块回调 END----------------------


    //--------------XMManager, IXmPlayerStatusListener播放器状态模块回调 START-----------------------
    @Override
    public void onPlayStart() {
        LogUtils.d(TAG, "onPlayStart");
        for (IPlayerViewCallBack callBack : _callBackList) {
            callBack.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        LogUtils.d(TAG, "onPlayPause");
        for (IPlayerViewCallBack callBack : _callBackList) {
            callBack.onPlayPause();
        }
    }

    @Override
    public void onPlayStop() {
        LogUtils.d(TAG, "onPlayStop");
        for (IPlayerViewCallBack callBack : _callBackList) {
            callBack.onPlayStop();
        }
    }

    @Override
    public void onSoundPlayComplete() {
        LogUtils.d(TAG, "onSoundPlayComplete");

    }

    @Override
    public void onSoundPrepared() {
        LogUtils.d(TAG, "onSoundPrepared");

    }

    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        LogUtils.d(TAG, "onSoundSwitch");

    }

    @Override
    public void onBufferingStart() {
        LogUtils.d(TAG, "onBufferingStart");

    }

    @Override
    public void onBufferingStop() {
        LogUtils.d(TAG, "onBufferingStop");

    }

    @Override
    public void onBufferProgress(int percent) {
        LogUtils.d(TAG, "onBufferProgress");
        for (IPlayerViewCallBack callBack : _callBackList) {
            callBack.onBufferingPercentChanged(percent);
        }
    }

    @Override
    public void onPlayProgress(int currentPos, int duration) {
        LogUtils.d(TAG, "onPlayProgress");
        for (IPlayerViewCallBack callBack : _callBackList) {
            callBack.onProgressChanged(currentPos, duration);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        LogUtils.d(TAG, "");
        return false;
    }

    //--------------XMManager, IXmPlayerStatusListener播放器状态模块回调 END-----------------------
}
