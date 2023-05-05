package com.nuc.service.impl;

import com.nuc.dao.*;
import com.nuc.dao.impl.*;
import com.nuc.service.UserService;
import com.nuc.unit.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author：朱瑞敏
 * @Description： TODO
 **/
public class UserServiceImpl implements UserService {

    /**
     * 随机生成用户
     * @return
     */
    @Override
    public User createUser() {

        User userZero = new User();
        userZero.setName("create-User-Error");

        // 生成用户id
        IdDao idDao = new IdDaoimpl();
        String userId = idDao.createUserId();

        // 随机生成姓氏
        SurnameDao createSurnameDao = new SurnameDaoImpl();
        String surName = createSurnameDao.createSurnameDao();



        // 随机生成性别
        SexDao createSexDao = new SexDaoImpl();
        String sex = createSexDao.createSex();

        // 根据性别随机生成用户名字
        NameDao createNameDao = new NameDaoImpl();
        String name = createNameDao.createUserName(sex);


        // 随机生成年龄
        AgeDao ageDao = new AgeDaoImpl();
        int age = ageDao.createAge();

        // 随机生成地域
        AreaDao areaDao = new AreaDaoImpl();
        Province provinceRandom = areaDao.getProvinceRandom();
        City cityByProvinceRandom = areaDao.getCityByProvinceRandom(provinceRandom);
        County countyByCityRandom = areaDao.getCountyByCityRandom(cityByProvinceRandom);

        // 生成注册时间
        TimeDao timeDao = new TimeDaoImpl();
        String time = timeDao.getNowTime();

        // 生成电话
        PhoneDao phoneDao = new PhoneDaoImpl();
        String phone = phoneDao.createPhone();

        // 生成企业
        Random random = new Random();
//        int index = random.nextInt(1);
        int index = 2;
        Company company = new Company();
        if(index % 2 == 0){
            CompanyDao companyDao = new CompanyDaoImpl();
            List<Company> companyList = companyDao.selectAllCompany();
            Random compantRandom = new Random();
            int listIndex = compantRandom.nextInt(companyList.size());
            company = companyList.get(listIndex);
        }else{
            company.setName("无企业");
        }

        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setSurname(surName);
        user.setSex(sex);
        user.setAge(10);
        user.setProvince(provinceRandom);
        user.setCity(cityByProvinceRandom);
        user.setCounty(countyByCityRandom);
        user.setCreateTime(time);
        user.setPhone(phone);
        user.setCompany(company);

        int result = -1;
        UserDao userDao = new UserDaoImpl();
        result = userDao.insertUser(user);

        if(result == -1){
            return userZero;
        }
        else{
            return user;
        }
    }

    /**
     * 用户登陆
     * @return 进行登陆的用户记录
     */
    @Override
    public LoginRecord loginUser() {

        // 获得登陆的用户
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.selectNoLoginUser();
        Random random = new Random();
        int index = random.nextInt(userList.size());
        User user = userList.get(index);

        // 获得登陆的时间
        TimeDao timeDao = new TimeDaoImpl();
        String time = timeDao.getNowTime();

        // 获得记录编号
        IdDao idDao = new IdDaoimpl();
        String recordId = idDao.createLoginrecordId();

        // 获得登陆地址
        AreaDao areaDao = new AreaDaoImpl();
        Province province = areaDao.getProvinceRandom();
        City city = areaDao.getCityByProvinceRandom(province);
        County county = areaDao.getCountyByCityRandom(city);

        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setRecordId(recordId);
        loginRecord.setType(1);
        loginRecord.setId(user.getUserId());
        loginRecord.setProvince(province);
        loginRecord.setCity(city);
        loginRecord.setCounty(county);
        loginRecord.setLoginTime(time);
        loginRecord.setOutTime("null");
        loginRecord.setIfLogin(1);

        LoginRecordDao loginRecordDao = new LoginRecordDaoImpl();

        if(loginRecordDao.insertLoginRecord(loginRecord)){
            return loginRecord;
        }else{
            return null;
        }
    }

    /**
     * 通过用户id查询用户
     * @param userId 需要查询的用户id
     * @return 查询到的用户
     */
    @Override
    public User selectUserByUserId(String userId) {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.selectUserByUserId(userId);
        return user;
    }

    /**
     * 登陆的用户退出登陆
     * @return 退出登陆记录
     */
    @Override
    public LoginRecord outUser() {
        LoginRecordDao loginRecordDao = new LoginRecordDaoImpl();
        List<LoginRecord> loginRecordList = loginRecordDao.selectLoginUserRecord();
        if(loginRecordList.size() < 1){
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(loginRecordList.size());
        LoginRecord loginRecord = loginRecordList.get(index);

        String loginRecordId = loginRecord.getRecordId();

        TimeDao timeDao = new TimeDaoImpl();
        String time = timeDao.getNowTime();

        // 修改登陆记录
        boolean flag = loginRecordDao.updateUserLoginRecordByLoginRecordIdAndOutTime(loginRecordId, time);

        if(flag){
            LoginRecord loginRecordOut = new LoginRecord();
            loginRecordOut = loginRecordDao.selectLoginRecordByRecordId(loginRecordId);
            return loginRecordOut;
        }else{
            return null;
        }

    }
}
