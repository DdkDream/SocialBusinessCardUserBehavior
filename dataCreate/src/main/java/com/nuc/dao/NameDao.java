package com.nuc.dao;

/**
 * @Author：朱瑞敏
 * @Description： TODO 名字
 **/
public interface NameDao {

    /**
     * 根据性别随机生成用户名字
     * @param sex 性别
     * @return 生成的名字
     */
    public String createUserName(String sex);


    /**
     * 随机生成企业名称
     * @return 生成的企业名称
     */
    public String createCompanyName();


    /**
     * 判断数据库中是否已经存在这家公司
     * @param companyName
     * @return
     */
    public boolean selectCompanyName(String companyName);
}
