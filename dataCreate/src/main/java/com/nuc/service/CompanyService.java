package com.nuc.service;

import com.nuc.unit.Company;
import com.nuc.unit.LoginRecord;

/**
 * @Author：朱瑞敏
 * @Description： TODO 企业Service层
 **/
public interface CompanyService {

    /**
     * 生成企业
     * @return 生成的企业
     */
    public Company createCompany();

    /**
     * 企业登陆
     * @return 企业登陆的记录信息
     */
    public LoginRecord loginCompany();


    /**
     * 通过企业id查找企业
     * @param companyId 企业id
     * @return 查找到的企业
     */
    public Company selectCompanyByCompanyId(String companyId);

    /**
     * 企业退出登陆
     * @return 退出登陆记录
     */
    public LoginRecord outCompany();


}
