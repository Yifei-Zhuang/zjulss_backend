package com.example.zjulss.controller;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.GoodWanted;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.GoodWantedService;
import com.example.zjulss.utils.HostHolder;
import com.example.zjulss.utils.MyStringUtils;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 个人发表的求购信息
 */
@Controller
@RequestMapping("/goodwanted")
public class GoodWantedController {
    @Autowired
    GoodWantedService goodWantedService;
    @Autowired
    HostHolder hostHolder;

    @PostMapping("/add")
    @ResponseBody
    @LoginRequired
    public BaseResponse addWanted(@RequestBody GoodWanted goodWanted) {
        UserInfo userInfo = hostHolder.getUser();
        goodWanted.setUid(userInfo.getId());
        goodWanted.setModify(LocalDateTime.now());
        if (!goodWantedService.addGoodWanted(goodWanted)) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("发布成功！");
    }

    @GetMapping("/get")
    @ResponseBody
    @LoginRequired
    public List<GoodWanted> getWanted(HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "not login");
            return null;
        }
        return goodWantedService.getUserWanted(hostHolder.getUser().getId());
    }

    @PostMapping("/remove")
    @ResponseBody
    @LoginRequired
    public boolean removeWanted(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        if (id == null) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodWantedService.removeWanted(Integer.parseInt(id));
    }

    @PostMapping("/updateName")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateName(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String newName = map.get("newName");
        if (!MyStringUtils.checkIsValid(id, newName)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("商品名称无效！");
        }
        if (!goodWantedService.updateWantedName(Integer.parseInt(id), newName)) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }

    @PostMapping("/updatePrice")
    @ResponseBody
    @LoginRequired
    public BaseResponse updatePrice(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String price = map.get("newPrice");
        if (!MyStringUtils.checkIsValid(id, price)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("商品价格无效！");
        }
        if (!goodWantedService.updateWantedPrice(Integer.parseInt(id), Double.parseDouble(price))) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }

    @PostMapping("/updateCount")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateCount(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String count = map.get("newCount");
        if (!MyStringUtils.checkIsValid(id, count)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("商品数量无效！");
        }
        if (!goodWantedService.updateWantedCount(Integer.parseInt(id), Integer.parseInt(count))) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }

    @PostMapping("/updateSort")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateSort(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String sort = map.get("newSort");
        if (!MyStringUtils.checkIsValid(id, sort)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("商品类别无效！");
        }
        if (!goodWantedService.updateWantedSort(Integer.parseInt(id), Integer.parseInt(sort))) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateRemark(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String remark = map.get("newRemark");
        if (!MyStringUtils.checkIsValid(id, remark)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("详情无效！");
        }
        if (!goodWantedService.updateWantedRemark(Integer.parseInt(id), remark)) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }

    @PostMapping("/updateTransaction")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateTransaction(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String transaction = map.get("newTransaction");
        if (!MyStringUtils.checkIsValid(id, transaction)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("交易方式无效！");
        }
        if (!goodWantedService.updateWantedTransaction(Integer.parseInt(id), Integer.parseInt(transaction))) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }

    @PostMapping("/updateImage")
    @ResponseBody
    @LoginRequired
    public BaseResponse updateImage(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String image = map.get("newImage");
        if (!MyStringUtils.checkIsValid(id, image)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("图片无效！");
        }
        if (!goodWantedService.updateWantedImage(Integer.parseInt(id), image)) {
            return BaseResponse.fail("服务器错误！");
        }
        return BaseResponse.success("修改成功！");
    }
}
