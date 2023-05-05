package com.nuc.service.impl;

import com.nuc.dao.*;
import com.nuc.dao.impl.*;
import com.nuc.service.CompanyService;
import com.nuc.unit.*;

import java.util.List;
import java.util.Random;

/**
 * @Author：朱瑞敏
 * @Description： TODO 企业service实现类
 **/
public class CompanyServiceImpl implements CompanyService {

    /**
     * 生成的企业
     * @return 随机生成的企业
     */
    @Override
    public Company createCompany() {
        Company companyZero = new Company();
        companyZero.setName("create-Company-Error");

        Company company = new Company();

        // 生成企业id
        IdDao idDao = new IdDaoimpl();
        String companyId = idDao.createCompantId();

        // 生成企业名称
        NameDao nameDao = new NameDaoImpl();
        String name = nameDao.createCompanyName();

        // 生成企业联系方式
        PhoneDao phoneDao = new PhoneDaoImpl();
        String phone = phoneDao.createPhone();

        // 生成企业邮箱
        EmailDao emailDao = new EmailDaoImpl();
        String email = emailDao.createEmail();

        // 生成省级行政区
        AreaDao areaDao = new AreaDaoImpl();
        Province province = areaDao.getProvinceRandom();

        // 获得地级市行政区
        City city = areaDao.getCityByProvinceRandom(province);

        // 获得县级行政区
        County county = areaDao.getCountyByCityRandom(city);

        // 获得主营业务
        BusinessDao businessDao = new BusinessDaoImpl();
        String mainBusiness = businessDao.createBusiness();


        // 获得注册时间
        TimeDao timeDao = new TimeDaoImpl();
        String time = timeDao.getNowTime();

        company.setCompany_id(companyId);
        company.setName(name);
        company.setPhone(phone);
        company.setEmail(email);
        company.setProvince(province);
        company.setCity(city);
        company.setCounty(county);
        company.setMainBusiness(mainBusiness);
        company.setCreateTime(time);

        CompanyDao companyDao = new CompanyDaoImpl();

        int result = companyDao.insertCompany(company);

        if(result > 0){
            return company;
        }else{
            return companyZero;
        }

    }

    @Override
    public LoginRecord loginCompany() {

        // 获得登陆企业
        CompanyDao companyDao = new CompanyDaoImpl();
        List<Company> companyList = companyDao.selectAllCompany();
        Random random = new Random();
        int index = random.nextInt(companyList.size());
        Company company = companyList.get(index);

        // 获得登陆时间
        TimeDao timeDao = new TimeDaoImpl();
        String time = timeDao.getNowTime();

        // 获得记录编号
        IdDao idDao = new IdDaoimpl();
        String id = idDao.createLoginrecordId();

        // 获得登陆地址
        AreaDao areaDao = new AreaDaoImpl();
        Province province = areaDao.getProvinceRandom();
        City city = areaDao.getCityByProvinceRandom(province);
        County county = areaDao.getCountyByCityRandom(city);

        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setRecordId(id);
        loginRecord.setType(2);
        loginRecord.setId(company.getCompany_id());
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
     * 通过企业id查找企业
     * @param companyId 企业id
     * @return 查找到的企业
     */
    @Override
    public Company selectCompanyByCompanyId(String companyId) {
        CompanyDao companyDao = new CompanyDaoImpl();
        Company company = companyDao.selectCompanyByCompanyId(companyId);
        return company;
    }

    /**
     * 退出企业登陆
     * @return 退出登陆记录
     */
    public LoginRecord outCompany(){
        LoginRecordDao loginRecordDao = new LoginRecordDaoImpl();
        List<LoginRecord> loginRecordList = loginRecordDao.selectLoginCompanyRecord();
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
