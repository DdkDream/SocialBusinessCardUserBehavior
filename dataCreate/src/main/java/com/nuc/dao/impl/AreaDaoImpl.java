package com.nuc.dao.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nuc.dao.AreaDao;
import com.google.gson.JsonParser;
import com.nuc.unit.City;
import com.nuc.unit.County;
import com.nuc.unit.Province;
import com.sun.org.apache.regexp.internal.REUtil;

import javax.swing.plaf.nimbus.State;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


/**
 * @Author：朱瑞敏
 * @Description： TODO 区域dao层impl
 **/
public class AreaDaoImpl implements AreaDao {

    ResourceBundle jdbc = ResourceBundle.getBundle("JDBC");
    String driver = jdbc.getString("driver");
    String url = jdbc.getString("url");
    String user = jdbc.getString("user");
    String password = jdbc.getString("password");

    /**
     * 从JSON导出区域信息到数据库
     */
    @Override
    public void importArea() {
        String path = "data/RegionalInformation.json";
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(path));
            JsonArray districtsAll = jsonObject.getAsJsonArray("districts");
            JsonObject countryJsonObject = districtsAll.get(0).getAsJsonObject();
            JsonArray provinceJsonArray = countryJsonObject.getAsJsonArray("districts");
            for(int i = 0; i < provinceJsonArray.size(); i++){
                JsonObject provinceJsonObject = provinceJsonArray.get(i).getAsJsonObject();
                String codeProvince = provinceJsonObject.get("code").toString();
                codeProvince = codeProvince.substring(1, codeProvince.length() - 1);
                String nameProvince = provinceJsonObject.get("name").toString();
                nameProvince = nameProvince.substring(1, nameProvince.length() - 1);
                int levelProvince = Integer.parseInt(provinceJsonObject.get("level").toString());
                Province province = new Province(codeProvince, nameProvince, levelProvince);
                int resultProvince = addProvince(province);
                if(resultProvince == -1){
                    break;
                }
                JsonArray cityJsonArray = provinceJsonObject.getAsJsonArray("districts");
                for(int j = 0; j < cityJsonArray.size(); j++){
                    JsonObject cityJsonObject = cityJsonArray.get(j).getAsJsonObject();
                    String codeCity = cityJsonObject.get("code").toString();
                    codeCity = codeCity.substring(1, codeCity.length() - 1);
                    String nameCity = cityJsonObject.get("name").toString();
                    nameCity = nameCity.substring(1, nameCity.length() - 1);
                    int levelCity = Integer.parseInt(cityJsonObject.get("level").toString());
                    City city = new City(codeProvince, codeCity, nameCity, levelCity);
                    int resultCity = addCity(city);
                    if(resultCity == -1){
                        break;
                    }
                    JsonArray countyJsonArray = cityJsonObject.getAsJsonArray("districts");
                    for(int k = 0; k < countyJsonArray.size(); k++){
                        JsonObject countyJsonObject = countyJsonArray.get(k).getAsJsonObject();
                        String codeCounty = countyJsonObject.get("code").toString();
                        codeCounty = codeCounty.substring(1, codeCounty.length() - 1);
                        String nameCounty = countyJsonObject.get("name").toString();
                        nameCounty = nameCounty.substring(1, nameCounty.length() - 1);
                        int levelCounty = Integer.parseInt(countyJsonObject.get("level").toString());
                        County county = new County(codeCity, codeCounty, nameCounty, levelCounty);
                        int resultCounty = addCounty(county);
                        if(resultCounty == -1){
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 向数据库添加省级行政区
     * @param province 要添加的省级行政区
     * @return 添加结果  -1失败
     */
    @Override
    public int addProvince(Province province) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "replace into chn_province values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, province.getProvince_id());
            preparedStatement.setString(2, province.getProvince_name());
            preparedStatement.setInt(3, province.getProvince_level());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 向数据库添加地级市行政区
     * @param city 要添加的地级市行政区
     * @return 添加结果 -1失败
     */
    @Override
    public int addCity(City city) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "replace into chn_city values(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, city.getProvince_id());
            preparedStatement.setString(2, city.getCity_id());
            preparedStatement.setString(3, city.getCity_name());
            preparedStatement.setInt(4, city.getCity_level());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 向数据库添加县级行政区
     * @param county 要添加的县级行政区
     * @return 添加结果 -1失败
     */
    @Override
    public int addCounty(County county) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "replace into chn_county values(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, county.getCity_id());
            preparedStatement.setString(2, county.getCounty_id());
            preparedStatement.setString(3, county.getCounty_name());
            preparedStatement.setInt(4, county.getCounty_level());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 随机获得省级行政区
     * @return 随机获得的省级行政区
     */
    @Override
    public Province getProvinceRandom() {
        List<Province> provinceList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from chn_province";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Province province = new Province();
                String provinceId = resultSet.getString(1);
                String provinceName = resultSet.getString(2);
                int provinceLevel = resultSet.getInt(3);
                province.setProvince_id(provinceId);
                province.setProvince_name(provinceName);
                province.setProvince_level(provinceLevel);
                provinceList.add(province);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        int index = random.nextInt(provinceList.size());
        return provinceList.get(index);
    }

    /**
     * 根据省级行政区随机获得地级行政区
     * @param province
     * @return 随机获得的地级行政区
     */
    @Override
    public City getCityByProvinceRandom(Province province) {
        List<City> cityList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from chn_city where province_id = " + province.getProvince_id();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                City city = new City();
                city.setProvince_id(resultSet.getString(1));
                city.setCity_id(resultSet.getString(2));
                city.setCity_name(resultSet.getString(3));
                city.setCity_level(resultSet.getInt(4));
                cityList.add(city);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        int index = random.nextInt(cityList.size());
        return cityList.get(index);
    }

    @Override
    public County getCountyByCityRandom(City city) {
        List<County> countyList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from chn_county where city_id = " + city.getCity_id();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                County county = new County();
                county.setCity_id(resultSet.getString(1));
                county.setCounty_id(resultSet.getString(2));
                county.setCounty_name(resultSet.getString(3));
                county.setCounty_level(resultSet.getInt(4));
                countyList.add(county);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        int index = random.nextInt(countyList.size());
        return countyList.get(index);
    }

    @Override
    public Province getProvinceByProvinceId(String provinceId) {
        Province province = new Province();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from chn_province where province_id = '" + provinceId + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                province.setProvince_id(resultSet.getString(1));
                province.setProvince_name(resultSet.getString(2));
                province.setProvince_level(resultSet.getInt(3));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return province;
    }

    @Override
    public City getCityByCityId(String cityId) {
        City city = new City();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from chn_city where city_id = '" + cityId + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                city.setProvince_id(resultSet.getString(1));
                city.setCity_id(resultSet.getString(2));
                city.setCity_name(resultSet.getString(3));
                city.setCity_level(resultSet.getInt(4));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return city;
    }

    @Override
    public County getCountyByCountyId(String countyId) {
        County county = new County();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from chn_county where county_id = '" + countyId + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                county.setCity_id(resultSet.getString(1));
                county.setCounty_id(resultSet.getString(2));
                county.setCounty_name(resultSet.getString(3));
                county.setCounty_level(resultSet.getInt(4));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return county;
    }


}
