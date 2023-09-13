package com.example.demo3.service;


import java.util.List;

public interface MenuService {

    public void addcollect(String username,String recipeName);

    public List<String> getcollect(String username);

    public void delete(String username,String recipeName);
}
