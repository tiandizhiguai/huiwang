package com.huiwang.common;

import org.apache.commons.lang3.StringUtils;

import com.huiwang.constant.Constants;

public class URIUtils {

    public static String getAdminPhotoFullUrl(String imgName) {
        return getFullImgUrl(imgName);
    }

    public static String getNoneFullImgUrl() {
        return Constants.STYLE_DOMAIN_URI + "img/" + Constants.NONE_IMG_NAME;
    }

    public static String getFullImgUrl(String imgName) {
        if (StringUtils.isEmpty(imgName)) {
            return null;
        }
        return Constants.STYLE_DOMAIN_URI + "img/admin/" + imgName;
    }
}
