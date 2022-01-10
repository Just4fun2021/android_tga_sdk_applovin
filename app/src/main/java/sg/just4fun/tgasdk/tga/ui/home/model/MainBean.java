package sg.just4fun.tgasdk.tga.ui.home.model;

import java.io.Serializable;

public class MainBean {

//    {
//        "stateCode": 1,
//            "resultInfo": {
//        "desc": "SUCCESS",
//                "user":{
//            "id":"用户ID",
//                    "header":"用户头像地址",
//                    "name":"用户昵称",
//                    "vip":"用户的VIP等级,数值型,可以为空",
//                    "expire":"VIP过期时间",
//                    "isFirst":"用户是否是首次登录,用户头像和昵称至少有一个为空时为true"
//        }
//    }
//    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    private String desc;
    private UserBean user;


    public static class UserBean implements Serializable {

        public  String id;//":"用户ID",
        public  String header;//":"用户头像地址",

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }

        public  String name;//":"用户昵称",
        public  String vip;//":"用户的VIP等级,数值型,可以为空",
        public  String expire;//":"VIP过期时间",
        public  boolean isFirst;//":"用户是否是首次登录,用户头像和昵称至少有一个为空时为true"
    }



}
