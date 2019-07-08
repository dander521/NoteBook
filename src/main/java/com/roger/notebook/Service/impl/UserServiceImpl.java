package com.roger.notebook.Service.impl;

import com.roger.notebook.Service.UserService;
import com.roger.notebook.Service.model.UserModel;
import com.roger.notebook.dao.UserDOMapper;
import com.roger.notebook.dataObject.UserDO;
import com.roger.notebook.error.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserModel login(String phone, String password) throws BusinessException {
        UserDO userDO = userDOMapper.selectByPhone(phone);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);

        return userModel;
    }

    @Override
    public boolean register(String phone, String password) throws BusinessException {
        UserDO userDOInsert = new UserDO();
        userDOInsert.setPhone(phone);
        userDOInsert.setPassword(password);

        int insert = userDOMapper.insert(userDOInsert);

        return insert > 0 ? true : false;
    }

    @Override
    public UserModel getUserInfo(int uuid) throws BusinessException {
        UserDO userDO = userDOMapper.selectByPrimaryKey(uuid);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        return userModel;
    }

    @Override
    public boolean updateUserInfo(UserModel userModel) throws BusinessException {

        UserDO userDO = userDOMapper.selectByPrimaryKey(userModel.getUuid());
        BeanUtils.copyProperties(userModel, userDO);
        int insert = userDOMapper.updateByPrimaryKey(userDO);
        return insert<=0 ? false : true;
    }
}
