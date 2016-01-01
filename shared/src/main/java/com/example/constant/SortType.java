package com.example.constant;

public enum SortType {

    NORMAL("gmt_created"), ID("id");

    private SortType(String value){
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
