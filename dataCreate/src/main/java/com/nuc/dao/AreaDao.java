package com.nuc.dao;

import com.nuc.unit.City;
import com.nuc.unit.County;
import com.nuc.unit.Province;

/**
 * @Author：朱瑞敏
 * @Description： TODO 区域dao层接口
 **/
public interface AreaDao {

    /**
     * 从JSON导入区域信息到数据库
     */
    public void importArea();


    /**
     * 向数据库添加省级行政区
     * @param province 要添加的省级行政区
     * @return 添加结果
     */
    public int addProvince(Province province);

    /**
     * 向数据库添加地级市行政区
     * @param city 要添加的地级市行政区
     * @return 添加结果
     */
    public int addCity(City city);

    /**
     * 向数据库添加县级行政区
     * @param county 要添加的县级行政区
     * @return 添加结果
     */
    public int addCounty(County county);


    /**
     * 随机获得省级行政区
     * @return 获得的省级行政区
     */
    public Province getProvinceRandom();

    /**
     * 根据省级行政区随机获得地级行政区
     * @param province
     * @return 随机获得的地级行政区
     */
    public City getCityByProvinceRandom(Province province);

    /**
     * 根据地级行政区随机获得县级行政区
     * @param city
     * @return 随机获得的县级行政区
     */
    public County getCountyByCityRandom(City city);

    /**
     * 根据省级行政区的id获得省级行政区
     * @param provinceId 省级行政区的id
     * @return 获得的省级行政区id
     */
    public Province getProvinceByProvinceId(String provinceId);

    /**
     * 根据地级市的id获得地级市行政区id
     * @param cityId 地级市行政区id
     * @return 获得的地级市行政区id
     */
    public City getCityByCityId(String cityId);


    /**
     * 根据县级市的id获得县级市行政区id
     * @param countyId 县级市行政区id
     * @return 获得的县级市行政区id
     */
    public County getCountyByCountyId(String countyId);
}
