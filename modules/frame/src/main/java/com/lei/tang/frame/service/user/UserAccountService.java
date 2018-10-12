package com.lei.tang.frame.service.user;

import domain.CommonResponse;
import domain.user.UserAccountBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanglei
 * @date 18/10/12
 */
public interface UserAccountService {

    /**
     * 登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    UserAccountBean login(HttpServletRequest request, String userAccount, String userPassword);

    /**
     * 注销登录
     */
    void logout();

    /**
     * 添加账号
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    CommonResponse addUser(String userAccount, String userPassword);
}
