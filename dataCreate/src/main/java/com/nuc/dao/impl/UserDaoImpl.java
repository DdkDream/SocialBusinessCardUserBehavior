package com.nuc.dao.impl;

import com.nuc.dao.AreaDao;
import com.nuc.dao.CompanyDao;
import com.nuc.dao.UserDao;
import com.nuc.unit.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author：朱瑞敏
 * @Description： TODO 用户Dao层的实现类
 **/
public class UserDaoImpl implements UserDao {

    ResourceBundle jdbc = ResourceBundle.getBundle("JDBC");
    String driver = jdbc.getString("driver");
    String url = jdbc.getString("url");
    String username = jdbc.getString("user");
    String password = jdbc.getString("password");


    /**
     * 向数据库中插入用户
     * @param user 需要插入的用户信息
     * @return 插入的结果
     */
    @Override
    public int insertUser(User user) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "replace into user values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSex());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getProvince().getProvince_id());
            preparedStatement.setString(7, user.getCity().getCity_id());
            preparedStatement.setString(8, user.getCounty().getCounty_id());
            preparedStatement.setString(9, user.getCreateTime());
            preparedStatement.setString(10, user.getPhone());
            preparedStatement.setString(11, user.getCompany().getCompany_id());
            preparedStatement.setString(12, user.getPosition());
            preparedStatement.setString(13, user.getIntroduction());
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

    @Override
    public List<User> selectAllUser() {
        List<User> userList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from user";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getString(1));
                user.setSurname(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSex(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                AreaDao areaDao = new AreaDaoImpl();
                user.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(6)));
                user.setCity(areaDao.getCityByCityId(resultSet.getString(7)));
                user.setCounty(areaDao.getCountyByCountyId(resultSet.getString(8)));
                user.setCreateTime(resultSet.getString(9));
                CompanyDao companyDao = new CompanyDaoImpl();
                user.setCompany(companyDao.selectCompanyByCompanyId(resultSet.getString(10)));
                user.setPosition(resultSet.getString(11));
                user.setIntroduction(resultSet.getString(12));
                userList.add(user);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * 查询没有登陆的用户
     * @return 查询到的没有登陆的用户
     */
    @Override
    public List<User> selectNoLoginUser() {
        List<User> userList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from user where user_id not in (select id from login_record where type = 1 and if_login = 1)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getString(1));
                user.setSurname(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSex(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                AreaDao areaDao = new AreaDaoImpl();
                user.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(6)));
                user.setCity(areaDao.getCityByCityId(resultSet.getString(7)));
                user.setCounty(areaDao.getCountyByCountyId(resultSet.getString(8)));
                user.setCreateTime(resultSet.getString(9));
                CompanyDao companyDao = new CompanyDaoImpl();
                user.setCompany(companyDao.selectCompanyByCompanyId(resultSet.getString(10)));
                user.setPosition(resultSet.getString(11));
                user.setIntroduction(resultSet.getString(12));
                userList.add(user);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * 通过用户ID查询用户
     * @param userId 需要查询用户的用户id
     * @return 查询到的用户
     */
    @Override
    public User selectUserByUserId(String userId) {
        User user = new User();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from user where user_id = '" + userId + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                user.setUserId(resultSet.getString(1));
                user.setSurname(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSex(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                AreaDao areaDao = new AreaDaoImpl();
                user.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(6)));
                user.setCity(areaDao.getCityByCityId(resultSet.getString(7)));
                user.setCounty(areaDao.getCountyByCountyId(resultSet.getString(8)));
                user.setCreateTime(resultSet.getString(9));
                CompanyDao companyDao = new CompanyDaoImpl();
                user.setCompany(companyDao.selectCompanyByCompanyId(resultSet.getString(10)));
                user.setPosition(resultSet.getString(11));
                user.setIntroduction(resultSet.getString(12));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    /**
     * 查询已经登陆的用户
     * @return 查询到的已经登陆的用户
     */
    @Override
    public List<User> selectLoginUser() {
        List<User> userList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from user where user_id in (select id from login_record where type = 1 and if_login = 1)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getString(1));
                user.setSurname(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSex(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                AreaDao areaDao = new AreaDaoImpl();
                user.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(6)));
                user.setCity(areaDao.getCityByCityId(resultSet.getString(7)));
                user.setCounty(areaDao.getCountyByCountyId(resultSet.getString(8)));
                user.setCreateTime(resultSet.getString(9));
                CompanyDao companyDao = new CompanyDaoImpl();
                user.setCompany(companyDao.selectCompanyByCompanyId(resultSet.getString(10)));
                user.setPosition(resultSet.getString(11));
                user.setIntroduction(resultSet.getString(12));
                userList.add(user);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
