package com.example.zjulss.controller;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.GoodForSaleService;
import com.example.zjulss.utils.HostHolder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/good")
public class GoodForSaleController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    GoodForSaleService goodForSaleService;

    @PostMapping("/add")
    @ResponseBody
    @LoginRequired
    public BaseResponse addGood(@RequestBody GoodForSale goodForSale, HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        goodForSale.setModify(LocalDateTime.now());
        goodForSale.setUid(userInfo.getId());
        if (goodForSaleService.insertGood(goodForSale)) {
            return BaseResponse.success();
        }
        return BaseResponse.fail("添加失败");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    @LoginRequired
    public BaseResponse removeGood(@PathVariable("id") int id, HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        GoodForSale goodForSale = goodForSaleService.getGoodInfo(id);
        if (goodForSale == null) {
            return BaseResponse.fail(HttpServletResponse.SC_NOT_FOUND, "不存在此商品");
        }
        if (userInfo.getId() != goodForSale.getUid()) {
            return BaseResponse.fail(HttpServletResponse.SC_UNAUTHORIZED, "清先登录");
        }
        if (goodForSaleService.removeGood(id)) {
            return BaseResponse.fail("删除成功");
        }
        return BaseResponse.fail("删除失败");
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public GoodForSale getDetailOfGood(@PathVariable("id") int id, HttpServletResponse httpServletResponse) throws IOException {
        return goodForSaleService.getGoodInfo(id);
    }


}
