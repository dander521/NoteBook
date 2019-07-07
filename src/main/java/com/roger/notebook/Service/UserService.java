package com.roger.notebook.Service;

import com.roger.notebook.Service.model.UserModel;
import com.roger.notebook.error.BusinessException;

public interface UserService {

    public UserModel login(String phone, String password) throws BusinessException;

    public boolean register(String phone, String password) throws BusinessException;

    public UserModel getUserInfo(int uuid) throws BusinessException;

    public boolean updateUserInfo(UserModel userModel) throws BusinessException;
}
