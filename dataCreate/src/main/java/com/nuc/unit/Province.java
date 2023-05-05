package com.nuc.unit;

/**
 * @Author：朱瑞敏
 * @Description： TODO 省级行政区实体类
 **/
public class Province {

    private String province_id;
    private String province_name;
    private int province_level;

    public Province() {
    }

    public Province(String province_id, String province_name, int province_level) {
        this.province_id = province_id;
        this.province_name = province_name;
        this.province_level = province_level;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getProvince_level() {
        return province_level;
    }

    public void setProvince_level(int province_level) {
        this.province_level = province_level;
    }

    @Override
    public String toString() {
        return "Province{" +
                "province_id='" + province_id + '\'' +
                ", province_name='" + province_name + '\'' +
                ", province_level=" + province_level +
                '}';
    }
}
