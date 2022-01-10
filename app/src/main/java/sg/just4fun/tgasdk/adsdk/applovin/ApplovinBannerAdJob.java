package sg.just4fun.tgasdk.adsdk.applovin;

import android.util.Log;

import androidx.core.util.Consumer;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;

import java.util.LinkedList;
import java.util.List;

import sg.just4fun.tgasdk.adsdk.TgaAdType;

public class ApplovinBannerAdJob implements MaxAdViewAdListener {

    private String uuid;
    private final String TAG = ApplovinApiBean.TAG;
    private final ApplovinApiBean apiBean;
    private List<Consumer<Boolean>> callbacks = new LinkedList<>();

    public ApplovinBannerAdJob(String uuid, ApplovinApiBean apiBean) {
        this.uuid = uuid;
        this.apiBean = apiBean;
    }

    public ApplovinBannerAdJob withCallback(Consumer<Boolean> callback) {
        if(callback != null) {
            callbacks.add(callback);
        }
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void onAdExpanded(MaxAd ad) {
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdExpanded But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdExpanded", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onAdExpanded with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    @Override
    public void onAdCollapsed(MaxAd ad) {
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdCollapsed But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdCollapsed", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onAdCollapsed with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        final List<Consumer<Boolean>> cacheCallbacks = callbacks;
        this.callbacks = new LinkedList<>();
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdLoaded But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdPreLoad"); //Report To SDK as event
                apiBean.setBannerAdLoaded(true);
            }
        } else {
            Log.e(TAG, "onAdLoaded with an illegal MaxAd input, SDK will ignore this event");
        }
        for(Consumer<Boolean> callback: cacheCallbacks) {
            try{
                callback.accept(true);
            } catch(Exception e) {

            }
        }
    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError errorCode) {
        int code = errorCode.getCode();
        Log.d("fak123","onAdLoadFailed"+System.currentTimeMillis());
        final List<Consumer<Boolean>> cacheCallbacks = callbacks;
        this.callbacks = new LinkedList<>();
        if(uuid == null) {
            Log.e(TAG, "onError But uuid lost, SDK will ignore this event");
        } else {
            apiBean.onEvent(uuid, "onError", apiBean.toErrorExt("onAdLoadFailed", adUnitId, code)); //Report To SDK onAdLoad
        }

        for(Consumer<Boolean> callback: cacheCallbacks) {
            try{
                callback.accept(false);
            } catch(Exception e) {

            }
        }
    }

    @Override
    public void onAdDisplayed(MaxAd ad) {
        Log.d("fak123","onAdDisplayed"+System.currentTimeMillis());
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdDisplayed But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdDisplayed", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onAdDisplayed with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    @Override
    public void onAdHidden(MaxAd ad) {
        Log.d("fak123","onAdHidden"+System.currentTimeMillis());
        try{
            apiBean.setBannerAdLoaded(false);
            apiBean.ensureAdLoaded(uuid, TgaAdType.Banner.getName(), new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) {
                    Log.d("fak123", "PRELOAD BANNER RESULT  " + aBoolean);
                }
            });
        } catch(Exception e) {
            Log.e("fak123", "Error ", e);
        }
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdHidden But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdHidden", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onAdHidden with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    @Override
    public void onAdClicked(MaxAd ad) {
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdClick But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdClick", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onAdClick with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    public void onAdRevenuePaid(MaxAd ad) {

    }


    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError errorCode) {
        int code = errorCode.getCode();
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onError But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onError", apiBean.toErrorExt("onAdDisplayFailed", ad,  code)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onError with an illegal MaxAd input, SDK will ignore this event");
        }
    }
}
