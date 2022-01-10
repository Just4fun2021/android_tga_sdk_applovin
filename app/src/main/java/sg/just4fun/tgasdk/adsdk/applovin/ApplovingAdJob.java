package sg.just4fun.tgasdk.adsdk.applovin;

import android.util.Log;

import androidx.core.util.Consumer;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;

import java.util.LinkedList;
import java.util.List;

import sg.just4fun.tgasdk.adsdk.TgaAdType;

public class ApplovingAdJob implements MaxAdListener, MaxRewardedAdListener {

    private String uuid;
    private final String TAG = ApplovinApiBean.TAG;
    private final ApplovinApiBean apiBean;
//    private MaxInterstitialAd interstitialAd;
//    private MaxRewardedAd rewardedAd;
    private final ApplovingAdPlacementType placementType;
//    private boolean loaded = false;

    private List<Consumer<Boolean>> callbacks = new LinkedList<>();

    public ApplovingAdJob(String uuid, ApplovinApiBean apiBean, ApplovingAdPlacementType placementType) {
        this.uuid = uuid;
        this.apiBean = apiBean;
        this.placementType = placementType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ApplovingAdJob withCallback(Consumer<Boolean> callback) {
        if(callback != null) {
            callbacks.add(callback);
        }
        return this;
    }

//    public void showAd(String uuid) {
//        if(loaded) {
//            if(interstitialAd != null) {
//                interstitialAd.showAd();
//            } else if(rewardedAd != null) {
//                rewardedAd.showAd();
//            }
//            return;
//        }
//        try{
//
//            apiBean.getInterstitialAd();
//        } catch(Exception e) {
//
//        }
//
//
//    }




    @Override
    public void onAdLoaded(MaxAd ad) {//预加载
        Log.d("fak123","onAdLoaded"+System.currentTimeMillis());
        final List<Consumer<Boolean>> cacheCallbacks = callbacks;
        this.callbacks = new LinkedList<>();
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onAdLoaded But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onAdPreLoad"); //Report To SDK as event
                if(placementType == ApplovingAdPlacementType.Interstitial) {
                    apiBean.setInterstitialAdLoaded(true);
//                    apiBean.getInterstitialAd().showAd();
                } else if(placementType == ApplovingAdPlacementType.Interstitial2){
                    apiBean.setInterstitialAdLoaded2(true);

                }else if(placementType == ApplovingAdPlacementType.Reward) {
                    apiBean.setRewardedAdLoaded(true);
//                    apiBean.getRewardedAd().showAd();
                }
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
    public void onAdLoadFailed(String adUnitId, MaxError errorCode) {//
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
        Log.d("fak123","onAdDisplayed"+System.currentTimeMillis() + " uuid=" + uuid);
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
        Log.d("fak123","onAdHidden"+System.currentTimeMillis() + " uuid=" + uuid);
        try{
            if(ad.getFormat().getDisplayName().equals(MaxAdFormat.INTERSTITIAL.getDisplayName())){
//                String uuid
                Log.d("fak123","false"+System.currentTimeMillis());
                apiBean.setInterstitialAdLoaded(false);
                apiBean.ensureAdLoaded(uuid, TgaAdType.FullScreen.getName(), new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        Log.d("fak123", "PRELOAD FULLSCREEN RESULT  " + aBoolean);
                    }
                });
                apiBean.setInterstitialAdLoaded2(false);
                apiBean.ensureAdLoaded(uuid, TgaAdType.Title.getName(), new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        Log.d("fak123", "PRELOAD FULLSCREEN RESULT  " + aBoolean);
                    }
                });

            } else if(ad.getFormat().getDisplayName().equals(MaxAdFormat.REWARDED.getDisplayName())){
                apiBean.setRewardedAdLoaded(false);
                apiBean.ensureAdLoaded(uuid, TgaAdType.Reward.getName(), new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        Log.d("fak123", "PRELOAD REWARD RESULT  " + aBoolean);
                    }
                });
            }
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

    @Override
    public void onRewardedVideoStarted(MaxAd ad) {
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onRewardedVideoStarted But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onRewardedVideoStarted", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onRewardedVideoStarted with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    @Override
    public void onRewardedVideoCompleted(MaxAd ad) {
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onRewardedVideoCompleted But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onRewardedVideoCompleted", apiBean.toAdInfo(ad)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onRewardedVideoCompleted with an illegal MaxAd input, SDK will ignore this event");
        }
    }

    @Override
    public void onUserRewarded(MaxAd ad, MaxReward reward) {
        if(ad != null && ad.getAdUnitId() != null) {
            if(uuid == null) {
                Log.e(TAG, "onUserRewarded But uuid lost, SDK will ignore this event");
            } else {
                apiBean.onEvent(uuid, "onUserRewarded", apiBean.toAdInfo(ad, reward)); //Report To SDK as event
            }
        } else {
            Log.e(TAG, "onUserRewarded with an illegal MaxAd input, SDK will ignore this event");
        }
    }

}
