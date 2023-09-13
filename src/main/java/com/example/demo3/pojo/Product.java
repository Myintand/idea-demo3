package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product
{
    //用户名（类的一个对象对应表的一列）
    String username;
    //商品名
    String productname;
    //价格
    int price;
    //图片
    String picture;
    //简介
    String introduce;
}

