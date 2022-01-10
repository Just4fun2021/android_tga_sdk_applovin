package sg.just4fun.tgasdk.adsdk.applovin;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;

import androidx.annotation.RequiresApi;
import androidx.core.util.Consumer;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.gson.Gson;

import java.util.UUID;

import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.TgaAdType;
import sg.just4fun.tgasdk.adsdk.TgaApiBean;
import sg.just4fun.tgasdk.adsdk.TgaEventBaseInfo;
import sg.just4fun.tgasdk.adsdk.TgaSdkEmptyJsonEntity;
import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;
import sg.just4fun.tgasdk.web.TgaSdk;
import sg.just4fun.tgasdk.web.banner.AppLovinAdPlacementConfig;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class ApplovinApiBean  implements TgaApiBean{


    public static final String MODEL_NAME = "applovin";
    public static final String TAG = "tga_" + MODEL_NAME;
//    public static final String WTF = "fak123";

    public static final TgaSdkJsonEntity EMPTY_MAP = new TgaSdkEmptyJsonEntity();
    public static final int TOUUID = 0;
    public static final int REFRESH = 1;
    public static final int FLUSH = 2;
    public static final int TOADUNITID = 3;
    public static final int HARD_MAX_CACHE_LENGTH = 128; // CACHE AT MOST 128 UUIDS
    public static final int SOFT_MAX_CACHE_LENGTH = 64; // IF OVER 128 CACHE WILL CUT OFF TO THIS SIZE


    private final String tgaUrl;
    private final Activity context;
    private final WebView webView;

    private MaxInterstitialAd interstitialAd;
    private MaxInterstitialAd interstitialAd2;
    private boolean interstitialAdLoaded2 = false;
    private boolean interstitialAdLoaded = false;
    private ApplovingAdJob interstitialAdJob;
    private ApplovingAdJob interstitialAdJob2;
    private MaxRewardedAd rewardedAd;
    private boolean rewardedAdLoaded = false;
    private ApplovingAdJob rewardedAdJob;

    private MaxAdView adView;
    private boolean bannerAdLoaded = false;
    private ApplovinBannerAdJob bannerAdJob;

    public MaxInterstitialAd getInterstitialAd2() {
        return interstitialAd2;
    }
    public void setInterstitialAd2(MaxInterstitialAd interstitialAd2) {
        this.interstitialAd2 = interstitialAd2;
    }

    public boolean isInterstitialAdLoaded2() {
        return interstitialAdLoaded2;
    }

    public void setInterstitialAdLoaded2(boolean interstitialAdLoaded2) {
        this.interstitialAdLoaded2 = interstitialAdLoaded2;
    }



    public MaxInterstitialAd getInterstitialAd() {
        return interstitialAd;
    }

    public void setInterstitialAd(MaxInterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
    }

    public boolean isInterstitialAdLoaded() {
        return interstitialAdLoaded;
    }

    public void setInterstitialAdLoaded(boolean interstitialAdLoaded) {
        this.interstitialAdLoaded = interstitialAdLoaded;
    }

    public MaxAdView getAdView() {
        return adView;
    }

    public void setAdView(MaxAdView adView) {
        this.adView = adView;
    }

    public boolean isBannerAdLoaded() {
        return bannerAdLoaded;
    }

    public void setBannerAdLoaded(boolean bannerAdLoaded) {
        this.bannerAdLoaded = bannerAdLoaded;
    }

    public MaxRewardedAd getRewardedAd() {
        return rewardedAd;
    }

    public void setRewardedAd(MaxRewardedAd rewardedAd) {
        this.rewardedAd = rewardedAd;
    }

    public boolean isRewardedAdLoaded() {
        return rewardedAdLoaded;
    }

    public void setRewardedAdLoaded(boolean rewardedAdLoaded) {
        this.rewardedAdLoaded = rewardedAdLoaded;
    }

    public void ensureAdLoaded(String uuid, String adTypeName, Consumer<Boolean> callback) {
        Log.d(TAG, "ensureAdLoaded(" + uuid + ", " + adTypeName +")");
        ApplovingAdPlacementType tgaAdType = toApplovingAdPlacementType(adTypeName);
        if(tgaAdType == ApplovingAdPlacementType.Other) {
            try {
                TgaAdSdkUtils.runEventForUnsupportedType(webView, uuid, MODEL_NAME, adTypeName, TAG);
            } catch (Exception e) {
                Log.e(TAG, "Error Event Commit Failed", e);
            }
        } else if(isInitiated()) {
            if(tgaAdType == ApplovingAdPlacementType.Interstitial) {
                if(isInterstitialAdLoaded()) {
                    interstitialAdJob.setUuid(uuid);
                    callback.accept(true);
                    return;
                }
                destroyInterstitialAd();
                interstitialAd = new MaxInterstitialAd(toAdUnitId(tgaAdType), context);
                interstitialAdJob = new ApplovingAdJob(uuid, this, tgaAdType).withCallback(callback);
                interstitialAd.setListener(interstitialAdJob);
                interstitialAd.loadAd();
            } else if(tgaAdType == ApplovingAdPlacementType.Interstitial2) {
                if(isInterstitialAdLoaded2()) {
                    interstitialAdJob2.setUuid(uuid);
                    callback.accept(true);
                    return;
                }
                destroyInterstitialAd2();
                interstitialAd2 = new MaxInterstitialAd(toAdUnitId(tgaAdType), context);
                interstitialAdJob2 = new ApplovingAdJob(uuid, this, tgaAdType).withCallback(callback);
                interstitialAd2.setListener(interstitialAdJob2);
                interstitialAd2.loadAd();
            }
            else if(tgaAdType == ApplovingAdPlacementType.Reward) {
                if (isRewardedAdLoaded()) {
                    rewardedAdJob.setUuid(uuid);
                    callback.accept(true);
                    return;
                }
                destroyRewardedAd();
                rewardedAd = MaxRewardedAd.getInstance(toAdUnitId(tgaAdType), context);
                rewardedAdJob = new ApplovingAdJob(uuid, this, tgaAdType).withCallback(callback);
                rewardedAd.setListener(rewardedAdJob);
                rewardedAd.loadAd();
            } else if(tgaAdType == ApplovingAdPlacementType.Banner) {
                if(isBannerAdLoaded()) {
                    Log.d("eZx4Pox", "bannerIsLoaded, now show");
                    bannerAdJob.setUuid(uuid);
                    callback.accept(true);
                    return;
                }
                if(adView != null) {
                    try{
                        adView.destroy();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
                adView = new MaxAdView(toAdUnitId(tgaAdType), context);
                bannerAdJob = new ApplovinBannerAdJob(uuid, this).withCallback(callback);
                adView.setListener(bannerAdJob);
                Log.d("eZx4Pox", "adView setListener .. ");
                webView.addView(adView, new AbsoluteLayout.LayoutParams(webView.getWidth(),webView.getWidth() / 5,0,0));
                Log.d("eZx4Pox", "addView ...  ");
                adView.loadAd();
            } else {
                try {
                    TgaAdSdkUtils.runEventForUnsupportedType(webView, uuid, MODEL_NAME, adTypeName, TAG);
                } catch (Exception e) {
                    Log.e(TAG, "Error Event Commit Failed", e);
                }
            }
        } else {
            onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME +" is not initiated"));
        }
    }

    public String unitsAction(String uuid, String adId, int actionType) {
        if(actionType == TOUUID) {
            return adId;
        } else if(actionType == TOADUNITID) {
            return uuid;
        } else if(actionType == REFRESH) {
            return null;
        } else if (actionType == FLUSH) {
//            checkUnitsAndResort();
            return null;
        } else {
            return "Error: Invalid ActionType";
        }
//        return null; // null represent peace result, then do nothing
    }

    public String toAdUnitId(ApplovingAdPlacementType placementType) {
        Gson gson = new Gson();
        Log.e("广告","applovnId="+TgaSdk.applovnIdConfig);
        AppLovinAdPlacementConfig appLovinAdPlacementConfig = gson.fromJson(TgaSdk.applovnIdConfig, AppLovinAdPlacementConfig.class);
        switch (placementType){
                case Interstitial:
//                    Log.e(WTF,"getInterstitial="+ bannerBean.getInterstitial());
                    Log.e("广告","applovnId="+appLovinAdPlacementConfig.getTitleAd());
                        return appLovinAdPlacementConfig.getTitleAd() ;
            case Interstitial2:
//                    Log.e(WTF,"getInterstitial="+ bannerBean.getInterstitial());
                Log.e("广告","applovnId="+appLovinAdPlacementConfig.getInterstitial());
                return appLovinAdPlacementConfig.getInterstitial() ;
                case Reward:

                        return appLovinAdPlacementConfig.getReward();
//                    Log.e(WTF,"getReward="+ bannerBean.getReward());

                case Banner:
//                    Log.e(WTF,"getBanner="+ bannerBean.getBanner());

                        return appLovinAdPlacementConfig.getBanner();

                default:
                    return null;
            }

    }



    public AppLovinSdkConfiguration appLovinSdkConfiguration;

    public ApplovinApiBean(Activity context, WebView webView, String tgaUrl) {
        this.context = context;
        this.tgaUrl = tgaUrl;
        this.webView = webView;
    }


    @Override
    public boolean isInitiated() {
        return appLovinSdkConfiguration != null; //When init success , callback function will set appLovinSdkConfiguration to a not null instance;
    }

    @Override
    public void initSdk() {
        AppLovinSdk appLovinSdk = AppLovinSdk.getInstance( context );
        appLovinSdk.setMediationProvider( "max" );
        appLovinSdk.getSettings().setVerboseLogging(true);

        AppLovinSdk.initializeSdk( context, configuration -> {
            // AppLovin SDK is initialized, start loading ads
            appLovinSdkConfiguration = configuration;
            Log.d(TAG, "sdk model " + MODEL_NAME + " init success");

            ensureAdLoaded(UUID.randomUUID().toString(), TgaAdType.FullScreen.getName(), (ApplovinAdLoadCallback) aBoolean -> Log.d(TAG, "PRELOAD FULLSCREEN RESULT  " + aBoolean));
            ensureAdLoaded(UUID.randomUUID().toString(), TgaAdType.Title.getName(), (ApplovinAdLoadCallback) aBoolean -> Log.d(TAG, "PRELOAD FULLSCREEN RESULT  " + aBoolean));

            ensureAdLoaded(UUID.randomUUID().toString(), TgaAdType.Reward.getName(), (ApplovinAdLoadCallback) aBoolean -> Log.d(TAG, "PRELOAD REWARD RESULT  " + aBoolean));

            ensureAdLoaded(UUID.randomUUID().toString(), TgaAdType.Banner.getName(), (ApplovinAdLoadCallback) aBoolean -> {
                if(aBoolean){
                    adView.setVisibility( View.GONE );
                    adView.stopAutoRefresh();
                }
                Log.d(TAG, "PRELOAD REWARD RESULT  " + aBoolean);
            });
        });


//        appLovinSdk .showMediationDebugger();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showAd(String uuid, String adTypeName) {
        Log.d("fak123","showAd"+System.currentTimeMillis());
        Log.d("eZx4Pox", "showAd"+System.currentTimeMillis());
        Log.d(TAG, "showAd(" + uuid + ", " + adTypeName +")");
        ApplovingAdPlacementType tgaAdType = toApplovingAdPlacementType(adTypeName);
        if(tgaAdType == ApplovingAdPlacementType.Other) {
            try {
                TgaAdSdkUtils.runEventForUnsupportedType(webView, uuid, MODEL_NAME, adTypeName, TAG);
            } catch (Exception e) {
                Log.e(TAG, "Error Event Commit Failed", e);
            }
        } else if(isInitiated()) {
            Log.d("eZx4Pox", "context is null ? " + (context == null));
            if(context != null) {
                Log.d("ADVIEW", "context is what ? " + context.getClass().getName());
            }
            if(tgaAdType == ApplovingAdPlacementType.Interstitial) {
                ensureAdLoaded(uuid, adTypeName, ready -> {
                    if(ready) {
                        interstitialAdJob.setUuid(uuid);
                        onEvent(uuid, "onAdLoad");
                        interstitialAd.showAd();
                    } else {
                        onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME +" is not preloaded"));
                    }
                });
            }
            else  if(tgaAdType == ApplovingAdPlacementType.Interstitial2) {
                ensureAdLoaded(uuid, adTypeName, ready -> {
                    if(ready) {
                        interstitialAdJob2.setUuid(uuid);
                        onEvent(uuid, "onAdLoad");
                        interstitialAd2.showAd();
                    } else {
                        onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME +" is not preloaded"));
                    }
                });
            }
            else if(tgaAdType == ApplovingAdPlacementType.Reward) {
                ensureAdLoaded(uuid, adTypeName, ready -> {
                    if (ready) {
                        rewardedAdJob.setUuid(uuid);
                        onEvent(uuid, "onAdLoad");
                        rewardedAd.showAd();
                    } else {
                        onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME + " is not preloaded"));
                    }
                });
            } else if(tgaAdType == ApplovingAdPlacementType.Banner) {
                Log.d("eZx4Pox", "tgaAdType == ApplovingAdPlacementType.Banner" );
                //TODO: 如果 context instanceof 我们的接口， 那么 通过接口显示控件，病执行
//                ensureAdLoaded(uuid, adTypeName, ready -> {
//                    Log.d("eZx4Pox", "Banner is ready ? " + ready);
//                    if (ready) {
                try{

                    runOnUiThread(new Runnable() {
                     public void run() {
                         adView.setVisibility( View.VISIBLE );
                         adView.startAutoRefresh();
                     }
                 });


             }catch (Exception e){
                 Log.d("eZx4Pox", "tgaAdType  "+e.getMessage().toString() );
             }

//                    } else {
//                        Log.e("eZx4Pox","no");
//                        onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME + " is not preloaded"));
//                    }
//                });
            } else {
                try {
                    TgaAdSdkUtils.runEventForUnsupportedType(webView, uuid, MODEL_NAME, adTypeName, TAG);
                } catch (Exception e) {
                    Log.e(TAG, "Error Event Commit Failed", e);
                }
            }
        } else {
            onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME +" is not initiated"));
        }
    }

    @Override
    public void showBannerAd(String uuid, String adTypeName, String paramsJson) {
        Log.d(TAG, "showBannerAd ...");
        try {
            ensureAdLoaded(uuid, adTypeName, ready -> {
                if (ready) {
                    onEvent(uuid, "onAdLoad");
                    runOnUiThread(new Runnable() {
                        public void run() {

                            adView.setVisibility( View.VISIBLE );
                            adView.startAutoRefresh();
                        }
                    });

                } else {
                    onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME + " is not preloaded"));
                }
            });
//            TgaAdSdkUtils.runEventForUnsupportedType(webView, uuid, MODEL_NAME, adTypeName, TAG);
        } catch (Exception e) {
            Log.e(TAG, "Error Event Commit Failed", e);
        }
    }

    @Override
    public void hideBannerAd(String uuid,String adTypeName) {
        ensureAdLoaded(uuid, TgaAdType.Banner.getName(), ready -> {
            if (ready) {
                onEvent(uuid, "onAdLoad");
        Log.d("eZx4Pox", "tgaAdType == 将控件隐藏" );
        //TODO: 将控件隐藏
                runOnUiThread(new Runnable() {
                    public void run() {
                        adView.setVisibility( View.GONE );
                        adView.stopAutoRefresh();
                    }
                });

            } else {
                onEvent(uuid, "onError", toErrorExt("showAd", "SDK MODEL-" + MODEL_NAME + " is not preloaded"));
            }
        });
    }
    public ApplovingAdInfo toAdInfo(MaxAd ad, MaxReward reward) {
        if(ad == null) {
            return null;
        }
        if(reward == null) {
            return new ApplovingAdInfo(ad.getAdUnitId(), ad.getNetworkName(), formatOf(ad));
        } else {
            return new ApplovingAdInfo(ad.getAdUnitId(), ad.getNetworkName(), formatOf(ad), reward.getAmount(), reward.getLabel());
        }
    }

    public ApplovingAdInfo toAdInfo(MaxAd ad) {
        if(ad == null) {
            return null;
        }
        return new ApplovingAdInfo(ad.getAdUnitId(), ad.getNetworkName(), formatOf(ad));
    }

    public String formatOf(MaxAd ad) {
        if(ad == null || ad.getFormat() == null) {
            return null;
        }
        return ad.getFormat().getLabel();
    }

    public void onEvent(String uuid, String methodName) {
        onEvent(new TgaEventBaseInfo(
                uuid,
                methodName,
                MODEL_NAME
        ));
    }

    public void onEvent(String uuid, String methodName, TgaSdkJsonEntity data) {
        onEvent(new TgaEventBaseInfo(
                uuid,
                methodName,
                MODEL_NAME
        ), data);
    }

    public void onEvent(TgaEventBaseInfo baseInfo) {
        onEvent(baseInfo, EMPTY_MAP);
    }
    public void onEvent(TgaEventBaseInfo baseInfo, TgaSdkJsonEntity data) {
        try{
            TgaAdSdkUtils.runEvent(webView, baseInfo, data, TAG);
        } catch(Exception e) {
            Log.e(TAG, "Run Event Error", e);
        }
    }


    public ApplovingAdErrorEvent toErrorExt(String realMethodName, MaxAd ad, int errorCode) {
        return new ApplovingAdErrorEvent(realMethodName, toAdInfo(ad), errorCode);
    }

    public ApplovingAdErrorEvent toErrorExt(String realMethodName, String adUnitId, int errorCode) {
        return new ApplovingAdErrorEvent(realMethodName, adUnitId, errorCode);
    }

    public ApplovingAdErrorEvent toErrorExt(String realMethodName, String erroMessage) {
        return new ApplovingAdErrorEvent(realMethodName, erroMessage);
    }

    public ApplovingAdPlacementType toApplovingAdPlacementType(String adType) {
        return toApplovingAdPlacementType(TgaAdType.toTgaAdType(adType));
    }

    public ApplovingAdPlacementType toApplovingAdPlacementType(TgaAdType adType) {
        switch (adType) {
            case Banner:
                return ApplovingAdPlacementType.Banner;
            case FullScreen:
            case NoSkip:

                    return ApplovingAdPlacementType.Interstitial;
            case Title:
                    return ApplovingAdPlacementType.Interstitial2;
                
            case Reward:
            case RewardNoSkip:
                return ApplovingAdPlacementType.Reward;
            default:
                return ApplovingAdPlacementType.Other;
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    public void destroyInterstitialAd() {
        if(interstitialAd != null) {
            try{
                this.interstitialAd.destroy();
            } catch(Exception e){
                Log.e(TAG, e.getMessage(), e);
            }
        }
        this.interstitialAdJob = null;
        this.interstitialAdLoaded = false;
    }

    public void destroyInterstitialAd2() {
        if(interstitialAd2 != null) {
            try{
                this.interstitialAd2.destroy();
            } catch(Exception e){
                Log.e(TAG, e.getMessage(), e);
            }
        }
        this.interstitialAdJob2 = null;
        this.interstitialAdLoaded2 = false;
    }

    public void destroyRewardedAd() {
        if(rewardedAd != null) {
            try{
                this.rewardedAd.destroy();
            } catch(Exception e){
                Log.e(TAG, e.getMessage(), e);
            }
        }
        this.rewardedAdJob = null;
        this.rewardedAdLoaded = false;
    }

}
