package com.nuc.unit;

/**
 * @Author：朱瑞敏
 * @Description： TODO 县级行政区
 **/
public class County {

    private String city_id;
    private String county_id;
    private String county_name;
    private int county_level;

    public County() {
    }

    public County(String city_id, String county_id, String county_name, int county_level) {
        this.city_id = city_id;
        this.county_id = county_id;
        this.county_name = county_name;
        this.county_level = county_level;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public int getCounty_level() {
        return county_level;
    }

    public void setCounty_level(int county_level) {
        this.county_level = county_level;
    }

    @Override
    public String toString() {
        return "County{" +
                "city_id='" + city_id + '\'' +
                ", county_id='" + county_id + '\'' +
                ", county_name='" + county_name + '\'' +
                ", county_level=" + county_level +
                '}';
    }
}
