package com.example.administrator.mycommonlibrarydemo.example.http_get_data;

import java.util.List;

/**
 * Created by wuyufeng    on  2018/7/11 0011.
 * interface by
 */

public class MainBean {

    /**
     * msg : success
     * code : 1
     * tip : 获取成功
     * data : {"id":"1","synopsis":" 于2013年开始从事锂离子电池BMS管理系统技术研发，深圳市锂贝特能源科技有限公司成立于2015年，注册资金为1000万RMB。公司主要以锂电池保护系统(BMS)的研发、应用、生产和销售为一体的高科技公司。\r\n\r\n\r\n公司拥有多项BMS技术发明专利和实用新型专利，在大功率，大电流BMS保护系统技术领域超前行业。\r\n\r\n主要产品有汽车启动锂电池、低速车动力锂电池、动力储能锂电池等，涵盖标称电压12V至370V(0A-2000A)的锂电池组。\r\n技术专利分别是:\r\n实有新型专利 一种微分电池组）ZL2001620406833.2; \r\n实用新型专利 一种大功率电池保护系统）ZL201620406833.2; \r\n发明专利 一种平衡电芯电压的方法和系统)ZL201410345054.1;\r\n发明专利 一种电池配对系统及方法)ZL201610421075.6;\r\n发明专利 一种微分电池组) ZL201610552342.3。","carousel":["/upload/ueditor/image/20180512/1526097014926758.jpg","/upload/ueditor/image/20180512/1526097029416615.jpg","/upload/ueditor/image/20180512/1526097041604333.jpg","/upload/ueditor/image/20180512/1526097052108897.jpg"],"phone":"0755-86007082","mobile":"18682145899","address":"深圳市南山区西丽镇"}
     */

    private String msg;
    private int code;
    private String tip;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * synopsis :  于2013年开始从事锂离子电池BMS管理系统技术研发，深圳市锂贝特能源科技有限公司成立于2015年，注册资金为1000万RMB。公司主要以锂电池保护系统(BMS)的研发、应用、生产和销售为一体的高科技公司。


         公司拥有多项BMS技术发明专利和实用新型专利，在大功率，大电流BMS保护系统技术领域超前行业。

         主要产品有汽车启动锂电池、低速车动力锂电池、动力储能锂电池等，涵盖标称电压12V至370V(0A-2000A)的锂电池组。
         技术专利分别是:
         实有新型专利 一种微分电池组）ZL2001620406833.2; 
         实用新型专利 一种大功率电池保护系统）ZL201620406833.2; 
         发明专利 一种平衡电芯电压的方法和系统)ZL201410345054.1;
         发明专利 一种电池配对系统及方法)ZL201610421075.6;
         发明专利 一种微分电池组) ZL201610552342.3。
         * carousel : ["/upload/ueditor/image/20180512/1526097014926758.jpg","/upload/ueditor/image/20180512/1526097029416615.jpg","/upload/ueditor/image/20180512/1526097041604333.jpg","/upload/ueditor/image/20180512/1526097052108897.jpg"]
         * phone : 0755-86007082
         * mobile : 18682145899
         * address : 深圳市南山区西丽镇
         */

        private String id;
        private String img;
        private String synopsis;
        private String phone;
        private String mobile;
        private String address;
        private String company;
        private List<String> carousel;

        public String getImg() {
            return img;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<String> getCarousel() {
            return carousel;
        }

        public void setCarousel(List<String> carousel) {
            this.carousel = carousel;
        }

        @Override
        public String toString() {
            return "DataBean{"
                + "id='"
                + id
                + '\''
                + ", img='"
                + img
                + '\''
                + ", synopsis='"
                + synopsis
                + '\''
                + ", phone='"
                + phone
                + '\''
                + ", mobile='"
                + mobile
                + '\''
                + ", address='"
                + address
                + '\''
                + ", company='"
                + company
                + '\''
                + ", carousel="
                + carousel
                + '}';
        }
    }

    @Override
    public String toString() {
        return "MainBean{"
            + "msg='"
            + msg
            + '\''
            + ", code="
            + code
            + ", tip='"
            + tip
            + '\''
            + ", data="
            + data
            + '}';
    }
}