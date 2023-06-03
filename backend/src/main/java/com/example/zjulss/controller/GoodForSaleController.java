package com.example.zjulss.controller;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.GoodForSaleService;
import com.example.zjulss.utils.HostHolder;
import com.example.zjulss.utils.MyStringUtils;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/good")
public class GoodForSaleController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    GoodForSaleService goodForSaleService;

    @GetMapping("/list")
    @ResponseBody
    @LoginRequired
    public List<GoodForSale> getUserSells(HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        if(userInfo == null){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"need login first");
            return null;
        }
        return goodForSaleService.getUserSells(userInfo.getId());
    }

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
            return BaseResponse.fail(HttpServletResponse.SC_UNAUTHORIZED, "您不是商品的发布者");
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

    @PostMapping("/updateName")
    @ResponseBody
    public boolean updateName(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) throws IOException {
        String id = map.get("id");
        String newName = map.get("newName");
        if (!MyStringUtils.checkIsValid(id, newName)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodName(Integer.parseInt(id), newName);
    }

    @PostMapping("/updatePrice")
    @ResponseBody
    public boolean updatePrice(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) throws IOException {
        String id = map.get("id");
        String price = map.get("newPrice");
        if (!MyStringUtils.checkIsValid(id, price)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodPrice(Integer.parseInt(id), Double.parseDouble(price));
    }

    @PostMapping("/updateCount")
    @ResponseBody
    public boolean updateCount(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) throws IOException {
        String id = map.get("id");
        String count = map.get("newCount");
        if (!MyStringUtils.checkIsValid(id, count)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodCount(Integer.parseInt(id), Integer.parseInt(count));
    }

    @GetMapping("/feed")
    @ResponseBody
    public List<GoodForSale> getRecommendGood(){
        return goodForSaleService.getGoodFeed();
    }
    @GetMapping("/all")
    @ResponseBody
    public List<GoodForSale> getAllGoodOfLimit(@RequestParam(value = "limit",required = false,defaultValue = "100") Integer limit,@RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset){
        return goodForSaleService.getGoodsByOffsetAndLimit(offset,limit);
    }

    @GetMapping("/getBySort")
    @ResponseBody
    public List<GoodForSale> getAllGoodOfSort(@RequestParam(value = "sort",required = true) int sort){
            return goodForSaleService.getGoodsBySort(sort);
    }
    //TODO 留言信息服务
}
