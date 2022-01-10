package sg.just4fun.tgasdk.modle;

public class BipGameUserInfo {
//    {
//        "stateCode":1, "resultInfo":{
//        "accessToken":"at-ff585ae346184e9e9d58b9bed20f49e2-tga",
//        "tokenType":"Bearer",
//         "user":{
//            "id":22256,
//            "appId":"47b1181148da4063b5ef17759dcaee25",
//            "txnId":"32",
//            "name":"",
//            "header":"",
//            "coin":0,
//            "energy":0,
//            "first":true
//        },
//        "desc":"SUCCESS",
//        "refreshToken":"rt-8ccbb8cf9c174362a7f010f3504a3642-tga"
//    }
//    }

    private String desc;
    private String tokenType;//":"token类型",
    private String accessToken;//":"用户Token",
    private String refreshToken;//":"刷新Token",
    private BipGameUserUser user;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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

    public BipGameUserUser getUser() {
        return user;
    }

    public void setUser(BipGameUserUser user) {
        this.user = user;
    }


}
