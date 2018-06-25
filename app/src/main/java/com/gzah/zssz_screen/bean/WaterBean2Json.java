package com.gzah.zssz_screen.bean;

/**
 * Created by 林佳荣 on 2018/5/31.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class WaterBean2Json {

    /**
     * code : 0
     * orderid : 1_2,2_3
     * machineid : 1
     */

    private String code;
    private String type;
    private String state;
    private String machineid;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMachineid() {
        return machineid == null ? "" : machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }
}
