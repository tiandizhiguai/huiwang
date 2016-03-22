package com.huiwang.constant;

public enum SortType {

    GMT_CREATED("gmt_created"), ID("id"), GMT_MODIFIED("gmt_modified");

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
