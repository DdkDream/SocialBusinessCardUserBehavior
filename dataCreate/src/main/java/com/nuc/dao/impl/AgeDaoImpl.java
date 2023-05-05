package com.nuc.dao.impl;

import com.nuc.dao.AgeDao;

import java.util.Random;

/**
 * @Author：朱瑞敏
 * @Description： TODO 年龄
 **/
public class AgeDaoImpl implements AgeDao {

    /**
     * 随机生成年龄
     * @return 年龄
     */
    @Override
    public int createAge() {
        int age = -1;
        Random random = new Random();
        age = random.nextInt(90) % (90 - 10 + 1) + 10;
        return age;
    }
}
