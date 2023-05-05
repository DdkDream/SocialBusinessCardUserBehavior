package com.nuc.unit;

/**
 * @Author：朱瑞敏
 * @Description： TODO 地级市行政区
 **/
public class City {

    private String province_id;
    private String city_id;
    private String city_name;
    private int city_level;

    public City() {
    }

    public City(String province_id, String city_id, String city_name, int city_level) {
        this.province_id = province_id;
        this.city_id = city_id;
        this.city_name = city_name;
        this.city_level = city_level;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity_level() {
        return city_level;
    }

    public void setCity_level(int city_level) {
        this.city_level = city_level;
    }

    @Override
    public String toString() {
        return "City{" +
                "province_id='" + province_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city_name='" + city_name + '\'' +
                ", city_level=" + city_level +
                '}';
    }
}
