package com.nuc.unit;

/**
 * @Author：朱瑞敏
 * @Description： TODO 用户实体类
 **/
public class User {

    private String userId;

    private String surname;

    private String name;

    private String sex;

    private int age;

    private Province province;

    private City city;

    private County county;

    private String createTime;

    private String phone;

    private Company company;

    private String position = "无";

    private String introduction = "无";

    public User() {
    }

    public User(String userId, String surname, String name, String sex, int age, Province province, City city, County county, String createTime, String phone, Company company, String position, String introduction) {
        this.userId = userId;
        this.surname = surname;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.province = province;
        this.city = city;
        this.county = county;
        this.createTime = createTime;
        this.phone = phone;
        this.company = company;
        this.position = position;
        this.introduction = introduction;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return  userId +
                "-" + surname +
                "-" + name +
                "-" + sex +
                "-" + age +
                "-" + province.getProvince_name() +
                "-" + city.getCity_name() +
                "-" + county.getCounty_name() +
                "-" + createTime +
                "-" + phone +
                "-" + company.getName() +
                "-" + position  +
                "-" + introduction;
    }
}
