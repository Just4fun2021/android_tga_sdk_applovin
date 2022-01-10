package sg.just4fun.tgasdk.tga.ui.home.model;

public class RegisterBean {

     /*    "desc": "SUCCESS",
            "accessToken":"登录token,大部分接口操作使用该token",
            "refreshToken":"刷新token,登录token过期时,调用该token静默刷新",
            "tokenType":"固定为Bearer,暂未使用",
            "user":{
                "id":"用户ID",
                "header":"用户头像地址",
                "coin":"用户金币数量,数值型",
                "energy":"用户能量数量,数值型"
    }*/

     private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    private String accessToken;
    private String refreshToken;
    private String tokenType;

    private UserInfo user;



    public class UserInfo{
        private String id;//":"用户ID",
        private String header;//":"用户头像地址",

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public int getEnergy() {
            return energy;
        }

        public void setEnergy(int energy) {
            this.energy = energy;
        }

        private String coin;//":"用户金币数量,数值型",
        private int  energy;//":"用户能量数量,数值型"

    }

}
