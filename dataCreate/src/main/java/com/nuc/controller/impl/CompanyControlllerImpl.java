package com.nuc.controller.impl;

import com.nuc.controller.CompanyController;
import com.nuc.service.CompanyService;
import com.nuc.service.impl.CompanyServiceImpl;
import com.nuc.unit.Company;
import com.nuc.unit.LoginRecord;

/**
 * @Author：朱瑞敏
 * @Description： TODO 企业controller层实现类
 **/
public class CompanyControlllerImpl implements CompanyController {

    /**
     * 生成企业
     * @return 生成的企业
     */
    @Override
    public Company createCompany() {
        CompanyService createCompanyService = new CompanyServiceImpl();
        Company company = createCompanyService.createCompany();
        return company;
    }

    /**
     * 企业登陆
     * @return 企业的登陆信息
     */
    @Override
    public LoginRecord loginCompany() {
        CompanyService companyService = new CompanyServiceImpl();
        LoginRecord loginRecord = companyService.loginCompany();
        return loginRecord;
    }

    /**
     * 通过企业id查找企业
     * @param companyId 企业id
     * @return 查找到的企业
     */
    @Override
    public Company selectCompanyByCompanyId(String companyId) {
        CompanyService companyService = new CompanyServiceImpl();
        Company company = companyService.selectCompanyByCompanyId(companyId);
        return company;
    }

    /**
     * 退出企业登陆
     * @return 退出登陆记录
     */
    @Override
    public LoginRecord outCompany() {
        CompanyService companyService = new CompanyServiceImpl();
        LoginRecord loginRecord = companyService.outCompany();
        return loginRecord;
    }
}
