package com.example.demo3.service;

import com.example.demo3.pojo.Roll;

import java.util.ArrayList;

public interface RollService
{

    //查找可使用的所有优惠卷
    ArrayList<Roll> getuserableroll(String username,String storeusername,double conditions);

    //根据编号获取优惠卷
    Roll getonerollbynumber(String number);

    //添加优惠卷
    void addoneroll(Roll roll);


    //优惠卷兑换次数+1
    void nownumberaddone(String number);
}