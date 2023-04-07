package com.example.zjulss.utils;

import com.example.zjulss.entity.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    private ThreadLocal<UserInfo> users = new ThreadLocal<>();

    public UserInfo getUser() {
        return users.get();
    }

    public void setUser(UserInfo user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }

}
