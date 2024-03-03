package com.api.agroguard.utils;

public class UserValidationUtil {

    // 检查密码强度
    public static boolean checkPasswordStrength(String password) {
        // 密码必须包含大写、小写、数字和特殊字符，且长度是6位以上
        return password != null && password.matches("- ^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[`~!@#$%^&*()-=_+;':\",./<>?])(?=\\S+$).{6,}$\n");
    }

    // 验证电子邮件格式
    public static boolean checkEmailFormat(String email) {
        // 简单的正则表达式来检查电子邮件格式
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
