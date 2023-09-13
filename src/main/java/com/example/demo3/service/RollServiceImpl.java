package com.example.demo3.service;

import com.example.demo3.mapper.RollMapper;
import com.example.demo3.pojo.Roll;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class RollServiceImpl implements RollService{


    @Resource
    RollMapper mapper;//数据库操作对象

    /**
     * 查找可使用的所有优惠卷ok
     */
    @Override
    public ArrayList<Roll> getuserableroll(String username,String storeusername,double conditions)
    {
        return mapper.getuserableroll(username, storeusername, conditions);
    }

    /**
     * 根据编号获取优惠卷
     */
    @Override
    public Roll getonerollbynumber(String number) {
        return mapper.getonerollbynumber(number);
    }

    /**
     * 添加优惠卷
     */
    @Override
    public void addoneroll(Roll roll) {
        mapper.addoneroll(roll);
    }

    /**
     * 优惠卷已兑换次数+1
     */
    @Override
    public void nownumberaddone(String number) {
        mapper.nownumberaddone(number);
    }

}
