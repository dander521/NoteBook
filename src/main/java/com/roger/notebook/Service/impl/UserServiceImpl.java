package com.roger.notebook.Service.impl;

import com.roger.notebook.Service.UserService;
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
}
