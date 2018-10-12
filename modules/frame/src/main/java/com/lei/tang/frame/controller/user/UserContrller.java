package com.lei.tang.frame.controller.user;

import com.lei.tang.frame.service.user.UserAccountService;
import domain.CommonResponse;
import domain.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanglei
 * @date 18/10/12
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserContrller {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/login")
    public CommonResponse login(HttpServletRequest request,String userAccount, String userPassword){
        return new CommonResponse(userAccountService.login(request,userAccount, userPassword));
    }

    /**
     * 注销
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public CommonResponse logout() {
        userAccountService.logout();
        return new CommonResponse(ResponseCode.EXPIRED_SESSION, "注销成功!");
    }

    @PostMapping("/addUser")
    public CommonResponse addUser(String userAccount, String userPassword){
        return new CommonResponse(userAccountService.addUser(userAccount, userPassword));
    }

}
