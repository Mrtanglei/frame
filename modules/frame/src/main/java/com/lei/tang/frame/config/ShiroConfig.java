package com.lei.tang.frame.config;

import com.lei.tang.frame.shiro.CustomerFormAuthenticationFilter;
import com.lei.tang.frame.shiro.CustomerRealm;
import com.lei.tang.frame.shiro.CustomerSessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import utils.PasswordUtil;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tanglei
 * @date 18/10/11
 * <p>
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    private static final String SHIRO_SESSION_REDIS_KEY_PREFIX = "SHIRO_SESSION_TOKEN";

    private static final String SHIRO_CACHE_REDIS_KEY_PREFIX = "SHIRO_CACHE";

    //会话有效期
    private static final int SHIRO_SESSION_EXPIRE = 30 * 60;//半小时

    //Shiro拦截器工厂类注入成功
    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String, Filter> filters = new HashMap<>();
        filters.put("PGOFilter", authFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //TODO 应该从数据库读取权限配置
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    @Bean
    public CustomerFormAuthenticationFilter authFilter() {
        return new CustomerFormAuthenticationFilter();
    }


    /**
     * 配置哪些页面需要受保护. 以及访问这些页面需要的权限. 界面跳转由前端控制，后台仅返回json数据
     * 1). anon 可以被匿名访问 2). authc 必须认证(即登录)后才可能访问的页面.
     * 3). logout 登出. 4). roles 角色过滤器
     * <p>
     * 配置顺序问题：前面的优先匹配,会覆盖后面的,一般将 /**放在最为下边
     *
     * @return
     */
    public LinkedHashMap<String, String> filterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/login", "anon");
        map.put("/logout", "authc");
        //TODO 添加账号暂时允许匿名
        map.put("/user/addUser", "authc,roles[admin]");
        map.put("/api/**", "PGOFilter");
        //剩下的需要认证才能访问
        map.put("/api", "authc");
        return map;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    //用于认证和（或）授权的realm
    @Bean
    public AuthorizingRealm authorizingRealm() {
        AuthorizingRealm authorizingRealm = new CustomerRealm();
        //设置一个匹配器
        authorizingRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authorizingRealm;
    }


    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.ALGORITHMNAME);//密码比对使用MD5加密算法
        hashedCredentialsMatcher.setHashIterations(PasswordUtil.HASHITERATIONS);//加密迭代的次数
        return hashedCredentialsMatcher;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        CustomerSessionManager sessionManager = new CustomerSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }


    /**
     * 会话DAO，sessionManager里面的session需要保存在会话Dao里，没有会话Dao，session是瞬时的，没法从
     * sessionManager里面拿到session
     * <p>
     * 这里通过redis实现 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setKeyPrefix(SHIRO_SESSION_REDIS_KEY_PREFIX);
        redisSessionDAO.setExpire(SHIRO_SESSION_EXPIRE);//设置有效时间，单位：秒
        return redisSessionDAO;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    @ConfigurationProperties(prefix = "spring.redis")
    @Bean
    public RedisManager redisManager() {
        return new RedisManager();
    }

    /**
     * 配置cacheManager缓存
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(SHIRO_CACHE_REDIS_KEY_PREFIX);
        return redisCacheManager;
    }

    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor
     * 来拦截用以下注解的方法。
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
