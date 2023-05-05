package com.nuc.unit;

/**
 * @Author：朱瑞敏
 * @Description： TODO 公司实体类
 **/
public class Company {

    private String company_id;

    private String name;

    private String phone;

    private String email;

    private Province province;

    private City city;

    private County county;

    private String mainBusiness;

    private String slogan;

    private String createTime;

    public Company() {
    }

    public Company(String company_id, String name, String phone, String email, Province province, City city, County county, String mainBusiness, String slogan, String createTime) {
        this.company_id = company_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.province = province;
        this.city = city;
        this.county = county;
        this.mainBusiness = mainBusiness;
        this.slogan = slogan;
        this.createTime = createTime;
    }


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return company_id +
                "-" + name +
                "-" + phone +
                "-" + email +
                "-" + province.getProvince_name() +
                "-" + city.getCity_name() +
                "-" + county.getCounty_name() +
                "-" + mainBusiness +
                "-" + slogan +
                "-" + createTime;
    }
}

