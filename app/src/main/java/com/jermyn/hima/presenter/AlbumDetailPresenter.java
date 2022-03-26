package com.jermyn.hima.presenter;

import androidx.annotation.Nullable;

import com.jermyn.hima.interfaces.IAlbumDetailPresenter;
import com.jermyn.hima.interfaces.IAlbumDetailViewCallBack;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专辑详情提供器
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public class AlbumDetailPresenter implements IAlbumDetailPresenter {

    private AlbumDetailPresenter() {
    }

    private static AlbumDetailPresenter ALBUM_DETAIL_PRESENTER = null;

    public static AlbumDetailPresenter getInstance() {
        if (ALBUM_DETAIL_PRESENTER == null) {
            synchronized (AlbumDetailPresenter.class) {
                if (ALBUM_DETAIL_PRESENTER == null) {
                    ALBUM_DETAIL_PRESENTER = new AlbumDetailPresenter();
                }
            }
        }
        return ALBUM_DETAIL_PRESENTER;
    }

    private Album _album;
    private List<IAlbumDetailViewCallBack> _callBackList = new ArrayList<>();
    private int _currentPage = 1;

    public void setTargetAlbum(Album album) {
        _album = album;
        _currentPage = 1;
    }

    @Override
    public void pull2Refresh() {
        load4Beginning();
    }

    @Override
    public void loadMore() {
        getAlbumDetail(_album.getId(), _currentPage, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                _currentPage++;
                if (_callBackList != null) {
                    if (trackList.getTracks().isEmpty()) {
                        for (IAlbumDetailViewCallBack callBack : _callBackList) {
                            callBack.onEmpty();
                        }
                    } else {
                        for (IAlbumDetailViewCallBack callBack : _callBackList) {
                            callBack.onDetailListLoadedMore(trackList.getTracks());
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                if (_callBackList != null) {
                    for (IAlbumDetailViewCallBack callBack : _callBackList) {
                        callBack.onNetWorkError();
                    }
                }
            }
        });
    }

    @Override
    public void load4Beginning() {
        _currentPage = 1;
        getAlbumDetail(_album.getId(), _currentPage, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                _currentPage++;
                if (_callBackList != null) {
                    if (trackList.getTracks().isEmpty()) {
                        for (IAlbumDetailViewCallBack callBack : _callBackList) {
                            callBack.onEmpty();
                        }
                    } else {
                        for (IAlbumDetailViewCallBack callBack : _callBackList) {
                            callBack.onDetailListLoadedBeginning(trackList.getTracks());
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                if (_callBackList != null) {
                    for (IAlbumDetailViewCallBack callBack : _callBackList) {
                        callBack.onNetWorkError();
                    }
                }
            }
        });
    }

    private void getAlbumDetail(long id, int page, IDataCallBack<TrackList> callBack) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_ID, String.valueOf(id));
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, String.valueOf(page));
        map.put("count", String.valueOf(20));
        CommonRequest.getTracks(map, callBack);
    }

    @Override
    public void registerViewCallBack(IAlbumDetailViewCallBack callBack) {
        if (_callBackList != null && !_callBackList.contains(callBack)) {
            _callBackList.add(callBack);
        }
    }

    @Override
    public void unRegisterViewCallBack(IAlbumDetailViewCallBack callBack) {
        if (_callBackList != null) {
            _callBackList.remove(callBack);
        }
    }
}
