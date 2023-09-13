package com.example.demo3.service;

import com.example.demo3.mapper.UserrollMapper;
import com.example.demo3.pojo.Roll;
import com.example.demo3.pojo.Userroll;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service

public class UserrollServiceImpl implements UserrollService{
    @Resource
    private UserrollMapper mapper;

    /**
     * 根据用户id获取我的所有优惠卷
     */
    @Override
    public ArrayList<Roll> getallrollbyusername(String username) {
        return mapper.getallrollbyusername(username);
    }

    /**
     * 根据用户id和店铺id和订单原价获取我的所有可使用优惠卷
     */
    @Override
    public ArrayList<Roll> getuserableroll(String username, String storeusername, double conditions) {
        return mapper.getuserableroll(username, storeusername,conditions);
    }

    /**
     * 设置优惠卷为已使用
     */
    @Override
    public int alterrolltoalreadyuse(String username, String number) {
        return mapper.alterrolltoalreadyuse(username, number);
    }

    /**
     * 设置优惠卷为未使用
     */
    @Override
    public int alterrolltonouse(String username, String number) {
        return mapper.alterrolltonouse(username, number);
    }

    /**
     * 根据用户名和优惠卷兑换码获取我的唯一优惠卷
     */
    @Override
    public Userroll getonerollbyusernameandnumber(String username,String number) {
        return mapper.getonerollbyusernameandnumber(username,number);
    }

    /**
     * 用户获取优惠卷
     */
    @Override
    public void addoneuserroll(Userroll userroll) {
        mapper.addoneuserroll(userroll);
    }
}
