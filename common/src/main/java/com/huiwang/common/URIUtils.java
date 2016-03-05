package com.huiwang.common;

import org.apache.commons.lang3.StringUtils;

import com.huiwang.constant.Constants;

public class URIUtils {

    public static String getAdminPhotoFullUrl(String imgName) {
        return getFullImgUrl(Constants.ADMIN_IMG_ABSOLUTE_PATH, imgName);
    }

    public static String getNoneFullImgUrl() {
        String url = Constants.IMG_ABSOLUTE_PATH + Constants.NONE_IMG_NAME;
        url = url.replace(url.substring(0, url.indexOf("huiwang")), Constants.STYLE_DOMAIN_URI);
        return url;
    }

    public static String getFullImgUrl(String uri, String imgName) {
        if (StringUtils.isEmpty(imgName)) {
            return null;
        }
        String url = uri + imgName;
        url = url.replace(url.substring(0, url.indexOf("huiwang")), Constants.STYLE_DOMAIN_URI);
        return url;
    }
}
