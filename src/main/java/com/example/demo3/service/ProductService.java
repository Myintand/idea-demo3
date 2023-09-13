package com.example.demo3.service;

import com.example.demo3.pojo.Product;

import java.util.ArrayList;

public interface ProductService
{

    //查找店铺商品
    ArrayList<Product> getProductList(String username);

    //根据主键组查找
    Product findProductList(String username,String productname);

    //删除
    int delete(String ussername,String productname);

    //添加商品
    int addproduct(Product product);

    //修改
    int modify(Product product,String lastproductname);
}
