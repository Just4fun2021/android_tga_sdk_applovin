package sg.just4fun.tgasdk.modle;

import sg.just4fun.tgasdk.web.banner.AppLovinAdPlacementConfig;

public class AdConfigBean  {
    public AdConfigBean() {
    }

    public String moduleAppId;//模块自身appid

    private String channelName;//渠道名

    private double weight;//权重

    private boolean enabled;//模块开关 enabled  actived
    private AppLovinAdPlacementConfig config;

    public AppLovinAdPlacementConfig getConfig() {
        return config;
    }

    public void setConfig(AppLovinAdPlacementConfig config) {
        this.config = config;
    }

    public String getModuleAppId() {
        return moduleAppId;
    }

    public void setModuleAppId(String moduleAppId) {
        this.moduleAppId = moduleAppId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setNeed(boolean enabled) {
        this.enabled = enabled;
    }

    public AdConfigBean(String channelName, double weight, boolean enabled, String moduleAppId) {
        this.channelName = channelName;
        this.weight = weight;
        this.enabled = enabled;
        this.moduleAppId = moduleAppId;
    }

//        @Override
//        public JSONObject toJson() throws Exception {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("channelName", channelName);
//            jsonObject.put("weight", weight);
//            jsonObject.put("enabled", enabled);
//            jsonObject.put("moduleAppId", moduleAppId);
//            return jsonObject;
//        }
//
//        @Override
//        public void fromJson(JSONObject jsonObject) throws Exception {
//
//        }
}