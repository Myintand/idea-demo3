package com.example.demo3.mapper;

import com.example.demo3.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

//二、mapper层
//同类： mapper层 = dao层
//作用： 对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的
@Mapper
public interface ProductMapper
{
    /**
     * 查找店铺商品ok
     */
    @Select("select * from product where username=#{username}")//注解式开发，自动匹配bean中的数据库
    ArrayList<Product> getProductList(@Param("username") String username);//传入参数，返回sql查询后的数据


    /**
     * 查找商品(根据主键组)ok
     */
    @Select("select * from product where username=#{username} and productname=#{productname}")//注解式开发，自动匹配bean中的数据库
    Product findProductList(@Param("username") String username, @Param("productname") String productname);//传入参数，返回sql查询后的数据


    /**
     * 删除商品ok
     */
    @Delete("DELETE FROM product WHERE username = #{username} AND productname = #{productname};")
    int delete(@Param("username") String username,@Param("productname") String productname);

    /**
     * 添加商品ok
     */
    @Insert("insert into product(username,productname,price,picture,introduce) " +
            "values(#{username},#{productname},#{price},#{picture},#{introduce});")
    int addproduct(Product product);

    /**
     * 修改商品信息ok
     */
    @Update("update product" +
            " set productname=#{product.productname},price=#{product.price},introduce=#{product.introduce}" +
            " where username=#{product.username} and productname=#{lastproductname}")
    int modify(@Param("product") Product product, @Param("lastproductname") String lastproductname);



}