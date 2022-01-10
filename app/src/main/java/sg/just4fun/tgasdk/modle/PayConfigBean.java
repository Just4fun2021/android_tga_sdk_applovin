package sg.just4fun.tgasdk.modle;

public class PayConfigBean {

    public String moduleAppId;//模块自身appid
    private String channelName;//渠道名
    private boolean enabled;//模块开关 enabled  actived
    private boolean actived;//是否支持支付能力

    public boolean getActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
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

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public PayConfigBean(String channelName, boolean actived, boolean enabled, String moduleAppId) {
        this.channelName = channelName;
        this.enabled = enabled;
        this.moduleAppId = moduleAppId;
        this.actived = actived;
    }
}