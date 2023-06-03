package com.example.zjulss.controller;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.GoodWanted;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.service.GoodWantedService;
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
    public boolean addWanted( @RequestBody GoodWanted goodWanted) {
        UserInfo userInfo = hostHolder.getUser();
        goodWanted.setUid(userInfo.getId());
        goodWanted.setModify(LocalDateTime.now());
        return goodWantedService.addGoodWanted(goodWanted);
    }

    @GetMapping("/get")
    @ResponseBody
    public List<GoodWanted> getWanted(HttpServletResponse httpServletResponse) throws IOException {
        if (hostHolder.getUser() == null) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "not login");
            return null;
        }
        return goodWantedService.getUserWanted(hostHolder.getUser().getId());
    }

    @PostMapping("/remove")
    @ResponseBody
    public boolean removeWanted(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) throws IOException {
        String id = map.get("id");
        if (id == null) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "illegal argument");
            return false;
        }
        return goodWantedService.removeWanted(Integer.parseInt(id));
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
        return goodWantedService.updateWantedName(Integer.parseInt(id), newName);
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
        return goodWantedService.updateWantedPrice(Integer.parseInt(id), Double.parseDouble(price));
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
        return goodWantedService.updateWantedCount(Integer.parseInt(id), Integer.parseInt(count));
    }


}
