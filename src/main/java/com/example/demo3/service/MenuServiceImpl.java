package com.example.demo3.service;

import com.example.demo3.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
    @Resource
    private MenuMapper mapper;


    @Override
    public void addcollect(String username, String recipeName) {
        mapper.addCollect(username,recipeName);
    }

    @Override
    public List<String> getcollect(String username) {
        return mapper.getCollect(username);
    }

    @Override
    public void delete(String username, String recipeName) {
        mapper.delete(username,recipeName);
    }
}
