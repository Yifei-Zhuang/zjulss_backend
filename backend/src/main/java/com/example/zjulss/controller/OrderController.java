package com.example.zjulss.controller;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.Order;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.OrderService;
import com.example.zjulss.utils.HostHolder;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    HostHolder hostHolder;

    @ResponseBody
    @GetMapping("/getall")
    @LoginRequired
    public List<Order> getOrderOfUser() {
        UserInfo userInfo = hostHolder.getUser();
        return orderService.getOrderOfUser(userInfo.getId());
    }

    @ResponseBody
    @GetMapping("/getsell")
    @LoginRequired
    public List<Order> getSellOrderOfUser() {
        UserInfo userInfo = hostHolder.getUser();
        return orderService.getSells(userInfo.getId());
    }

    @ResponseBody
    @GetMapping("/getbuy")
    @LoginRequired
    public List<Order> getBuyOrderOfUser() {
        UserInfo userInfo = hostHolder.getUser();
        return orderService.getBroughts(userInfo.getId());
    }

    @ResponseBody
    @PostMapping("/accept")
    @LoginRequired
    public BaseResponse acceptOrder(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) {
        UserInfo userInfo = hostHolder.getUser();
        String orderId = map.get("id");
        if (orderId == null || orderId.isEmpty()) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "missing id");
        }
        Order order = orderService.getOrder(Integer.parseInt(orderId));
        if (order.getSeller() != userInfo.getId()) {
            return BaseResponse.fail(HttpServletResponse.SC_FORBIDDEN, "当前用户不是商品出售者");
        }
        order.setEndTime(LocalDateTime.now());
        if (orderService.acceptOrder(Integer.parseInt(orderId))) {
            return BaseResponse.success("success");
        } else {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "fail");
        }
    }

    @ResponseBody
    @PostMapping("/refuse")
    @LoginRequired
    public BaseResponse refuseOrder(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) {
        UserInfo userInfo = hostHolder.getUser();
        String orderId = map.get("id");
        if (orderId == null || orderId.isEmpty()) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "missing id");
        }
        Order order = orderService.getOrder(Integer.parseInt(orderId));
        if (order.getSeller() != userInfo.getId()) {
            return BaseResponse.fail(HttpServletResponse.SC_FORBIDDEN, "当前用户不是商品出售者");
        }
        order.setEndTime(LocalDateTime.now());
        if (orderService.refuseOrder(Integer.parseInt(orderId))) {
            return BaseResponse.success("success");
        } else {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "fail");
        }
    }

    @ResponseBody
    @PostMapping("/cancel")
    @LoginRequired
    public BaseResponse cancelOrder(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) {
        UserInfo userInfo = hostHolder.getUser();
        String orderId = map.get("id");
        if (orderId == null || orderId.isEmpty()) {
            return BaseResponse.fail(HttpServletResponse.SC_BAD_REQUEST, "missing id");
        }
        Order order = orderService.getOrder(Integer.parseInt(orderId));
        if (order.getBuyer() != userInfo.getId()) {
            return BaseResponse.fail(HttpServletResponse.SC_FORBIDDEN, "当前用户不是商品购买者");
        }
        order.setEndTime(LocalDateTime.now());
        if (orderService.cancelOrder(Integer.parseInt(orderId))) {
            return BaseResponse.success("success");
        } else {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "fail");
        }
    }

    @ResponseBody
    @PostMapping("/add")
    @LoginRequired
    public BaseResponse addOrder(@RequestBody Order order, HttpServletResponse httpServletResponse) {
        UserInfo userInfo = hostHolder.getUser();
        System.out.println(order);
        if (order.getBuyer() != userInfo.getId()) {
            return BaseResponse.fail(HttpServletResponse.SC_FORBIDDEN, "发送订单的购买者id不是当前登录用户");
        }
        order.setCreateTime(LocalDateTime.now());
        if (orderService.addOrder(order)) {
            return BaseResponse.success("success");
        } else {
            return BaseResponse.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "fail");
        }
    }

}
