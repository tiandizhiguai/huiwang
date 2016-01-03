package com.example.result;

public class RestfulResult {

    private Object  data;

    public RestfulResult(){

    }

    public RestfulResult(Object data){
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
