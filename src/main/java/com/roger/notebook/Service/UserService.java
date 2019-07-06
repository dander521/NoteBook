package com.roger.notebook.Service;

import com.roger.notebook.Service.model.UserModel;

public interface UserService {

    public int login(String phone, String password);

    public int register(String phone, String password);

    public UserModel getUserInfo();

    public boolean updateUserInfo(UserModel userModel);
}
