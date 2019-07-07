package com.roger.notebook.controller.viewmodel;

import lombok.Data;

@Data
public class BookVO {

    private Integer bookid;
    private String title;
    private String subtitle;
    private String photo;
    private String createtime;
    private Integer uuid;
}
