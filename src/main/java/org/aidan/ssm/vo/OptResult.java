package org.aidan.ssm.vo;

/**
 * @author Aidan
 * @创建时间：2019/1/7 9:43 AM
 * @描述: 统一响应封装体
 */
public class OptResult {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAIL = -1;

    private int code;

    private String msg;

    private Object data;


    public OptResult() {
    }

    public OptResult(int code) {
        this.code = code;
    }

    public OptResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public OptResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static OptResult ok() {
        return new OptResult(CODE_SUCCESS);
    }

    public static OptResult ok(String msg) {
        return new OptResult(CODE_SUCCESS, msg);
    }

    public static OptResult ok(String msg, Object data) {
        return new OptResult(CODE_SUCCESS, msg, data);
    }

    public static OptResult error() {
        return new OptResult(CODE_FAIL);
    }

    public static OptResult error(String msg) {
        return new OptResult(CODE_FAIL, msg);
    }

    public static OptResult error(String msg, Object data) {
        return new OptResult(CODE_FAIL, msg, data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean ifSuccess() {
        if (code == CODE_SUCCESS) {
            return true;
        }
        return false;
    }

    public boolean ifFail() {
        if (code != CODE_SUCCESS) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "OptResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
