package com.roger.notebook.Service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class NoteModel implements Serializable {

    private Integer noteid;
    private Integer bookid;
    private String bookname;
    private String remark;
    private String title;
    private String content;
    private String photo;
    private String createtime;
}
