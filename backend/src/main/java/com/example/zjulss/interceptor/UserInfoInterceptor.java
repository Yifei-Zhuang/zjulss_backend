package com.example.zjulss.interceptor;


import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.service.UserInfoService;
import com.example.zjulss.utils.HostHolder;
import com.example.zjulss.utils.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HostHolder hostHolder;

    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //     // 从header中获取凭证
    //     String token = request.getHeader("Authorization");
    //     System.out.println(token);
    //     if (token != null) {
    //         // 查询凭证
    //         try {
    //             int userId = JwtUtil.validateJWT(token);
    //             // 检查凭证是否有效
    //             // 根据凭证查询用户
    //             UserInfo userinfo = userInfoService.getUserInfoById(userId);
    //             // 在本次请求中持有用户
    //             hostHolder.setUser(userinfo);
    //             System.out.println(userinfo); // test
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             return true;
    //         }
    //     }
    //     return true;
    // }

    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    //                        @Nullable ModelAndView modelAndView) throws Exception {
    //     hostHolder.clear();
    // }
}
