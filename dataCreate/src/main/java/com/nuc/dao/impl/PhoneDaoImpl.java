package com.nuc.dao.impl;

import com.nuc.dao.PhoneDao;

import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @Author：朱瑞敏
 * @Description： TODO 随机生成电话号
 **/
public class PhoneDaoImpl implements PhoneDao {

    // 电话号码前缀
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    /**
     * 随机生成电话号
     * @return 生成的电话号
     */
    @Override
    public String createPhone() {
        boolean flag = true;
        String phone = "";
        while(flag){
            Random random = new Random();
            int index = random.nextInt(telFirst.length - 1);
            String first = telFirst[index];
            String second = String.valueOf((random.nextInt(10000) + 10000)).substring(1);
            String third = String.valueOf((random.nextInt(10000) + 10000)).substring(1);
            phone = first + second + third;
            if(!phoneWhetherExist(phone)){
                flag = false;
            }
        }
        return phone;
    }

    /**
     * 判断电话号是否存在
     * @param phone 要判断的电话号
     * @return 存在为true ，不存在为false
     */
    @Override
    public boolean phoneWhetherExist(String phone) {
        ResourceBundle jdbc = ResourceBundle.getBundle("JDBC");
        String driver = jdbc.getString("driver");
        String url = jdbc.getString("url");
        String user = jdbc.getString("user");
        String password = jdbc.getString("password");
        int i = 0;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from user where phone = " + phone;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                i++;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(i > 0){
            return true;
        }else{
            return false;
        }
    }


}
