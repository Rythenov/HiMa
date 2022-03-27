package com.jermyn.hima;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jermyn.hima.base.HiMaActivity;
import com.jermyn.hima.interfaces.IPlayerViewCallBack;
import com.jermyn.hima.presenter.PlayPresenter;
import com.jermyn.hima.utils.Constants;
import com.jermyn.hima.utils.LogUtils;
import com.jermyn.hima.utils.TinyTools;
import com.microsoft.fluentui.util.ThemeUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayerActivity extends HiMaActivity implements IPlayerViewCallBack {

    private static final String TAG = "PLAYER_ACTIVITY";

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_cover)
    ImageView ivCover;

    @BindView(R.id.tv_current_duration)
    TextView tvCurrentDuration;

    @BindView(R.id.tv_total_duration)
    TextView tvTotalDuration;

    @BindView(R.id.seek_bar)
    SeekBar seekBar;

    @BindView(R.id.iv_previous)
    ImageView ivPrevious;

    @BindView(R.id.iv_play)
    ImageView ivPlay;

    @BindView(R.id.iv_next)
    ImageView ivNext;

    @BindView(R.id.iv_list)
    ImageView ivList;

    private List<Track> _trackList;
    private int _currentIndex;

    public static void open(Context context, List<Track> trackList, int currentIndex){
        Intent intent = new Intent(context, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PARCELABLE_TAG_CURRENT_INDEX, currentIndex);
        bundle.putParcelableArrayList(Constants.PARCELABLE_TAG_TRACK_LIST, new ArrayList<>(trackList));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _trackList = getIntent().getParcelableArrayListExtra(Constants.PARCELABLE_TAG_TRACK_LIST);
        _currentIndex = getIntent().getIntExtra(Constants.PARCELABLE_TAG_CURRENT_INDEX, 0);
        LogUtils.d(TAG, "Intent get track list size : " + _trackList.size());
        LogUtils.d(TAG, "Intent get current index : " + _currentIndex);
        getWindow().setNavigationBarColor(ThemeUtil.INSTANCE.getThemeAttrColor(this, androidx.appcompat.R.attr.colorPrimary));

        PlayPresenter.getInstance().registerViewCallBack(this);

        initView();
        PlayPresenter.getInstance().setPlayList(_trackList, _currentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PlayPresenter.getInstance().play();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        if (_trackList == null || _trackList.isEmpty() || _currentIndex < 0 || _currentIndex >= _trackList.size()){
            return;
        }
        Track currentTrack = _trackList.get(_currentIndex);
        tvTitle.setText(currentTrack.getTrackTitle());
        Glide.with(this).load(currentTrack.getCoverUrlLarge()).into(ivCover);
        tvCurrentDuration.setText(TinyTools.formatDuration(0));
        tvTotalDuration.setText(TinyTools.formatDuration(currentTrack.getDuration()));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_player;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayPresenter.getInstance().unRegisterViewCallBack(this);
    }

    @OnClick(R.id.iv_play)
    void playOrPause(){
        boolean isPlaying = PlayPresenter.getInstance().isPlaying();
        if (isPlaying){
            PlayPresenter.getInstance().pause();
        } else {
            PlayPresenter.getInstance().play();
        }
    }

    //--------------------------IPlayerViewCallBack 回调 START-------------------------------------------------------//
    @Override
    public void onPlayStart() {
        ivPlay.setImageDrawable(getDrawable(com.microsoft.fluent.mobile.icons.R.drawable.ic_fluent_pause_24_regular));
    }

    @Override
    public void onPlayPause() {
        ivPlay.setImageDrawable(getDrawable(com.microsoft.fluent.mobile.icons.R.drawable.ic_fluent_play_circle_28_regular));
    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onPlayError() {

    }

    @Override
    public void onNextPlay(Track track) {

    }

    @Override
    public void onPrePlay(Track track) {

    }

    @Override
    public void onListLoaded(List<Track> trackList) {

    }

    @Override
    public void onPlayModeChanged(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void onProgressChanged(int current, int total) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(0);
        }
        seekBar.setMax(total);
        seekBar.setProgress(current);
    }

    @Override
    public void onBufferingPercentChanged(int percent) {
        seekBar.setSecondaryProgress(seekBar.getMax() * percent / 100);
    }

    @Override
    public void onAdsLoading() {

    }

    @Override
    public void onAdsFinished() {

    }
    //--------------------------IPlayerViewCallBack 回调 END-------------------------------------------------------//
}