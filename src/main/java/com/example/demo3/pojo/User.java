package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    //用户名（类的一个对象对应表的一列）
    String username;
    //密码
    String password;
    //性别
    String sex;
    //学历
    String education;
    //邮件
    String email;
    //简介
    String introduce;
    //问题
    String problem;
    //答案
    String answer;
    //头像文件名
    String headpicture;
    //电话
    String tel;
    //地址
    String address;
    //余额
    double mymoney;
}

