package com.nuc.dao.impl;

import com.nuc.dao.EmailDao;

import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @Author：朱瑞敏
 * @Description： TODO 邮箱Dao层的实现类
 **/
public class EmailDaoImpl implements EmailDao {

    private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";


    /**
     * 随机生成邮箱
     * @return 随机生成的邮箱
     */
    @Override
    public String createEmail() {
        Random random = new Random();
        int length = random.nextInt(5) + 3;
        boolean flag = false;
        String email = null;
        while(!flag){
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int number = (int) (Math.random() * base.length());
                sb.append(base.charAt(number));
            }
            sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
            email = sb.toString();
            if(!selectEmail(email)){
                flag = true;
            }
        }
        return email;

    }

    /**
     * 判断邮箱是否存在
     * @param email 要判断的邮箱
     * @return 存在为true，不存在为false
     */
    @Override
    public boolean selectEmail(String email) {
        ResourceBundle jdbc = ResourceBundle.getBundle("JDBC");
        String driver = jdbc.getString("driver");
        String url = jdbc.getString("url");
        String user = jdbc.getString("user");
        String password = jdbc.getString("password");
        int i = 0;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from company where email = '" + email + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                i++;
            }
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
