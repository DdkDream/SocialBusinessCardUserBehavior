package com.nuc.service;

import com.nuc.unit.LoginRecord;
import com.nuc.unit.User;

/**
 * @Author：朱瑞敏
 * @Description： TODO 用户
 **/
public interface UserService {

    /**
     * 生成用户
     * @return 生成的用户
     */
    public User createUser();

    /**
     * 用户登陆
     * @return 进行登陆的用户记录
     */
    public LoginRecord loginUser();

    /**
     * 通过用户id查询用户
     * @param userId 需要查询的用户id
     * @return 查询到的用户
     */
    public User selectUserByUserId(String userId);


    /**
     * 用户退出登陆
     * @return 退出登陆的用户记录
     */
    public LoginRecord outUser();
}
