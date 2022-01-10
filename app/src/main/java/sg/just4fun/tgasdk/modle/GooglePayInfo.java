package sg.just4fun.tgasdk.modle;

public class GooglePayInfo {

    public String wareId;//":"商品ID",
    public String thirdWareId;//":"谷歌支付的商品ID"

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }

    public String getThirdWareId() {
        return thirdWareId;
    }

    public void setThirdWareId(String thirdWareId) {
        this.thirdWareId = thirdWareId;
    }
}