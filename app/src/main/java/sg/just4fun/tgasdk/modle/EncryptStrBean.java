package sg.just4fun.tgasdk.modle;

import org.json.JSONObject;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class EncryptStrBean implements TgaSdkJsonEntity {

    private String payId;
    private String price;
    private long payTime;
    private String wareId;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payId", payId);
        jsonObject.put("price",price);
        jsonObject.put("payTime",payTime);
        jsonObject.put("wareId",wareId);
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

    }

    public EncryptStrBean() {
    }

    public EncryptStrBean(String payId, String price, long payTime, String wareId) {
        this.payId = payId;
        this.price = price;
        this.payTime = payTime;
        this.wareId = wareId;
    }


}
