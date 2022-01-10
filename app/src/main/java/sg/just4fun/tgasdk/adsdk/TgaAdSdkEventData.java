package sg.just4fun.tgasdk.adsdk;

import org.json.JSONObject;

public class TgaAdSdkEventData implements TgaSdkJsonEntity{

    private Object ext;
    private String method;
    private String model;

    public TgaAdSdkEventData() {
    }

    public TgaAdSdkEventData(Object ext, String method, String model) {
        this.ext = ext;
        this.method = method;
        this.model = model;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
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
        jsonObject.put("method", method);
        jsonObject.put("model", model);
        if (ext != null) {
            jsonObject.put("ext" , ext);
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {
        try{
            this.method = jsonObject.getString("method");
        } catch(Exception e) {

        }
        try{
            this.model = jsonObject.getString("model");
        } catch(Exception e) {

        }
        if(!jsonObject.isNull("ext")) {
            try{
                this.ext = jsonObject.getJSONObject("ext");
            } catch(Exception e) {
                try{
                    this.ext = jsonObject.getJSONArray("ext");
                } catch (Exception e2) {
                    this.ext = jsonObject.get("ext");
                }
            }
        }
    }
}
