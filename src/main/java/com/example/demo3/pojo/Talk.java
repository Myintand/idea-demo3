package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
//优惠卷
public class Talk
{
    //用户名（类的一个对象对应表的一列）
    String username;
    //店铺用户名
    String storeusername;
    //店铺名称
    String storename;
    //标志属性，看是什么类型的数据
    int flag;
    //内容
    String talking;
    //图片路径
    String picture;
}

