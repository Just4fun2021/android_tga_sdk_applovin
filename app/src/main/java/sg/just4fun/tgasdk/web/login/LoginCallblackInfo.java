package sg.just4fun.tgasdk.web.login;

import org.json.JSONObject;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;
import sg.just4fun.tgasdk.modle.BipGameUserInfo;

public class LoginCallblackInfo implements TgaSdkJsonEntity {

    private String method = "onCode";
    private boolean success;
    private String accessToken;//":"用户Token",
    private String refreshToken;//":"刷新Token",
    public LoginCallblackInfo(boolean success,String accessToken,String refreshToken) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }
    public LoginCallblackInfo(boolean success) {
        this.success = success;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", this.success);
        jsonObject.put("method", this.method);
        jsonObject.put("accessToken", this.accessToken);
        jsonObject.put("refreshToken", this.refreshToken);
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

    }
}
