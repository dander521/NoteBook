package com.roger.notebook.Service.impl;

import com.roger.notebook.Service.UserService;
import com.roger.notebook.Service.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UserServiceImpl implements UserService {

    @Override
    public int login(String phone, String password) {
        return 0;
    }

    @Override
    public int register(String phone, String password) {
        return 0;
    }

    @Override
    public UserModel getUserInfo() {
        return null;
    }

    @Override
    public boolean updateUserInfo(UserModel userModel) {
        return false;
    }
}
