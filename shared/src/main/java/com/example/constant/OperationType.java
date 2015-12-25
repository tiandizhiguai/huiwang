package com.example.constant;

public enum OperationType {

    SUCCESS("success"), ADD("add"), EDIT("edit"), DELETE("delete");

    private OperationType(String value){
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
