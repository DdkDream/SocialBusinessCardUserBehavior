package com.nuc.dao;

/**
 * @Author：朱瑞敏
 * @Description： TODO 账号iddao层
 **/
public interface IdDao {

    /**
     * 生成用户id
     * @return 生成的用户id
     */
    public String createUserId();


    /**
     * 生成企业id
     * @return 生成的企业id
     */
    public String createCompantId();

    /**
     * 生成登陆退出记录id
     * @return 生成的id
     */
    public String createLoginrecordId();
}
