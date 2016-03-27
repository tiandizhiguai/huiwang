package com.huiwang.common;

public class PathUtils {

    public static String getClassPath() {
        return Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    }

    public static String getWebInfPath() {
        String getClassPath = getClassPath();
        return getClassPath.substring(0, getClassPath.indexOf("classes"));
    }

    public static String getTemplatesPath() {
        return getWebInfPath() + "templates/";
    }
}
