package com.nuc.dao;

import com.nuc.unit.Company;

import java.util.List;

/**
 * @Author：朱瑞敏
 * @Description： TODO 企业dao层接口
 **/
public interface CompanyDao {

    public int insertCompany(Company company);

    /**
     * 查找所有已经注册的企业
     * @return 已经注册的企业
     */
    public List<Company> selectAllCompany();

    /**
     * 通过企业id查找企业
     * @param companyId 企业的id
     * @return 查找到的企业
     */
    public Company selectCompanyByCompanyId(String companyId);
}
