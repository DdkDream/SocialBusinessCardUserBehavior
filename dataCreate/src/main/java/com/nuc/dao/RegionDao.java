package com.nuc.dao;

/**
 * @Author：朱瑞敏
 * @Description： TODO 区域
 **/
public interface RegionDao {

    /**
     * 通过API接口获取行政区划
     */
    public void getRegion();

    public String[] chooseRegion();
}
