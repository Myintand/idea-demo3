package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
//优惠卷
public class Userroll
{
    //用户名
    String username;
    //优惠卷唯一id
    String number;
    //优惠卷状态
    int state;
}

