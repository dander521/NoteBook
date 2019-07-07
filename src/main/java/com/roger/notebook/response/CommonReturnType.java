package com.roger.notebook.response;

import lombok.Data;

@Data
public class CommonReturnType {

    private String status;
    private String code;
    private Object data;


    public static CommonReturnType creat(Object result) {
        return CommonReturnType.creat(result, "success");
    }

    public static CommonReturnType creat(Object result, String status) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(result);
        commonReturnType.setStatus(status);
        commonReturnType.setCode("0");
        return commonReturnType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
