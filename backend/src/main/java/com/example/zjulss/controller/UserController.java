package com.example.zjulss.controller;

import com.alibaba.fastjson.JSON;
import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.dao.UserInfoMapper;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.entity.UserPwd;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.UserInfoService;
import com.example.zjulss.service.UserPwdService;
import com.example.zjulss.utils.HostHolder;
import com.example.zjulss.utils.JwtUtil;
import com.example.zjulss.utils.MyStringUtils;
import com.example.zjulss.vo.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserPwdService userPwdService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HostHolder hostHolder;

    // 注册
    @PostMapping("/register")
    @ResponseBody
    public BaseResponse registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return BaseResponse.fail("参数校验失败");
        }
        Integer codeCache = (Integer) redisTemplate.opsForValue().get(user.getPhoneNumber());
        if (codeCache != null) {
            int codeSaved = codeCache;
            if (codeSaved != user.getCode()) {
                return BaseResponse.fail("验证码错误");
            }
        } else {
            return BaseResponse.fail("请先请求验证码");
        }
        // fetch code from redis
        // redisTemplate.delete(user.getPhoneNumber());
        // check whether exist this user(by phone)
        UserInfo userInfo = userInfoService.getUserInfoByPhone(user.getPhoneNumber());
        if (userInfo != null) {
            return BaseResponse.fail("重复创建");
        }
        // 创建对象
        userInfo = new UserInfo();
        // userInfo.setModify(LocalDateTime.now());
        userInfo.setUserName(user.getUserName());
        userInfo.setPhone(user.getPhoneNumber());
        userInfo.setRealName(user.getRealName());
        userInfo.setClazz(user.getClazz());
        userInfo.setSno(user.getSno());
        userInfo.setDormitory(user.getDormitory());
        userInfo.setGender(user.getGender());
        userInfo.setCreateTime(LocalDateTime.now());
        ;
        userInfo.setAvatar(user.getAvatar());
        if (!userInfoService.saveUserInfo(userInfo)) {
            return BaseResponse.fail("创建失败，服务器错误");
        }
        // pwd
        UserPwd userPwd = new UserPwd();
        userPwd.setPassword(user.getPassword());
        userPwd.setUid(userInfo.getId());
        if (!userPwdService.saveUserPwd(userPwd)) {
            return BaseResponse.fail("创建失败，服务器错误");
        }
        return BaseResponse.success("创建成功");
    }

    // 更新密码
    @PostMapping("/changePassword")
    @ResponseBody
    @LoginRequired
    public BaseResponse changePassword(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        String phoneNumber = userInfo.getPhone();
        String newPassword = map.get("newPassword");
        String codee = map.get("code");
        if (!MyStringUtils.checkIsValid(phoneNumber, newPassword, codee)) {
            return BaseResponse.fail(HttpStatus.BAD_REQUEST.value(), "invalild post body");
        }
        int code = Integer.parseInt(codee);
        Integer codeCache = (Integer) redisTemplate.opsForValue().get(phoneNumber);
        if (codeCache != null) {
            int codeSaved = codeCache;
            if (codeSaved != code) {
                return BaseResponse.fail("验证码错误");
            }
        } else {
            return BaseResponse.fail("请先请求验证码");
        }
        // 获取uid
        UserPwd userPwd = userPwdService.getUserPwdByUid(userInfo.getId());
        if (userPwd == null) {
            return BaseResponse.fail("服务器错误");
        }
        userPwd.setPassword(newPassword);
        if (!userPwdService.updateUserPwd(userPwd)) {
            return BaseResponse.fail("更新失败，服务器错误");
        }
        return BaseResponse.success("更新成功");
    }

    @PostMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestBody Map<String, String> inputMap) throws IOException {
        String phoneNumber = inputMap.get("phoneNumber");
        String password = inputMap.get("password");
        if (!MyStringUtils.checkIsValid(phoneNumber, password)) {
            return BaseResponse.fail("参数缺失");
        }
        if (!userPwdService.login(phoneNumber, password)) {
            return BaseResponse.fail("登录失败");
        }
        UserInfo userinfo = userInfoService.getUserInfoByPhone(phoneNumber);
        String token = JwtUtil.createJWT(UUID.randomUUID().toString(), userinfo.getId(), (long) (48 * 60 * 60 * 1000));
        return BaseResponse.success(token);
    }

    @GetMapping("/userinfo/")
    @ResponseBody
    @LoginRequired
    public BaseResponse getUserInfo() throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        return BaseResponse.success(hostHolder.getUser().toString());
    }

    @PostMapping("/updaterealname")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateRealName(@RequestBody Map<String, String> inputMap,
            HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        if (!inputMap.containsKey("realName")) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "invalid req body");
        }
        UserInfo userInfo = hostHolder.getUser();
        userInfo.setRealName(inputMap.get("realName"));
        if (!userInfoService.updateRealName(userInfo)) {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update fail");
        }
        return BaseResponse.success("success");
    }

    @PostMapping("/updateclazz")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateClazz(@RequestBody Map<String, String> inputMap,
            HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        if (!inputMap.containsKey("clazz")) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "invalid req body");
        }
        UserInfo userInfo = hostHolder.getUser();
        userInfo.setClazz(inputMap.get("clazz"));
        if (!userInfoService.updateClazz(userInfo)) {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update fail");
        }
        return BaseResponse.success("success");
    }

    @PostMapping("/updatesno")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateSno(@RequestBody Map<String, String> inputMap,
            HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        if (!inputMap.containsKey("sno")) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "invalid req body");
        }
        UserInfo userInfo = hostHolder.getUser();
        userInfo.setSno(inputMap.get("sno"));
        if (!userInfoService.updateSno(userInfo)) {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update fail");
        }
        return BaseResponse.success("success");
    }

    @PostMapping("/updatedormitory")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateDormitory(@RequestBody Map<String, String> inputMap,
            HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        if (!inputMap.containsKey("dormitory")) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "invalid req body");
        }
        UserInfo userInfo = hostHolder.getUser();
        userInfo.setDormitory(inputMap.get("dormitory"));
        if (!userInfoService.updateDormitory(userInfo)) {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update fail");
        }
        return BaseResponse.success("success");
    }

    @PostMapping("/updategender")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateGender(@RequestBody Map<String, String> inputMap,
            HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        if (!inputMap.containsKey("gender")) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "invalid req body");
        }
        UserInfo userInfo = hostHolder.getUser();
        userInfo.setGender(inputMap.get("gender"));
        if (!userInfoService.updateGender(userInfo)) {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update fail");
        }
        return BaseResponse.success("success");
    }

    @PostMapping("/updateavatar")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateAvatar(@RequestBody Map<String, String> inputMap,
            HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            return BaseResponse.fail("请先登录");
        }
        if (!inputMap.containsKey("avatar")) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "invalid req body");
        }
        UserInfo userInfo = hostHolder.getUser();
        userInfo.setAvatar(inputMap.get("avatar"));
        if (!userInfoService.updateAvatar(userInfo)) {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update fail");
        }
        return BaseResponse.success("success");
    }

}
