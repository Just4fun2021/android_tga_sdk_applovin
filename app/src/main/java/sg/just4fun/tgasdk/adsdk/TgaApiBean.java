package sg.just4fun.tgasdk.adsdk;

/**
 *
 * EveryApiRequire a bean to keep live
 *
 */
public interface TgaApiBean {

    void initSdk();
    void showAd(final String uuid, String adTypeName);
    void showBannerAd(final String uuid, String adTypeName, String paramsJson);
    void hideBannerAd(final String uuid, String adTypeName);
    boolean isInitiated();
    String getTag();

}
