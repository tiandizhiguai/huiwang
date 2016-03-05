package com.huiwang.constant;

public enum StatusType {

    // 依次为：正常、删除
    NORMAL("normal"), DEL("del");

    private StatusType(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
