package com.example.demo3.service;


import com.example.demo3.pojo.Roll;
import com.example.demo3.pojo.Userroll;

import java.util.ArrayList;
import java.util.List;

public interface UserrollService {

    //根据用户名获取我的所有优惠卷
    ArrayList<Roll> getallrollbyusername(String username);

    //下单时，根据用户名和店铺用户名获取我的所有可用优惠卷
    ArrayList<Roll> getuserableroll(String username,String storeusername,double conditions);

    //修改优惠卷状态为已使用
    int alterrolltoalreadyuse(String username,String number);

    //修改优惠卷状态为未使用
    int alterrolltonouse(String username, String number);

    //根据用户名和优惠卷兑换码获取我的唯一优惠卷
    Userroll getonerollbyusernameandnumber(String username,String number);

    //用户获取优惠卷
    void addoneuserroll(Userroll userroll);
}
