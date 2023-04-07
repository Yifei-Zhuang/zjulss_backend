package com.example.zjulss.controller;

import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.PhoneCodeService;
import com.example.zjulss.utils.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuangyifei
 */
@RestController
@RequestMapping("/code")
public class AliyunSmsApiController {

    @Autowired
    private PhoneCodeService phoneCodeService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    /**
     * 短信发送
     *
     * @param phone
     * @return
     */
    @ResponseBody
    @GetMapping("/send/{phone}")
    public BaseResponse sendCode(@PathVariable("phone") String phone) {
        // 根据手机号从redis中拿验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return BaseResponse.success("you may call this interface too frequently");
        }

        // 如果redis 中根据手机号拿不到验证码，则生成4位随机验证码
        code = MyStringUtils.getRandom4();

        // 验证码存入codeMap
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);

        // 调用aliyunSendSmsService发送短信
        Boolean bool = phoneCodeService.sendMessage(phone, templateCode, codeMap);

        if (bool) {
            // 如果发送成功，则将生成的4位随机验证码存入redis缓存,5分钟后过期
//            redisTemplate.opsForValue().set(phone, code, 3, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(phone, code);
            return BaseResponse.success(phone + " ： " + code + "发送成功！");
        } else {
            return BaseResponse.fail(phone + " ： " + code + "发送失败！");
        }
    }
}