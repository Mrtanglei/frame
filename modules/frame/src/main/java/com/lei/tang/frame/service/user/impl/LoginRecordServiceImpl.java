package com.lei.tang.frame.service.user.impl;

import com.lei.tang.frame.service.user.LoginRecordService;
import entity.user.LoginRecord;
import entity.user.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.user.LoginRecordRepository;
import utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author tanglei
 * @date 18/10/11
 */
@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Autowired
    private LoginRecordRepository loginRecordRepository;

    @Transactional
    @Override
    public LoginRecord save(HttpServletRequest request, UserAccount userAccount) {
        if(userAccount != null){
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setLoginTime(new Date());
            loginRecord.setUserAccount(userAccount);
            loginRecord.setIp(IPUtils.getIp(request));
            loginRecordRepository.save(loginRecord);
            return loginRecord;
        }
        return null;
    }
}
