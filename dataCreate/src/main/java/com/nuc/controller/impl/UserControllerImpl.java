package com.nuc.controller.impl;

import com.nuc.controller.UserController;
import com.nuc.dao.LoginRecordDao;
import com.nuc.dao.UserDao;
import com.nuc.service.UserService;
import com.nuc.service.impl.UserServiceImpl;
import com.nuc.unit.LoginRecord;
import com.nuc.unit.User;

/**
 * @Author：朱瑞敏
 * @Description： TODO 生成用户
 **/
public class UserControllerImpl implements UserController {

    /**
     * 随机生成用户
     * @return
     */
    @Override
    public User createUser() {

        UserService createUserService = new UserServiceImpl();
        User user = new User();
        user = createUserService.createUser();
        return user;
    }

    /**
     * 随机选择一个用户登陆
     * @return 返回登陆的用户记录
     */
    @Override
    public LoginRecord loginUser() {
        UserService userService = new UserServiceImpl();
        LoginRecord loginRecord = userService.loginUser();
        return loginRecord;
    }

    /**
     * 根据用户id查询用户
     * @param userId 需要查询的用户id
     * @return 查询到的用户
     */
    @Override
    public User selectUserByUserId(String userId) {
        UserService userService = new UserServiceImpl();
        User user = userService.selectUserByUserId(userId);
        return user;
    }

    /**
     * 登陆的用户退出登陆
     * @return 退出登陆记录
     */
    @Override
    public LoginRecord outUser() {
        UserService userService = new UserServiceImpl();
        LoginRecord loginRecord = userService.outUser();
        return loginRecord;
    }


}
