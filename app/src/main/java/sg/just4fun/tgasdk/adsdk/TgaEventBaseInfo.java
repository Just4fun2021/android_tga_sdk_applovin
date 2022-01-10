package sg.just4fun.tgasdk.adsdk;

import org.json.JSONObject;

public class TgaEventBaseInfo implements TgaSdkJsonEntity{


    private String uuid;
    private String method;
    private String model;

    public TgaEventBaseInfo() {
    }

    public TgaEventBaseInfo(String uuid, String method, String model) {
        this.uuid = uuid;
        this.method = method;
        this.model = model;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid",TgaAdSdkUtils.jsonValueOf(this.uuid));
        jsonObject.put("method",TgaAdSdkUtils.jsonValueOf(this.method));
        jsonObject.put("model",TgaAdSdkUtils.jsonValueOf(this.model));
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {
        this.uuid=jsonObject.has("uuid")?jsonObject.getString("uuid"):null;
        this.method=jsonObject.has("method")?jsonObject.getString("method"):null;
        this.model=jsonObject.has("model")?jsonObject.getString("model"):null;
    }
}
