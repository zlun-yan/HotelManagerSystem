package com.csu.dao;

import com.csu.bean.Clerk;

import java.util.List;

public interface ClerkDao {
    /**
     * 获取表中的记录的条数
     * @return
     */
    int getCount();

    /**
     * 根据员工姓名分页条件查询
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回查询对象的List链表
     */
    List<Clerk> getClerkByPageWithName(int index, int pageSize, String value);

    /**
     * 根据员工职位分页条件查询
     * @param index  当前页面开始index
     * @param pageSize   每页显示记录条数
     * @param value  值
     * @return   返回查询对象的List链表
     */
    List<Clerk> getClerkByPageWithJob(int index, int pageSize, String value);

    List<Clerk> getClerkByPageWithJid(int index, int pageSize, String value);

    /**
     * 将对应对象的List链表转为Object二维数组
     * @param list
     * @return
     */
    Object[][] getClerkRowDate(List<Clerk> list);

    /**
     * 直接根据员工姓名分页条件查询表  并返回二维数组
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回二维数组
     */
    Object[][] getClerkRowDateWithName(int index, int pageSize, String value);

    /**
     * 直接根据员工职位分页条件查询表  并返回二维数组
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回二维数组
     */
    Object[][] getClerkRowDateWithJob(int index, int pageSize, String value);

    Object[][] getClerkRowDateWithJid(int index, int pageSize, String value);

    /**
     *
     * @param name
     * @param job
     * @return 0:插入失败  1:插入成功
     */
    int insertClerk(String name, String job);

    /**
     *
     * @param jid
     * @param job
     * @return 0:更新失败  1:成功
     */
    int updateJobWithJid(String jid, String job);

    /**
     *
     * @param jid
     * @return  0:失败  1:成功
     */
    int deleteClerkWithJid(String jid);

    List<Clerk> getClerkByJid(String jid);

    int updateClerk(String id,String name,String job,String pwd);
    void updateName(String id,String name);
    void updatePwd(String id,String password);
}
