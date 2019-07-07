package com.roger.notebook.controller.viewmodel;

import lombok.Data;

@Data
public class UserVO {

    private Integer uuid;
    private String phone;
    private String avatar;
    private String nickname;
    private Integer sex;
    private String address;
}
