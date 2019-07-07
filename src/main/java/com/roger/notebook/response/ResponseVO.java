package com.roger.notebook.response;

import lombok.Data;

@Data
public class ResponseVO<M> {

    // 状态 【0-成功 1-失败 999-系统异常】
    private int status;
    // 返回信息
    private String msg;
    // 实体
    private M data;

    // 不允许外部创建实体 单例
    private ResponseVO() {}

    public static<M> ResponseVO success(M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg("成功");
        responseVO.setData(m);

        return responseVO;
    }

    public static<M> ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO serviceFail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO appFail(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }
}
