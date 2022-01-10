package sg.just4fun.tgasdk.modle;

public class BipGameUserUser {
        private int id;//":"用户的第三方ID",
        private String appId;//":"BIP的APPID",
        private String txnId;//":"BIP的唯一ID"
        private String header;//":"用户头像",
        private String name;//":"用户昵称",
        private String coin;//":用户金币数,
        private String energy;//":用户体力,
        private boolean first;//":true/false-是否首次登录,

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getTxnId() {
            return txnId;
        }

        public void setTxnId(String txnId) {
            this.txnId = txnId;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getEnergy() {
            return energy;
        }

        public void setEnergy(String energy) {
            this.energy = energy;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }
}
