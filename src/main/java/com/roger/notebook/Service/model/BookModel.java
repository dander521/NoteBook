package com.roger.notebook.Service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookModel implements Serializable {

    private Integer bookid;
    private String title;
    private String subtitle;
    private String photo;
    private String createtime;
    private Integer uuid;
}
