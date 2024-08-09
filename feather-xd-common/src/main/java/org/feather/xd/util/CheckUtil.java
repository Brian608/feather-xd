package org.feather.xd.util;

import java.util.regex.Pattern;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.util
 * @className: CheckUtil
 * @author: feather
 * @description:
 * @since: 2024-08-09 21:47
 * @version: 1.0
 */
public class CheckUtil {
    /**
     * 邮箱正则
     */
    private static final Pattern MAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 手机号正则，暂时未用
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    /**
     * @param email
     * @return
     */
    public static  boolean isEmail(String email) {
        if (null == email || email.isEmpty()) {
            return false;
        }
        return MAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 暂时未用
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (null == phone || phone.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
}
