package com.nuc.dao;

import com.nuc.unit.User;

import java.util.List;

/**
 * @Author：朱瑞敏
 * @Description： TODO User的dao层
 **/
public interface UserDao {

    /**
     * 向数据库中插入用户信息
     * @param user 需要插入的用户信息
     * @return 插入的结果
     */
    public int insertUser(User user);

    /**
     * 查询所有的用户
     * @return 查询到的用户集合
     */
    public List<User> selectAllUser();


    /**
     * 查询没有登陆的用户
     * @return 没有登陆的用户
     */
    public List<User> selectNoLoginUser();


    /**
     * 通过用户id查找用户
     * @param userId 需要查询用户的用户id
     * @return 查找到的用户
     */
    public User selectUserByUserId(String userId);


    /**
     * 查询已经登陆的用户
     * @return
     */
    public List<User> selectLoginUser();

}
