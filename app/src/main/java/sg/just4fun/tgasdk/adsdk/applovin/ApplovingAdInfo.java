package sg.just4fun.tgasdk.adsdk.applovin;

import org.json.JSONObject;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class ApplovingAdInfo implements TgaSdkJsonEntity {

    private String adUnitId;
    private String networkName;
    private String format;

    private Integer rewardAmount;
    private String rewardLabel;

    public ApplovingAdInfo() {
    }

    public ApplovingAdInfo(String adUnitId, String networkName, String format, int rewardAmount, String rewardLabel) {
        this.adUnitId = adUnitId;
        this.networkName = networkName;
        this.format = format;
        this.rewardAmount = rewardAmount;
        this.rewardLabel = rewardLabel;
    }

    public ApplovingAdInfo(String adUnitId, String networkName, String format) {
        this.adUnitId = adUnitId;
        this.networkName = networkName;
        this.format = format;
    }

    public String getAdUnitId() {
        return adUnitId;
    }

    public void setAdUnitId(String adUnitId) {
        this.adUnitId = adUnitId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getRewardLabel() {
        return rewardLabel;
    }

    public void setRewardLabel(String rewardLabel) {
        this.rewardLabel = rewardLabel;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("adUnitId", getAdUnitId());
        jsonObject.put("networkName", getNetworkName());
        jsonObject.put("format", getFormat());
        if(rewardAmount != null)
            jsonObject.put("rewardAmount", getRewardAmount());
        jsonObject.put("rewardLabel", getRewardLabel());
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {
        this.adUnitId = jsonObject.getString("adUnitId");
        this.networkName = jsonObject.getString("networkName");
        this.format = jsonObject.getString("format");
        if(jsonObject.has("rewardAmount"))
            this.rewardAmount = jsonObject.getInt("rewardAmount");
        this.rewardLabel = jsonObject.getString("rewardLabel");
    }
}
