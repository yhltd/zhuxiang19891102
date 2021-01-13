
package com.zx.pro.interceptor;

import com.zx.pro.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 拦截器
 *
 * @author dai
 */
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    /**
     * 在请求之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StringBuffer requestUrl = request.getRequestURL();
        log.info("本次请求地址：{}",requestUrl);
        try {
            //获取session对象
            HttpSession session = request.getSession();
            //判断session中有没有登录信息
            if (!SessionUtil.checkToken(session)) {
                //跳转到登陆页
                response.sendRedirect(request.getContextPath() + "/");
                log.info("请求被拦截");
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        log.info("请求成功");
        return true;
    }

    /**
     * 在请求之后执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 在整个请求结束时执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}