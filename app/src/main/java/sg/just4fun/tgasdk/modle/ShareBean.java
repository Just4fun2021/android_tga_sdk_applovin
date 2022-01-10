package sg.just4fun.tgasdk.modle;

import java.util.List;

public class ShareBean {
    public boolean allEnabled;//总开关
    public List<ShareConfigBean> list;

    public ShareBean() {

    }

    public boolean isAllEnabled() {
        return allEnabled;
    }

    public void setAllEnabled(boolean allEnabled) {
        this.allEnabled = allEnabled;
    }

    public List<ShareConfigBean> getList() {
        return list;
    }

    public void setList(List<ShareConfigBean> list) {
        this.list = list;
    }

//        @Override
//        public JSONObject toJson() throws Exception {
//
//            return null;
//        }
//
//        @Override
//        public void fromJson(JSONObject jsonObject) throws Exception {
//
//        }
}