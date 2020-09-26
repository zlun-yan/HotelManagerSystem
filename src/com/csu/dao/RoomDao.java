package com.csu.dao;

import java.util.List;

import com.csu.bean.Room;
import com.csu.bean.RoomType;

public interface RoomDao {
	/**
	 * 获取某类型剩余房间
	 * @param type 房间类型
	 * @return	List<String>
	 */
	List<String> getRooms(String type);
	/**
	 * 根据用户id获取用户预定的房间
	 * @param id
	 * @return	List<String>
	 */
	List<String> getBookingRooms(String id);
	/**
	 * 根据用户id获取用户入住的房间
	 * @param id
	 * @return	List<String>
	 */
	List<String> getStayingRooms(String id);
	/**
	 * 更新住店信息
	 * @param roomId    房间号
	 * @param state		房间状态
	 * @param StayInDate	入住日期
	 * @param cusid		房客id
	 * @return	int
	 */
 	int StayInRoom(int roomId,int state,String StayInDate,String cusid);
 	/**
 	 * 预定转入住
 	 * @param roomId	房间号
 	 * @param StayDate  入住日期
 	 * @return	int
 	 */
 	int BookingToStay(int roomId,String StayDate);
 	/**
 	 * 根据用户ID获取房间所有信息
 	 * @param customId	房客id
 	 * @return	List<Room>
 	 */
 	List<Room> GetRoom(String customId);
 	/**
 	 * 获取已预订的房间所有信息
 	 * @return	List<Room>
 	 */
 	List<Room> GetBookRoom();
 	/**
     * 获取表中的记录的条数
     * @return
     */
 	int getCount();
 	/**
     * 直接分页条件查询表  并返回二维数组
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回二维数组
     */
 	Object[][] getRoomRowDateWithName(int index, int pageSize, String value);
 	 /**
     * 分页条件查询
     * @param index  当前页面开始index
     * @param pageSize  每页显示记录条数
     * @param value  值
     * @return  返回查询对象的List链表
     */
 	List<Room> getRoomByPageWithName(int index, int pageSize, String value);
 	 /**
     * 将对应对象的List链表转为Object二维数组
     * @param list
     * @return
     */
 	Object[][] getRoomRowDate(List<Room> list);
 	/**
 	 * 获取房间内消费
 	 * @param roomid 房间号
 	 * @return	double
 	 */
 	double getConsume(int roomid);
 	/**
 	 * 更新房间内消费
 	 * @param roomid	房间号
 	 * @param consume	消费金额
 	 * @return int
 	 */
 	int insertConsume(int roomid,double consume);
 	/**
 	 * 获取房间价格
 	 * @param roomid	房间号
 	 * @return double
 	 */
 	double getRoomPrice(int roomid);
 	/**
 	 * 获取房间的入住日期
 	 * @param roomid
 	 * @return String
 	 */
 	String getStayDate(int roomid);
 	/**
 	 * 重置房间信息
 	 * @param roomid
 	 * @return	int
 	 */
 	int resetRoom(int roomid);
 	/**
 	 * 获取所有房间类型
 	 * @return	List<String>
 	 */
 	List<String> getTypes();
 	/**
     * 根据ID查询房间
     * @param id
     * @return 0:该房间可用 1:该房间已被预定 2:该房间已经入住 3:查询失败
     */
    int queryRoomStateById(int id);

    /**
     *
     * @param preId
     * @param newId
     * @return  0:该房间不可用  1:换房成功  2:换房类型不一样
     */
    int changeRoom(int preId, int newId);

    String getCustomId(int roomId);

	public int addRoom(int roomId, String type, int limitNum);

	/**
	 *room表
	 * @param
	 * @return
	 */
	public int deleteRoom(String type);

	/**
	 *
	 * @param roomId
	 * @param type
	 * @param limitNum
	 * @return
	 */
	public int updateRoom(int roomId, String type,int limitNum);

	/**
	 * 获取对应于type的房间数
	 * @param type
	 * @return
	 */
	public int getCount(String type);

	/**
	 * 获取房型
	 * @param index
	 * @param pageSize
	 * @return
	 */
	public List<RoomType> getRoomTypeByPage(int index, int pageSize);

	public List<Room> getRoomByPage(int index, int pageSize, String value);

	int insertRoom(int id, String type, int floor, double price);

	List<String> queryRoomType();

	int deleteRoomById(int id);
	
	double getRoomDeposit(int roomid);
}