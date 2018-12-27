package com.snailmann.seckill.web.resolver;

import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为了重写web层对参数的处理
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    @Autowired
    UserService userService;

    /**
     * 这个参数处理器是否支持User类型的处理
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    /**
     * 为了兼容多方式传递携带的token
     * 1. Cookie形式
     * 2. get请求参数形式
     * 3. header形式
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter("token");
        String headerToken = request.getHeader("token");
        String cookieToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookieToken = cookie.getValue();
                }
            }
        }


        //如果所有形式的传递都没有token，返回登录页面
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(headerToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        //按照优先级顺序，如果param空，则取header，如果header也为空，则取cookie
        String token = StringUtils.isEmpty(paramToken)
                ? StringUtils.isEmpty(headerToken) ? cookieToken : headerToken
                : paramToken;
        //根据请求中带来的token获取用户信息
        User user = userService.getByToken(response, token);

        return user;
    }
}
