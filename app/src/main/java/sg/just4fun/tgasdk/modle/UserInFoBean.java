package sg.just4fun.tgasdk.modle;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;
import sg.just4fun.tgasdk.web.banner.AppLovinAdPlacementConfig;

public class UserInFoBean {

    public UserInFoBean() {
    }
    private String appId;
    private String packageName;
    private String appConfig;
    private String iconpath;
    private String appName;

    public String  getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(String appConfig) {
        this.appConfig = appConfig;
    }



}
