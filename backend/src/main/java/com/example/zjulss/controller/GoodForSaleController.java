package com.example.zjulss.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.example.zjulss.vo.GoodForSaleBySortVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.entity.GoodMessage;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.GoodESService;
import com.example.zjulss.service.GoodForSaleService;
import com.example.zjulss.service.GoodMessageService;
import com.example.zjulss.utils.HostHolder;
import com.example.zjulss.utils.MyStringUtils;

@Controller
@RequestMapping("/good")
public class GoodForSaleController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    GoodForSaleService goodForSaleService;

    @Autowired
    GoodESService goodESService;

    @Autowired
    GoodMessageService goodMessageService;

    @GetMapping("/list")
    @ResponseBody
    @LoginRequired
    public List<GoodForSale> getUserSells(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        if (userInfo == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "need login first");
            return null;
        }
        return goodForSaleService.getUserSells(userInfo.getId(), offset, limit);
    }

    @PostMapping("/add")
    @ResponseBody
    @LoginRequired
    public BaseResponse addGood(@RequestBody GoodForSale goodForSale, HttpServletResponse httpServletResponse)
            throws IOException {
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
    public BaseResponse removeGood(@PathVariable("id") int id, HttpServletResponse httpServletResponse)
            throws IOException {
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
    public GoodForSale getDetailOfGood(@PathVariable("id") int id, HttpServletResponse httpServletResponse)
            throws IOException {
        return goodForSaleService.getGoodInfo(id);
    }

    @PostMapping("/updateName")
    @ResponseBody
    public boolean updateName(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
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
    public boolean updatePrice(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
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
    public boolean updateCount(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String count = map.get("newCount");
        if (!MyStringUtils.checkIsValid(id, count)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodCount(Integer.parseInt(id), Integer.parseInt(count));
    }

    @PostMapping("/updateSort")
    @ResponseBody
    public boolean updateSort(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String sort = map.get("newSort");
        if (!MyStringUtils.checkIsValid(id, sort)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodSort(Integer.parseInt(id), Integer.parseInt(sort));
    }

    @PostMapping("/updateRemark")
    @ResponseBody
    public boolean updateRemark(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String remark = map.get("newRemark");
        if (!MyStringUtils.checkIsValid(id, remark)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodRemark(Integer.parseInt(id), remark);
    }

    @PostMapping("/updateTransaction")
    @ResponseBody
    public boolean updateTransaction(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String transaction = map.get("newTransaction");
        if (!MyStringUtils.checkIsValid(id, transaction)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodTransaction(Integer.parseInt(id), Integer.parseInt(transaction));
    }

    @PostMapping("/updateImage")
    @ResponseBody
    public boolean updateImage(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse)
            throws IOException {
        String id = map.get("id");
        String image = map.get("newImage");
        if (!MyStringUtils.checkIsValid(id, image)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodForSaleService.updateGoodImage(Integer.parseInt(id), image);
    }

    @GetMapping("/feed")
    @ResponseBody
    public List<GoodForSale> getRecommendGood() {
        return goodForSaleService.getGoodFeed();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<GoodForSale> getAllGoodOfLimit(
            @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return goodForSaleService.getGoodsByOffsetAndLimit(offset, limit);
    }

    @GetMapping("/getBySort")
    @ResponseBody
    public GoodForSaleBySortVO getGoodOfSort(@RequestParam(value = "sort", required = true) int sort, @RequestParam(value = "page", required = true) int page) {
        return new GoodForSaleBySortVO(goodForSaleService.getGoodsBySort(sort,page),goodForSaleService.getSortTotal(sort));
    }

    // ES 部分,仅有模糊搜索实现
    @GetMapping("/name")
    @ResponseBody
    public List<GoodForSale> goodsListByNameContaining(@RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "sortByPrice", required = false) String sortByPrice,
            @RequestParam(value = "searchSort", required = false) String searchSort,
            @RequestParam(value = "sort", required = false, defaultValue = "0") int sort,
            HttpServletResponse httpServletResponse) throws IOException {
        boolean isSortByPrice = !"false".equals(sortByPrice);
        boolean isSearchSort = !"false".equals(searchSort);
        return goodESService.searchGoodForSale(name, page, isSortByPrice, isSearchSort, sort);
    }

    // TODO 留言信息服务
    @PostMapping("/comment")
    @ResponseBody
    @LoginRequired
    public BaseResponse addComment(@RequestBody GoodMessage goodMessage, HttpServletResponse httpServletResponse)
            throws IOException {
        if (!MyStringUtils.checkIsValid(goodMessage.getContent())) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return BaseResponse.fail("留言内容不能为空");
        }
        try {
            UserInfo userInfo = hostHolder.getUser();
            goodMessage.setUid(userInfo.getId());
            if (goodMessageService.insertGoodMessage(goodMessage)) {
                return BaseResponse.success();
            }
        } catch (Exception e) {
            return BaseResponse.fail(e.toString());
        }
        return BaseResponse.fail("留言失败");
    }

    @GetMapping("/getComment")
    @ResponseBody
    public List<GoodMessage> addComment(@RequestParam(value = "qid",required = true) int qid, HttpServletResponse httpServletResponse)
            throws IOException {
        try {
            return goodMessageService.getGoodMessageByQid(qid);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Deprecated
    @GetMapping("/importES")
    @ResponseBody
    public boolean insertAllGoodToES() {
        goodForSaleService.insertAllToESSErvice();
        return true;
    }

}
