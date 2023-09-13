package com.example.demo3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//数据表，一个表对应一个类
@AllArgsConstructor
@NoArgsConstructor
@Data
//优惠卷
public class Roll
{
    //优惠卷编号
    String number;
    //店铺用户名
    String storeusername;
    //店铺名称
    String storename;
    //优惠比率
    double rate;
    //优惠金额
    double reduce;
    //使用条件
    double conditions;
    //优惠卷介绍
    String introduce;
    //优惠卷是否多张
    boolean isalluseable;
    //优惠卷开启兑换时间
    String begintime;
    //优惠卷结束兑换时间
    String endtime;
    //当前兑换数量
    int nownumber;
    //最大兑换数量
    int maxnumber;
    //优惠卷是否可兑换
    int state;

}

