package com.nuc.dao;

/**
 * @Author：朱瑞敏
 * @Description： TODO 手机号dao
 **/
public interface PhoneDao {

    /**
     * 生成电话号
     * @return 随机生成的电话号
     */
    public String createPhone();

    /**
     * 判断电话号是否存在
     * @param phone 要判断的电话号
     * @return 存在为true，不存在为false
     */
    public boolean phoneWhetherExist(String phone);
}
