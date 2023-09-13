package com.example.demo3.controller.tool;

import java.util.Objects;

/**
 * 1合法 2为空 3长度 4禁止文字（非ASCII字符） 5种类太少
 */
public class IsLegalPassword
{
    public static int isLegalPassword(String password)
    {
        System.out.println("输入的字符："+password);
        //判空
        if (Objects.equals(password, "") ||password==null)
        {
            return 2;
        }
        if (password.length() < 6 || password.length() > 20) {
            return 3;
        }
        //禁止中文和外语（含非ASCII字符）
        if (!password.matches("^[\\x00-\\xff]*$")) {
            return 4;
        }
        int count = 0;
        //小写
        if (password.matches(".*[a-z].*")) {
            count++;
        }
        //大写
        if (password.matches(".*[A-Z].*")) {
            count++;
        }
        //数字
        if (password.matches(".*\\d.*")) {
            count++;
        }
        //包含特殊字符(此项包含空格)
        if (password.matches(".*[\\p{P}\\p{S}].*")) {
            count++;
        }
        if(count>=2)
        {
            return 1;
        }
        else
        {
            return 5;
        }
    }
}
