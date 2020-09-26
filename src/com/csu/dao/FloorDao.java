package com.csu.dao;

import com.csu.bean.Floor;

import java.util.List;

public interface FloorDao {

    /**
     *
     * @param floor
     * @param roomCount
     * @return
     */
    public int addFloor(int floor,int roomCount);

    /**
     *
     * @param floor
     * @param roomCount
     * @return
     */
    public int changeFloor(int floor, int roomCount);

    /**
     *
     * @param floor
     * @return
     */
    public int deleteFloor(int floor);

    /**
     * 根据楼层获得单条数据
     * @param
     * @return
     */
    public List<Floor> getFloorByPage(int index, int pageSize);

    /**
     * 获取楼层表行数
     * @return
     */
    public int getCount();
}
