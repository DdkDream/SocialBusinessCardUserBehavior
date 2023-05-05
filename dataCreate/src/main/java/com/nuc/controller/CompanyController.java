package com.nuc.controller;

import com.nuc.unit.Company;
import com.nuc.unit.LoginRecord;

/**
 * @Author：朱瑞敏
 * @Description： TODO 企业controller层接口
 **/
public interface CompanyController {

    /**
     * 生成新的企业
     * @return 返回生成的企业
     */
    public Company createCompany();

    /**
     * 企业登陆
     * @return 企业登陆记录信息
     */
    public LoginRecord loginCompany();

    /**
     * 通过企业id查找企业
     * @param companyId 企业id
     * @return 查找到的企业
     */
    public Company selectCompanyByCompanyId(String companyId);


    /**
     * 退出企业登陆
     * @return 退出记录
     */
    public LoginRecord outCompany();
}
