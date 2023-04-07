package com.example.zjulss.service;

import com.example.zjulss.dao.CartMapper;
import com.example.zjulss.entity.Cart;
import com.example.zjulss.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    HostHolder hostHolder;

    public List<Cart> getGoodsInCartOfUser(int uid) {
        return cartMapper.getGoodsInCartOfUser(uid);
    }

    public boolean removeRecord(int uid, int qid) {
        return cartMapper.remove(uid, qid) != 0;
    }

    public boolean addRecord(Cart cart) {
        return cartMapper.addCart(cart) == 1;
    }

    public boolean setQuantity(Cart cart) {
        return cartMapper.setQuantity(cart.getId(), cart.getQuantity(), LocalDateTime.now()) != null;
    }
}
