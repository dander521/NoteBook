package com.roger.notebook.Service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {

    private Integer uuid;
    private String phone;
    private String password;
    private String avatar;
    private String nickname;
    private Integer sex;
    private String address;
}
