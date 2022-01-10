package sg.just4fun.tgasdk.adsdk;

import org.json.JSONObject;

public class TgaSdkEventDataBean  {
    public TgaSdkEventDataBean() {
    }

    private String uuid;
    private TgaSdkJsonEntity data;
//    private String listData;
//    public TgaSdkEventDataBean(String uuid, String listData) {
//        this.uuid = uuid;
//        this.listData = listData;
//    }
    public TgaSdkEventDataBean(TgaSdkJsonEntity data) {
        this.data = data;
    }
    public TgaSdkEventDataBean(String uuid, TgaSdkJsonEntity data) {
        this.uuid = uuid;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TgaSdkJsonEntity getData() {
        return data;
    }

    public void setData(TgaSdkJsonEntity data) {
        this.data = data;
    }

    public String toJsCode() throws Exception {
        JSONObject res = new JSONObject();
        res.put("uuid", uuid);
//        if(listData!=null){
//
//            res.put("data", listData);
//        }
        if (data != null ){
            res.put("data", data.toJson());
        }
        return res.toString();
    }
}
