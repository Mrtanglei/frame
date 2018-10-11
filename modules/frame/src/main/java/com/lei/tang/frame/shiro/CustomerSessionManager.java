package com.lei.tang.frame.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import utils.AESUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author tanglei
 * @date 18/10/11
 */
public class CustomerSessionManager extends DefaultWebSessionManager {

    private static final String SESSION_ID_SOURCE = "Stateless request";

    public static final String COOKIE_NAME = "AccessToken";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        List<String> result = new ArrayList<>();
        if(httpServletRequest.getCookies() != null){
            Stream.of(httpServletRequest.getCookies()).filter((cookie -> COOKIE_NAME.equalsIgnoreCase(cookie.getName()))).findFirst().ifPresent((cookie -> {
                String accessToken = cookie.getValue();
                if (!StringUtils.isEmpty(accessToken)) {
                    String sessionId = AESUtil.decrypt(accessToken);
                    result.add(sessionId);
                }
            }));
        }
        if (result.size() > 0) {
            String sessionId = result.get(0);
            //如果Cookie中有 accessToken 则其值为加密后的sessionId，设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, SESSION_ID_SOURCE);//来源
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
        return super.getSessionId(request, response);
    }
}
