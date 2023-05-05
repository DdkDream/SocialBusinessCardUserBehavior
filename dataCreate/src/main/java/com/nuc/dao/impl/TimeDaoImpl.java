package com.nuc.dao.impl;

import com.nuc.dao.TimeDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author：朱瑞敏
 * @Description： TODO 时间dao层
 **/
public class TimeDaoImpl implements TimeDao {

    /**
     * 得到现在时间
     * @return 返回现在的时间
     */
    @Override
    public String getNowTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
        String time = format.format(date);
        return time;
    }
}
