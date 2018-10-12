package com.lei.tang.frame.shiro;

import entity.user.UserAccount;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import repository.user.UserAccountRepository;

/**
 * @author tanglei
 * @date 18/10/11
 */
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserAccountRepository userAccountRepository;

    //用户授权，登录成功后回调
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从PrincipalCollection 中来获取登录用户的信息
        UserAccount account = (UserAccount) principalCollection.getPrimaryPrincipal();

        //TODO 利用登录的用户的信息来获取当前用户的角色
//        Set<String> roles = new HashSet<>();
//        roles.add("admin");
//        Set<String> permissions = new HashSet<>();
//        permissions.add("admin");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
//        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    //用户验证账号密码
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();
        Assert.hasText(account, "账号不能为空");
        UserAccount userAccount = userAccountRepository.findUserAccountByAccount(account);
        if(userAccount == null){
            throw new UnknownAccountException("账号不正确");
        }
        //principal: 认证的实体信息.
        Object principal = userAccount;
        //credentials: 密码.
        String credentials = userAccount.getPassword();
        // credentialsSalt 盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(userAccount.getPasswordSalty());
        // realmName: 当前 realm 对象的 name.
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt,
                realmName);
        return info;
    }
}
