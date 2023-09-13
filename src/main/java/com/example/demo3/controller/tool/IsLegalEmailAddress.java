package com.example.demo3.controller.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsLegalEmailAddress {

    //邮箱格式的正则表达式
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isLegalEmailAddress(String email) {

        return email.matches(EMAIL_PATTERN);
    }

}
