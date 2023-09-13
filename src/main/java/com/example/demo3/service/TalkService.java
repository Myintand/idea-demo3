package com.example.demo3.service;

import com.example.demo3.pojo.Talk;
import com.example.demo3.pojo.User;

import java.util.ArrayList;

public interface TalkService {
    ArrayList<Talk> getalltalkList(String username, String storeusername,String storename);

    //插入新对话
    void addtalk(Talk talk);

    //查找有过对话的用户
    ArrayList<User> findtalk(String storeusername);
}
