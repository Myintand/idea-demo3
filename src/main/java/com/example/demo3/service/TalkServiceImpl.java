package com.example.demo3.service;

import com.example.demo3.mapper.TalkMapper;
import com.example.demo3.pojo.Talk;
import com.example.demo3.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class TalkServiceImpl implements TalkService{


    @Resource
    TalkMapper mapper;//数据库操作对象

    /**
     * 查找我和商家的所有对话
     */
    @Override
    public ArrayList<Talk> getalltalkList(String username, String storeusername,String storename)
    {
        return mapper.getalltalkList(username,storeusername,storename);
    }

    /**
     * 插入新对话
     */
    @Override
    public void addtalk(Talk talk)
    {
        mapper.addtalk(talk);
    }

    @Override
    public ArrayList<User> findtalk(String storeusername) {
        return mapper.findtalk(storeusername);
    }

}
