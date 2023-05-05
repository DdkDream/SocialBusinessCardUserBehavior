package com.nuc.dao.impl;

import com.nuc.dao.SexDao;

import java.util.Random;

/**
 * @Author：朱瑞敏
 * @Description： TODO 性别
 **/
public class SexDaoImpl implements SexDao {

    /**
     * 随机生成性别
     * @return 生成的性别
     */
    @Override
    public String createSex() {
        Random random = new Random();

        int index = random.nextInt(9);

        if(index % 2 == 0){
            return "女";
        }else{
            return "男";
        }
    }
}
