package com.example.demo3.controller.tool;


public class IsLegalTel {

    private static final String TEL_PATTERN = "^1[3-9]\\d{9}$";

    public static boolean isLegalTel(String tel)
    {
        return tel.matches(TEL_PATTERN);
    }
}
