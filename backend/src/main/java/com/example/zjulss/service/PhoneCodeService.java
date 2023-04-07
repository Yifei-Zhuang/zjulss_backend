package com.example.zjulss.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author: zhuangyifei
 */
@Service
public class PhoneCodeService {

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 发送短信验证码
     *
     * @param phone        接收短信的手机号
     * @param templateCode 短信模板CODE
     * @param codeMap      验证码map 集合
     * @return
     */
    public Boolean sendMessage(String phone, String templateCode, Map<String, Object> codeMap) {

        /**
         * 连接阿里云：
         *
         * 三个参数：
         * regionId 不要动，默认使用官方的
         * accessKeyId 自己的用户accessKeyId
         * accessSecret 自己的用户accessSecret
         */
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        // 构建请求：
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 自定义参数：
        request.putQueryParameter("PhoneNumbers", phone);// 手机号
        request.putQueryParameter("SignName", "zjulss");// 短信签名
        request.putQueryParameter("TemplateCode", templateCode);// 短信模版CODE

        // 构建短信验证码
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(codeMap));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}