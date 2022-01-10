package sg.just4fun.tgasdk.modle;

import org.json.JSONObject;

import java.util.List;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class AppConfig implements TgaSdkJsonEntity {
    private List<AdConfigBean> ad;
    private List<PayConfigBean> payMentList;
    private ShareBean share;
    private String gameCentreUrl;
    private String appId;


    public AppConfig(List<AdConfigBean> ad, List<PayConfigBean> payMentList, ShareBean share, String gameCentreUrl, String appId){
        this.ad=ad;
        this.payMentList=payMentList;
        this.share=share;
        this. gameCentreUrl=gameCentreUrl;
        this. appId=appId;
    }
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<AdConfigBean> getAd() {
        return ad;
    }

    public void setAd(List<AdConfigBean> ad) {
        this.ad = ad;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public String getGameCentreUrl() {
        return gameCentreUrl;
    }

    public void setGameCentreUrl(String gameCentreUrl) {
        this.gameCentreUrl = gameCentreUrl;
    }

    public List<PayConfigBean> getPayMentList() {
        return payMentList;
    }

    public void setPayMentList(List<PayConfigBean> payMentList) {
        this.payMentList = payMentList;
    }


    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ad", ad);
        jsonObject.put("payMentList", payMentList);
        jsonObject.put("share", share);
        jsonObject.put("appId", appId);
        jsonObject.put("gameCentreUrl", gameCentreUrl);
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

    }
}
