package com.roger.notebook.Service.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestTemplateModel implements Serializable {
    private String success;
    private String ShowWeb;
    private String PushKey;
    private String Url;
    private String Msg;
}
