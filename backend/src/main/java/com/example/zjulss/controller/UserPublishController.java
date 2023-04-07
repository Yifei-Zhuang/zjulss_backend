package com.example.zjulss.controller;

import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.entity.UserPublishGood;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.UserPublishGoodService;
import com.example.zjulss.utils.HostHolder;
import com.example.zjulss.vo.UserPublishGoodVO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * 个人出售相关
 * */

@Controller
@RequestMapping("/publish")
public class UserPublishController {
    @Autowired
    UserPublishGoodService userPublishGoodService;

    @Autowired
    HostHolder hostHolder;

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse addUserPublish(@Valid @RequestBody UserPublishGoodVO userPublishGoodVO, HttpServletResponse httpServletResponse) throws IOException {
        UserInfo user = hostHolder.getUser();
        if (user == null) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "please login");
            return BaseResponse.fail("fail");
        }
        if (userPublishGoodService.addPublish(userPublishGoodVO)) {
            return BaseResponse.success();
        } else {
            return BaseResponse.fail();
        }
    }

    @ResponseBody
    @PostMapping("/remove")
    public BaseResponse removeUserPublish(@RequestBody int id, HttpServletResponse httpServletResponse) throws IOException {
        UserInfo user = hostHolder.getUser();
        if (user == null) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "please login");
            return BaseResponse.fail("fail");
        }
        if (userPublishGoodService.removePublish(id)) {
            return BaseResponse.success();
        } else {
            return BaseResponse.fail();
        }
    }

    @ResponseBody
    @GetMapping("/list")
    public List<UserPublishGood> getUserPublishs(HttpServletResponse httpServletResponse) throws IOException {
        UserInfo user = hostHolder.getUser();
        if (user == null) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "please login");
            return new ArrayList<>();
        }
        return userPublishGoodService.getUserPublishs(user.getId());
    }
}
