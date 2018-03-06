package cn.mxlog.sscloud.base;

import java.util.List;

/**
 * Created by F.Du on 2017/9/8.
 */
public class BaseResult {

    private int status;
    private  String error;
    private String message;
    private List list;
    private Iterable map;
    private Object data;

    public BaseResult() {

    }

    public BaseResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }



    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Iterable getMap() {
        return map;
    }

    public void setMap(Iterable map) {
        this.map = map;
    }
}
