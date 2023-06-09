package com.example.zjulss.controller;

import com.example.zjulss.annotation.LoginRequired;
import com.example.zjulss.entity.Cart;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.entity.Order;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.response.BaseResponse;
import com.example.zjulss.service.CartService;
import com.example.zjulss.service.GoodForSaleService;
import com.example.zjulss.service.OrderService;
import com.example.zjulss.utils.HostHolder;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/cart")
@Controller
@RestController
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    GoodForSaleService goodForSaleService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    OrderService orderService;

    @GetMapping("/detail")
    @ResponseBody
    @LoginRequired
    public List<Cart> getAllCart(HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        return cartService.getGoodsInCartOfUser(userInfo.getId());
    }

    @ResponseBody
    @GetMapping("/delete/{id}")
    @LoginRequired
    public boolean removeRecord(@PathVariable("id") int id,
                                HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        Cart cart = cartService.getCart(id);
        if (userInfo.getId() != cart.getUid()) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "you're now allowed to remove cart which is" +
                    " not belong to you");
            return false;
        }
        return cartService.removeRecord(id);
    }

    @ResponseBody
    @LoginRequired
    @GetMapping("/total")
    public int sum() {
        UserInfo userInfo = hostHolder.getUser();
        List<Cart> list = cartService.getGoodsInCartOfUser(userInfo.getId());
        int sum = 0;
        for (var cart : list) {
            GoodForSale goodForSale = goodForSaleService.getGoodInfo(cart.getQid());
            sum += goodForSale.getPrice() * cart.getQuantity();
        }
        return sum;
    }

    @ResponseBody
    @PostMapping("/add")
    @LoginRequired
    public BaseResponse addRecord(@RequestBody Cart cart, HttpServletResponse httpServletResponse) throws IOException {
        UserInfo userInfo = hostHolder.getUser();
        cart.setUid(userInfo.getId());
        if (!cartService.addRecord(cart)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "add fail");
            return BaseResponse.fail();
        }
        return BaseResponse.success();
    }

    @ResponseBody
    @PostMapping("/setquantity")
    @LoginRequired
    public BaseResponse setQuantity(@RequestBody Cart cart, HttpServletResponse httpServletResponse) throws IOException {
        cart.setModify(LocalDateTime.now());
        int stock = goodForSaleService.getGoodInfo(cart.getQid()).getCount();
        if(cart.getQuantity() > stock) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "quantity exceed stock");
            return BaseResponse.fail();
        }
        if (!cartService.setQuantity(cart)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "invalid post body");
            return BaseResponse.fail();
        }
        return BaseResponse.success();
    }

    @GetMapping("/submit")
    @LoginRequired
    @ResponseBody
    public BaseResponse submitOrder(@RequestParam(value = "id",required = true) int cartid, HttpServletResponse httpServletResponse) throws IOException {
        Cart cart = cartService.getCart(cartid);
        if(cart == null) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "invalid cart id");
            return BaseResponse.fail();
        }
        GoodForSale good = goodForSaleService.getGoodInfo(cart.getQid());
        System.out.println(cart);
        System.out.println(good);
        if(cart.getQuantity() > good.getCount()) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "quantity exceed stock");
            return BaseResponse.fail();
        }
        UserInfo userInfo = hostHolder.getUser();
        Order order = new Order();
        order.setSeller(good.getUid());
        order.setBuyer(userInfo.getId());
        order.setQid(cart.getQid());
        order.setQuantity(cart.getQuantity());
        order.setCreateTime(LocalDateTime.now());
        if(!orderService.addOrder(order)) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "submit order fail");
            return BaseResponse.fail();
        }
        cartService.removeRecord(cartid);
        return BaseResponse.success();
    }
}
