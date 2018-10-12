package com.lei.tang.frame.shiro;

import domain.CommonResponse;
import domain.ResponseCode;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import utils.JSONUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author tanglei
 * @date 18/10/11
 */
@Slf4j
public class CustomerFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 未登陆或会话超时不跳转login页面，返回错误信息交给前端
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        @Cleanup PrintWriter out = response.getWriter();

        out.print(JSONUtil.toJSONString(new CommonResponse(ResponseCode.EXPIRED_SESSION, "用户未登录或会话超时,请重新登录!")));
        return false;
    }

    /**
     * 跨域支持
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = WebUtils.toHttp(servletRequest);
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        //允许向该服务器提交请求的URI
        response.addHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        //允许提交请求的方法，*表示全部允许
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        //允许访问的头信息
        response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //允许Cookie跨域，在做登录校验的时候有用
        response.addHeader("Access-Control-Allow-Credentials", "true");

        return super.preHandle(request, response);
    }
}
