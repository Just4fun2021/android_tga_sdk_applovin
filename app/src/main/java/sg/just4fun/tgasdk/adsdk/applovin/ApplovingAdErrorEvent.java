package sg.just4fun.tgasdk.adsdk.applovin;

import org.json.JSONObject;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class ApplovingAdErrorEvent implements TgaSdkJsonEntity {

    String orgMethod;
    String adUnitId;
    Integer errorCode;
    ApplovingAdInfo ad;
    String erroMessage;

    public ApplovingAdErrorEvent() {
    }

    public ApplovingAdErrorEvent(String orgMethod, String erroMessage) {
        this.orgMethod = orgMethod;
        this.erroMessage = erroMessage;
    }

    public ApplovingAdErrorEvent(String orgMethod, String adUnitId, int errorCode) {
        this.orgMethod = orgMethod;
        this.adUnitId = adUnitId;
        this.errorCode = errorCode;
    }

    public ApplovingAdErrorEvent(String orgMethod, ApplovingAdInfo ad, int errorCode) {
        this.orgMethod = orgMethod;
        this.errorCode = errorCode;
        this.ad = ad;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orgMethod", orgMethod);
        if(adUnitId != null) {
            jsonObject.put("adUnitId", adUnitId);
        }
        if(ad != null) {
            jsonObject.put("ad", ad.toJson());
        }
        jsonObject.put("errorCode", errorCode);
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {
        this.orgMethod = jsonObject.getString("orgMethod");
        if(jsonObject.has("adUnitId"))
            this.adUnitId = jsonObject.getString("adUnitId");
        if(jsonObject.has("ad")) {
            this.ad = new ApplovingAdInfo();
            this.ad.fromJson(jsonObject.getJSONObject("ad"));
        }
        this.errorCode = jsonObject.getInt("errorCode");
    }
}
