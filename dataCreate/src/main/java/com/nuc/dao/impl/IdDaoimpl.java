package com.nuc.dao.impl;

import com.nuc.dao.IdDao;

import java.text.SimpleDateFormat;

/**
 * @Author：朱瑞敏
 * @Description： TODO id的dao层实现类
 **/
public class IdDaoimpl implements IdDao {

    /**
     * 生成用户ID
     * @return 生成的用户id
     */
    @Override
    public String createUserId() {
        String userId = "u";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        userId += sdf.format(System.currentTimeMillis());
        return userId;
    }

    /**
     * 生成企业id
     * @return 生成的企业id
     */
    @Override
    public String createCompantId() {
        String companyId = "c";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        companyId += sdf.format(System.currentTimeMillis());
        return companyId;
    }

    @Override
    public String createLoginrecordId() {
        String loginRecordId = "lo";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        loginRecordId += sdf.format(System.currentTimeMillis());
        return loginRecordId;
    }
}
