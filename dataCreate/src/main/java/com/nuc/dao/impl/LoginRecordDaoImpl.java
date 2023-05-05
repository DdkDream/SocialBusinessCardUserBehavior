package com.nuc.dao.impl;

import com.nuc.dao.AreaDao;
import com.nuc.dao.CompanyDao;
import com.nuc.dao.LoginRecordDao;
import com.nuc.unit.LoginRecord;
import com.nuc.unit.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author：朱瑞敏
 * @Description： TODO 登陆退出dao层实体类
 **/
public class LoginRecordDaoImpl implements LoginRecordDao {

    ResourceBundle jdbc = ResourceBundle.getBundle("JDBC");
    String driver = jdbc.getString("driver");
    String url = jdbc.getString("url");
    String username = jdbc.getString("user");
    String password = jdbc.getString("password");


    @Override
    public boolean insertLoginRecord(LoginRecord loginRecord) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "insert into login_record values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, loginRecord.getRecordId());
            preparedStatement.setInt(2, loginRecord.getType());
            preparedStatement.setString(3, loginRecord.getId());
            preparedStatement.setString(4, loginRecord.getProvince().getProvince_id());
            preparedStatement.setString(5, loginRecord.getCity().getCity_id());
            preparedStatement.setString(6, loginRecord.getCounty().getCounty_id());
            preparedStatement.setString(7, loginRecord.getLoginTime());
            preparedStatement.setString(8, loginRecord.getOutTime());
            preparedStatement.setInt(9, loginRecord.getIfLogin());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<LoginRecord> selectLoginUserRecord() {
        List<LoginRecord> loginRecordList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from login_record where type = 1 and if_login = 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                LoginRecord loginRecord = new LoginRecord();
                loginRecord.setRecordId(resultSet.getString(1));
                loginRecord.setType(resultSet.getInt(2));
                loginRecord.setId(resultSet.getString(3));
                AreaDao areaDao = new AreaDaoImpl();
                loginRecord.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(4)));
                loginRecord.setCity(areaDao.getCityByCityId(resultSet.getString(5)));
                loginRecord.setCounty(areaDao.getCountyByCountyId(resultSet.getString(6)));
                loginRecord.setLoginTime(resultSet.getString(7));
                loginRecord.setOutTime(resultSet.getString(8));
                loginRecord.setIfLogin(resultSet.getInt(9));
                loginRecordList.add(loginRecord);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginRecordList;
    }

    @Override
    public boolean updateUserLoginRecordByLoginRecordIdAndOutTime(String loginRecordId, String time) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "update login_record set out_time = ?, if_login = 0 where record_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, time);
            preparedStatement.setString(2, loginRecordId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 通过记录id查询记录
     * @param recordId 要查询的id
     * @return 查询到的结果
     */
    @Override
    public LoginRecord selectLoginRecordByRecordId(String recordId) {
        LoginRecord loginRecord = new LoginRecord();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from login_record where record_id = '" + recordId + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                loginRecord.setRecordId(resultSet.getString(1));
                loginRecord.setType(resultSet.getInt(2));
                loginRecord.setId(resultSet.getString(3));
                AreaDao areaDao = new AreaDaoImpl();
                loginRecord.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(4)));
                loginRecord.setCity(areaDao.getCityByCityId(resultSet.getString(5)));
                loginRecord.setCounty(areaDao.getCountyByCountyId(resultSet.getString(6)));
                loginRecord.setLoginTime(resultSet.getString(7));
                loginRecord.setOutTime(resultSet.getString(8));
                loginRecord.setIfLogin(resultSet.getInt(9));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginRecord;
    }

    @Override
    public List<LoginRecord> selectLoginCompanyRecord() {
        List<LoginRecord> loginRecordList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from login_record where type = 2 and if_login = 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                LoginRecord loginRecord = new LoginRecord();
                loginRecord.setRecordId(resultSet.getString(1));
                loginRecord.setType(resultSet.getInt(2));
                loginRecord.setId(resultSet.getString(3));
                AreaDao areaDao = new AreaDaoImpl();
                loginRecord.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(4)));
                loginRecord.setCity(areaDao.getCityByCityId(resultSet.getString(5)));
                loginRecord.setCounty(areaDao.getCountyByCountyId(resultSet.getString(6)));
                loginRecord.setLoginTime(resultSet.getString(7));
                loginRecord.setOutTime(resultSet.getString(8));
                loginRecord.setIfLogin(resultSet.getInt(9));
                loginRecordList.add(loginRecord);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginRecordList;
    }
}
