package com.nuc.unit;

/**
 * @Author：朱瑞敏
 * @Description： TODO  登陆记录实例
 **/
public class LoginRecord {

    private String recordId;
    private int type;
    private String id;
    private Province province;
    private City city;
    private County county;
    private String loginTime;
    private String outTime;
    private int ifLogin;

    public LoginRecord() {
    }

    public LoginRecord(String recordId, int type, String id, Province province, City city, County county, String loginTime, String outTime, int ifLogin) {
        this.recordId = recordId;
        this.type = type;
        this.id = id;
        this.province = province;
        this.city = city;
        this.county = county;
        this.loginTime = loginTime;
        this.outTime = outTime;
        this.ifLogin = ifLogin;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public int getIfLogin() {
        return ifLogin;
    }

    public void setIfLogin(int ifLigin) {
        this.ifLogin = ifLigin;
    }

    @Override
    public String toString() {
        return "LoginRecord{" +
                "recordId='" + recordId + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                ", province=" + province +
                ", city=" + city +
                ", county=" + county +
                ", loginTime='" + loginTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", ifLigin=" + ifLogin +
                '}';
    }
}
