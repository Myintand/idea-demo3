package com.example.demo3.controller.tool;

import java.util.Objects;

/**
 * 1合法 2为空 3过长 4非法特殊字符 5首位为0 6包含文字
 */
public class IsLegalUserName {

    public static int isLegalUsername(String input) {
        System.out.println("输入的字符："+input);
        //判空
        if (Objects.equals(input, "") ||input==null)
        {
            return 2;
        }
        //过长判断
        int length=input.length();
        if(length>15)
        {
            return 3;
        }
        //非法特殊字符判断(空格)
        for (int i = 0; i < input.length(); i++) {
            if (Character.isWhitespace(input.charAt(i))) {
                return 4;
            }
        }
        //首位为0判断
        if(input.charAt(0)=='0')
        {
            return 5;
        }
        //保留字母、数字和部分特殊字符
        String regEx = "[^a-zA-Z0-9@#￥%…&*（）—+【】、；‘，。/{}|：“《》？\\p{Punct}]";
        input = input.replaceAll(regEx, "");
        System.out.println("去除非法字符后："+input);
        if(input.length()<length)
        {
           return 6;
        }
        return 1;
    }
}
