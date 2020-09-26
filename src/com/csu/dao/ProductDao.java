package com.csu.dao;

import java.util.List;

import com.csu.bean.Product;

public interface ProductDao {
	/**
	 * 获取商品列表
	 * @return List<String>
	 */
	List<String> getNames();
	/**
	 * 获取商品价格
	 * @param name	商品名
	 * @return	double
	 */
	double getPrice(String name);
	/**
	 * 获取商品数量
	 * @param name	商品名
	 * @return	int 
	 */
	int getProductNum(String name);
	/**
	 * 减少商品数量
	 * @param name	商品名
	 * @param num   数量
	 * @return int
	 */
	int minusProduct(String name,int num);
    /**
     * 获取表中的记录的条数
     * @return
     */
    int getCount();

    /**
     * 分页条件查询
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回查询对象的List链表
     */
    List<Product> getProductByPage(int index, int pageSize, String value);

    /**
     * 将对应对象的List链表转为Object二维数组
     * @param list
     * @return
     */
    Object[][] getProductRowDate(List<Product> list);

    /**
     * 直接分页条件查询表  并返回二维数组
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回二维数组
     */
    Object[][] getProductRowDate(int index, int pageSize, String value);

    /**
     * 根据商品名查询商品是否存在
     * @param name
     * @return 0:不存在该商品 1:该商品存在 2:查询失败
     */
    int queryProductByName(String name);

    /**
     * 根据商品的ID查询商品是否存在
     * @param id
     * @return 0:不存在该商品 1:该商品存在 2:查询失败
     */
    int queryProductById(int id);

    /**
     * 按照商品名插入商品
     * @param productName
     * @param price
     * @param num
     * @return
     */
    int insertProductByName(String productName, double price, int num);

    /**
     * 根据商品名修改商品名
     * @param preName
     * @param newName
     * @return
     */
    int updateProductWithName(String preName, String newName);

    /**
     * 根据商品名修改商品的价格
     * @param productName
     * @param price
     * @return
     */
    int updateProductWithPrice(String productName, double price);

    /**
     * 根据商品名修改商品的剩余数量
     * @param id
     * @param changeNum  表示在此前商品的剩余数量上 + num
     * @return
     */
    int updateProductWithNum(int id, int changeNum);

    /**
     * 根据商品名称删除商品
     * @param productName
     * @return
     */
    int deleteProductByName(String productName);

    /**
     * 根据商品ID删除商品
     * @param id
     * @return
     */
    int deleteProductById(int id);
}