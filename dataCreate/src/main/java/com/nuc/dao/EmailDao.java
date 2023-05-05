package com.nuc.dao;

/**
 * @Author：朱瑞敏
 * @Description： TODO 邮箱Dao层
 **/
public interface EmailDao {




    /**
     * 随机生成邮箱
     * @return 随机生成的邮箱
     */
    public String createEmail();

    /**
     * 判断邮箱是否存在
     * @param email 要判断的邮箱
     * @return 存在为true，不存在为false
     */
    public boolean selectEmail(String email);

}
