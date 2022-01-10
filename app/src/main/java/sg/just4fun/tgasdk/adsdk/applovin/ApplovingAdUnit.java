package sg.just4fun.tgasdk.adsdk.applovin;

import org.json.JSONObject;

import java.util.Date;

import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class ApplovingAdUnit implements Comparable<ApplovingAdUnit>, TgaSdkJsonEntity {
    private static final long DEFAULT_EXPIRE_MS = 10 * 60 * 1000L; // wait for next event for one adUnit at most 10 minutes

    private String uuid; //TGA Ad Event Group uuid
    private String adUnitId; // The corresponding apploving max ad unitId
    private Date expireTime; // Ad Expire Time, time out unit info will be removed to limit the memory

    public ApplovingAdUnit() {
    }

    public ApplovingAdUnit(String uuid, String adUnitId) {
        this(uuid, adUnitId, new Date(new Date().getTime() + DEFAULT_EXPIRE_MS));
    }

    public ApplovingAdUnit(String uuid, String adUnitId, Date expireTime) {
        this.uuid = uuid;
        this.adUnitId = adUnitId;
        this.expireTime = expireTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAdUnitId() {
        return adUnitId;
    }

    public void setAdUnitId(String adUnitId) {
        this.adUnitId = adUnitId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public boolean checkExpire() {
        return new Date().before(expireTime);
    }

    public void refreshExpireTime() {
        long now = new Date().getTime();
        setExpireTime(new Date(now + DEFAULT_EXPIRE_MS));
    }

    @Override
    public int compareTo(ApplovingAdUnit o) {
        if(o == null) {
            return -1;
        }
        if(this.getExpireTime() == null) {
            if(o.getExpireTime() == null) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if(o.getExpireTime() == null) {
                return -1;
            } else {
                return o.getExpireTime().compareTo(this.getExpireTime());
            }
        }
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", this.getUuid());
        jsonObject.put("adUnitId", this.getAdUnitId());
        jsonObject.put("expireTime", this.getExpireTime().getTime());
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {
        this.uuid = jsonObject.getString("uuid");
        this.adUnitId = jsonObject.getString("adUnitId");
        this.expireTime = TgaAdSdkUtils.tryToDate(jsonObject, "expireTime");
    }
}
