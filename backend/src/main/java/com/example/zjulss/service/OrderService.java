package com.example.zjulss.service;

import com.example.zjulss.dao.OrderMapper;
import com.example.zjulss.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    public List<Order> getOrderOfUser(int uid) {
        return orderMapper.getOrderOfUser(uid);
    }

    public List<Order> getSells(int uid) {
        return orderMapper.getSells(uid);
    }

    public List<Order> getBroughts(int uid) {
        return orderMapper.getBroughts(uid);
    }

    public boolean addOrder(Order order) {
        return orderMapper.addOrder(order) == 1;
    }

    public boolean acceptOrder(int id) {
        return orderMapper.acceptOrder(id, LocalDateTime.now()) == 1;
    }

    public boolean refuseOrder(int id) {
        return orderMapper.refuseOrder(id, LocalDateTime.now()) == 1;
    }

    public boolean cancelOrder(int id) {
        return orderMapper.cancelOrder(id, LocalDateTime.now()) == 1;
    }

    public Order getOrder(int id) {
        return orderMapper.getOrder(id);
    }


}
