package com.example.demo3.service;

import com.example.demo3.mapper.ProductMapper;
import com.example.demo3.pojo.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

//这个类是实现接口里面的所有方法用的，当servlet中要使用数据库数据时，先new一个接口类对象UserService userService,然后用对象名称+'.'+函数名的形式调用这边的函数
//这边函数的具体数据库内部的实现函数在userDao里面，这边的函数将实现排错和跳转功能
//这边返回的一般都是类对象，就是数据库查询返回的对象
@Service
public class ProductServiceImpl implements ProductService
{

    @Resource
    ProductMapper mapper;//数据库操作对象

    /**
     * 商品查找ok
     */
    @Override
    public ArrayList<Product> getProductList(String username)
    {
        return mapper.getProductList(username);
    }

    /**
     * 商品查找ok
     */
    @Override
    public Product findProductList(String username,String productname)
    {
        return mapper.findProductList(username,productname);
    }


    /**
     * 商品删除ok
     */
    @Override
    public int delete(String username, String productname)
    {
        return mapper.delete(username,productname);
    }

    /**
     * 添加商品ok
     */
    @Override
    public int addproduct(Product product)
    {
        return mapper.addproduct(product);
    }

    /**
     * 修改商品ok
     */
    @Override
    public int modify(Product product,String lastproductname)
    {
        return mapper.modify(product,lastproductname);
    }

}
