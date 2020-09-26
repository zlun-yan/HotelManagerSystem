package com.csu.dao;

import java.util.List;

import com.csu.bean.Income;

public interface IncomeDao {
	/**
	 * 判断日期是否存在
	 * @param date
	 * @return boolean
	 */
	boolean isExsit(String date);
	/**
	 * 插入一条记录
	 * @param date	日期
	 * @param income	收入
	 * @param stayNum	住店人数
	 * @param bookingNum	预定人数
	 * @param leaveNum	离店人数
	 * @return	int
	 */
	int insert(String date,double income,int stayNum,int bookingNum,int leaveNum);
	/**
	 * 获取收入
	 * @param date	日期
	 * @return	double
	 */
	double getIncome(String date);
	/**
	 * 获取住店人数
	 * @param date
	 * @return	int
	 */
	int getStayNum(String date);
	/**
	 * 获取预定人数
	 * @param date
	 * @return	int
	 */
	int getBookNum(String date);
	/**
	 * 获取离店人数
	 * @param date
	 * @return	int
	 */
	int getLeaveNum(String date);
	/**
	 * 更新收入
	 * @param date
	 * @param income
	 * @return	int
	 */
	int updateIncome(String date,double income);
	/**
	 * 更新住店人数
	 * @param date
	 * @param stayNum
	 * @return	int
	 */
	int updateStayNum(String date,int stayNum);
	/**
	 * 更新预定人数
	 * @param date
	 * @param bookNum
	 * @return	int
	 */
	int updateBookNum(String date,int bookNum);
	/**
	 * 更新离店人数
	 * @param date
	 * @param leaveNum
	 * @return int
	 */
	int updateLeaveNum(String date,int leaveNum);
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
    List<Income> getIncomeByPage(int index, int pageSize, String value, int state);

    /**
     * 将对应对象的List链表转为Object二维数组
     * @param list
     * @return
     */
    Object[][] getIncomeRowDate(List<Income> list);

    /**
     * 直接分页条件查询表  并返回二维数组
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回二维数组
     */
    Object[][] getIncomeRowDate(int index, int pageSize, String value, int state);

    String[] getTheFirstDay();
}