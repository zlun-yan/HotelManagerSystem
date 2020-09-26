package com.csu.dao;

import java.util.List;

import com.csu.bean.CustomInfo;

public interface CustomInfoDao {
	/**
	 * 鎻掑叆鎴垮淇℃伅
	 * @param name 鎴垮濮撳悕
	 * @param telephone	鎴垮鐢佃瘽
	 * @param idnum	鎴垮韬唤璇佸彿
	 * @return int
	 */
	int customsInsert(String name,String telephone,String idnum);
	/**
	 * 鍒ゆ柇鎴垮id鏄惁瀛樺湪
	 * @param id 鎴垮id
	 * @return boolean
	 */
	boolean isExsit(String id);
	/**
     * 将对应对象的List链表转为Object二维数组
     * @param list
     * @return
     */
	Object[][] getCustomInfoRowDate(List<CustomInfo> list);
	/**
     * 直接分页条件查询表  并返回二维数组
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回二维数组
     */
	Object[][] getCustomInfoRowDate(int index, int pageSize, String value);
	 /**
     * 分页条件查询
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回查询对象的List链表
     */
	List<CustomInfo> getCustomInfoByPage(int index, int pageSize, String value);
	/**
     * 获取表中的记录的条数
     * @return
     */
	int getCount();
	/**
	 * 鑾峰彇鎴垮鎬绘秷璐�
	 * @param id
	 * @return double
	 */
	double getTotConsume(String id);
	int customInfoInsert(String name, String telephone, String idnum, String roomId);
	int getTimes(String id);
	int addTimes(String id,int times);
}
