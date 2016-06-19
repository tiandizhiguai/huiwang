package com.huiwang.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringUtils {

    private static final Logger logger = LogManager.getLogger(StringUtils.class);
    
    public static String chineseToPinyin(String chineseStr) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写，音标方式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        String pinyingStr = "";
        try {
            pinyingStr = PinyinHelper.toHanYuPinyinString(chineseStr, defaultFormat, "", true);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("failed to convert chinese to pinyin", e);
        }
        return pinyingStr;
    }
}
