package sg.just4fun.tgasdk.tga.ui.home.model;

import org.json.JSONObject;

import java.io.Serializable;

import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class TgaSdkUserInFo implements Serializable , TgaSdkJsonEntity {
    private String userId;//":"用户userId",
    private String nickname;//":null
    private String avatar;//":"用户头像地址",
    private String gameId;//":"游戏id",

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", TgaAdSdkUtils.jsonValueOf(this.userId));
        jsonObject.put("nickname",TgaAdSdkUtils.jsonValueOf(this.nickname));
        jsonObject.put("avatar",TgaAdSdkUtils.jsonValueOf(this.avatar));
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {
        try{
            this.userId = jsonObject.getString("userId");
        } catch(Exception e) {
        }
        try{
            this.gameId = jsonObject.getString("gameId");
        } catch(Exception e) {
        }
        try{
            this.nickname = jsonObject.getString("nickname");
        } catch(Exception e) {

        }
        try{
            this.avatar = jsonObject.getString("avatar");
        } catch(Exception e) {
        }
    }
    public TgaSdkUserInFo() {
    }
    public TgaSdkUserInFo(String gameId) {
        this.gameId = gameId;
    }
    public TgaSdkUserInFo(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public TgaSdkUserInFo(String userId,  String nickname, String avatar) {
        this.userId = userId;
        this.avatar = avatar;
        this.nickname = nickname;
    }
    public TgaSdkUserInFo(String userId,  String nickname, String avatar,String gameId) {
        this.userId = userId;
        this.avatar = avatar;
        this.gameId = gameId;
        this.nickname = nickname;

    }

}
