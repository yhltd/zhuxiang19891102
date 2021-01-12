package com.zx.pro.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Session工具类
 *
 * @author dai
 */
@Component
public class SessionUtil {

    //session过期时间（秒）
    private static final Integer MAX_INTERVAL = 7200;

    /**
     * 检查token
     * @return 是否过期
     */
    public static boolean checkToken(HttpSession session){
        Object data = session.getAttribute("token");
        return StringUtils.isNotNull(data);
    }

    /** set*/
    public static void setToken(HttpSession session,String data){
        //设置session过期时间
        session.setMaxInactiveInterval(MAX_INTERVAL);
        session.setAttribute("token",data);
    }

    /** get*/
    public static String getToken(HttpSession session){
        return checkToken(session) ? session.getAttribute("token").toString() : StringUtils.EMPTY;
    }

    public static void remove(HttpSession session,String key){
        session.removeAttribute(key);
    }
}
