package com.roger.notebook.controller.viewmodel;

import lombok.Data;

@Data
public class NoteVO {

    private Integer noteid;
    private String bookname;
    private String remark;
    private String title;
    private String content;
    private String photo;
    private String createtime;
    private Integer uuid;
}
