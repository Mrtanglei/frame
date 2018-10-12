package com.lei.tang.frame.service.user;

import entity.user.LoginRecord;
import entity.user.UserAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanglei
 * @date 18/10/11
 */
public interface LoginRecordService {

    /**
     * 保存登录记录
     * @param userAccount
     * @return
     */
    LoginRecord save(HttpServletRequest request, UserAccount userAccount);
}
