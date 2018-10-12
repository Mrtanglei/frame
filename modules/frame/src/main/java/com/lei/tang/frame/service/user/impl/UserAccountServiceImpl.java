package com.lei.tang.frame.service.user.impl;

import com.lei.tang.frame.service.user.LoginRecordService;
import com.lei.tang.frame.service.user.UserAccountService;
import domain.CommonResponse;
import domain.user.UserAccountBean;
import entity.user.UserAccount;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repository.user.UserAccountRepository;
import utils.AESUtil;
import utils.PasswordUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanglei
 * @date 18/10/12
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LoginRecordService loginRecordService;

    @Override
    public UserAccountBean login(HttpServletRequest request, String userAccount, String userPassword) {
        Subject subject = SecurityUtils.getSubject();
        //没有验证
        if (!subject.isAuthenticated()) {
            Assert.hasText(userAccount, "登录账号不能为空");
            Assert.hasText(userPassword, "登录密码不能为空");
            UsernamePasswordToken token = new UsernamePasswordToken(userAccount, userPassword);
            //登录，抛出的异常在全局异常中处理
            subject.login(token);
        }
        loginRecordService.save(request,(UserAccount) subject.getPrincipal());
        return buildUserAccountBean(subject);
    }

    private UserAccountBean buildUserAccountBean(Subject subject) {
        Assert.notNull(subject, "内部错误");
        UserAccountBean bean = new UserAccountBean();
        UserAccount account = (UserAccount) subject.getPrincipal();
        bean.setAccount(account.getAccount());
        bean.setToken(AESUtil.encrypt(subject.getSession().getId().toString()));
        return bean;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
    }

    @Override
    public CommonResponse addUser(String userAccount, String userPassword) {
        Assert.hasText(userAccount, "账号不能为空");
        Assert.hasText(userPassword, "密码不能为空");
        Assert.isNull(userAccountRepository.findUserAccountByAccount(userAccount), "账号已存在");
        UserAccount account = new UserAccount();
        account.setAccount(userAccount);
        account.setIsAdmin(true);
        account.setIsSystem(true);
        String salty = PasswordUtil.generateSalty(userPassword);
        account.setPasswordSalty(salty);
        account.setPassword(PasswordUtil.encryptPassword(userPassword, salty));
        userAccountRepository.save(account);
        return new CommonResponse(true);
    }
}
