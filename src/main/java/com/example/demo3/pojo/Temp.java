package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Temp
{
    //用户名（类的一个对象对应表的一列）
    String username;
    //商户用户名
    String storeusername;
    //价格
    double money;
    //下单时间
    String time;

}

