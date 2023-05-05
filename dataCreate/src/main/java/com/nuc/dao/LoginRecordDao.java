package com.nuc.dao;

import com.nuc.unit.LoginRecord;

import java.util.List;

/**
 * @Author：朱瑞敏
 * @Description： TODO 登陆退出记录dao层
 **/
public interface LoginRecordDao {

    /**
     * 向数据库中插入登陆记录
     * @param loginRecord 要插入的登陆记录
     * @return 插入成功为true，失败为false
     */
    public boolean insertLoginRecord(LoginRecord loginRecord);

    /**
     * 查询登陆记录中登陆的用户
     * @return 查询到的记录
     */
    public List<LoginRecord> selectLoginUserRecord();

    /**
     * 用户退出登陆，通过记录编号和时间对记录进行更改
     * @param loginRecordId 记录的编号
     * @param time 时间
     * @return 修改结果
     */
    public boolean updateUserLoginRecordByLoginRecordIdAndOutTime(String loginRecordId, String time);

    /**
     * 通过记录id查询
     * @param recordId 要查询的id
     * @return 查询到的结果
     */
    public LoginRecord selectLoginRecordByRecordId(String recordId);


    public List<LoginRecord> selectLoginCompanyRecord();

}
