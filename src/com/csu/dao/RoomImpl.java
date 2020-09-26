package com.csu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.csu.bean.Room;
import com.csu.bean.RoomType;
import com.csu.dbUtil.DBUtils;

import static com.csu.dbUtil.DBUtils.close;

public class RoomImpl implements RoomDao {
	
	@Override
	public List<String> getRooms(String type) {
		List<String> list = new ArrayList<String>();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs =null;
		
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT id FROM room WHERE type=? AND state=0";
			ps=conn.prepareStatement(sql);
			ps.setString(1, type);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				String roomid = rs.getString(1).toString();
				list.add(roomid);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int StayInRoom(int roomId, int state,String StayInDate,String cusid) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		try {
			String sql = "UPDATE room SET state=?,date=?,customId=? WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, state);
			ps.setString(2, StayInDate);
			ps.setString(3, cusid);
			ps.setInt(4, roomId);
			
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(null, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<String> getBookingRooms(String id) {
		List<String> list = new ArrayList<String>();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs =null;
		
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT id FROM room WHERE customId=? AND state=1";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				String roomid = rs.getString(1).toString();
				list.add(roomid);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int BookingToStay(int roomId, String StayDate) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		try {
			String sql = "UPDATE room SET state=2,date=? WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, StayDate);
			ps.setInt(2, roomId);
			
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(null, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<Room> GetRoom(String customId) {
		List<Room> list  = new ArrayList<Room>();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT id, type FROM room WHERE customId = ? AND state=1";
			ps = conn.prepareStatement(sql);
			ps.setString(1,customId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Room r = new Room();
				r.setRoomId(rs.getInt(1));
				r.setType(rs.getString(2));
				r.setCustomId(customId);
				list.add(r);
			}
			return list;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public int getCount() {
		Connection connection = null;
        PreparedStatement statement = null;

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM room";
            statement = connection.prepareStatement(sql);

            rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
			try {
				DBUtils.close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

        return 0;
	}

	@Override
	public Object[][] getRoomRowDateWithName(int index, int pageSize, String value) {
		return getRoomRowDate(getRoomByPageWithName(index, pageSize, value));
		
	}

	@Override
	public List<Room> getRoomByPageWithName(int index, int pageSize, String value) {
		List<Room> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (value == null) {
                String sql = "SELECT * FROM room LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, index);
                statement.setInt(2, pageSize);
            }
            else {
                String sql = "SELECT * FROM room WHERE id = ? LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(value));
                statement.setInt(2, index);
                statement.setInt(3, pageSize);
            }

            rs = statement.executeQuery();

            while (rs.next()) {
                Room room = new Room();

                room.setRoomId(rs.getInt(1));
                room.setType(rs.getString(2));
                room.setFloor(rs.getInt(3));
                room.setPrice(rs.getDouble(4));
                room.setState(rs.getInt(5));
                room.setCustomId(rs.getString(6));
                room.setDate(rs.getString(7));
                room.setConsume(rs.getDouble(8));

                list.add(room);
            }
            return list;
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
			try {
				DBUtils.close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

        return null;
	}

	@Override
	public Object[][] getRoomRowDate(List<Room> list) {
		if (list != null && list.size() > 0) {
            Object[][] data = new Object[list.size()][8];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = list.get(i).getRoomId();
                data[i][1] = list.get(i).getType();
                data[i][2] = list.get(i).getFloor();
                data[i][3] = list.get(i).getPrice();
                data[i][4] = list.get(i).getState();
                data[i][5] = list.get(i).getCustomId();
                data[i][6] = list.get(i).getDate();
                data[i][7] = list.get(i).getConsume();
            }
            return data;
        }
        return null;
	}

	@Override
	public List<String> getStayingRooms(String id) {
		List<String> list = new ArrayList<String>();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs =null;
		
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT id FROM room WHERE customId=? AND state=2";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				String roomid = rs.getString(1).toString();
				list.add(roomid);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public double getConsume(int roomid) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		double consume=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT consume FROM room WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while(rs.next())
				consume = rs.getDouble(1);
			return consume;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int insertConsume(int roomid,double consume) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE room SET consume=? WHERE id = ? ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, consume);
			ps.setInt(2, roomid);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(null, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public double getRoomPrice(int roomid) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs =null;
		
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT price FROM room WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,roomid);
			rs = ps.executeQuery();
			double price = 0;
			while(rs.next())
				price = rs.getDouble(1);
			return price;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		
		return 0;
	}

	@Override
	public String getStayDate(int roomid) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		String date = null;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT date FROM room WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while(rs.next())
				date = rs.getString(1);
			return date;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int resetRoom(int roomid) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE room SET consume=0,date=0,state=0,customId=null WHERE id = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roomid);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(null, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<String> getTypes() {
		List<String> list = new ArrayList<String>();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs =null;
		
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT typeName FROM roomtype ";
			ps=conn.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				String type = rs.getString(1).toString();
				list.add(type);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Room> GetBookRoom() {
		List<Room> list  = new ArrayList<Room>();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs = null;
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT id, type,customId FROM room WHERE state=1 LIMIT 10";
			ps = conn.prepareStatement(sql);;
			rs = ps.executeQuery();
			while(rs.next()) {
				Room r = new Room();
				r.setRoomId(rs.getInt(1));
				r.setType(rs.getString(2));
				r.setCustomId(rs.getString(3));
				list.add(r);
			}
			return list;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		
		return null;
	}

	 @Override
	    public int queryRoomStateById(int id) {
	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;
	        ResultSet rs = null;

	        try {
	            String sql = "SELECT state FROM room WHERE id = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setInt(1, id);

	            rs = statement.executeQuery();
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        } catch (SQLException exception) {
	            exception.printStackTrace();
	        }
	        finally {
				try {
					DBUtils.close(rs, statement, connection);
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}

	        return 3;
	    }

	    @Override
	    public int changeRoom(int preId, int newId) {
	        if (queryRoomStateById(newId) != 0) return 0;

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;
	        ResultSet rs = null;

	        Object[][] data = getRoomRowDateWithName(0, 1, preId + "");
	        Object[][] dataInNew = getRoomRowDateWithName(0, 1, newId + "");
	        if (!data[0][1].toString().equals(dataInNew[0][1].toString())) return 2;

	        try {
	        	String sql3 = "SELECT state FROM room WHERE id = ?";
	        	statement = connection.prepareStatement(sql3);
	        	statement.setInt(1, preId);
	        	rs = statement.executeQuery();
	        	int state = 0;
	        	if (rs.next()) {
	        		state = rs.getInt(1);
	        	}
	        	
	        	if (state == 0) return 0;
	        	
	            String sql = "UPDATE room SET state = 0, customId = null, date = null, consume = null WHERE id = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setInt(1, preId);

	            statement.executeUpdate();
	            String sql2 = "UPDATE room SET state = ?, customId = ?, date = ?, consume = ? WHERE id = ?";
	            statement = connection.prepareStatement(sql2);
	            statement.setInt(1, state);
	            statement.setString(2, data[0][5].toString());
	            statement.setString(3, data[0][6].toString());
	            statement.setDouble(4, Double.parseDouble(data[0][7].toString()));
	            statement.setInt(5, newId);
	            return statement.executeUpdate();
	        } catch (SQLException exception) {
	            exception.printStackTrace();
	        }
	        finally {
				try {
					DBUtils.close(null, statement, connection);
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}

	        return 0;
	    }

	 public String getCustomId(int roomId) {
		Connection connection = DBUtils.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT customId FROM room WHERE id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, roomId);

			rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				DBUtils.close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return null;
	 }

	public int addRoom(int roomId, String type, int limitNum){
		Connection conn = null;//连接对象
		PreparedStatement ps = null;//sql语句的预编译对象（存sql语句的）
		try {
			conn = DBUtils.getConnection();

			//4.创建PreparedStatement对象
			String sql = "INSERT INTO roomtype(id,typeName,limitNum) VALUES(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,roomId);
			ps.setString(2, type);
			ps.setInt(3,limitNum);

			//5.执行
			return ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				close(null, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return 0;
	}

	public int deleteRoom(String type){
		Connection conn = null;//连接对象
		PreparedStatement ps = null;//sql语句的预编译对象（存sql语句的）
		try {
			conn = DBUtils.getConnection();

			//4.创建PreparedStatement对象
			String sql = "DELETE FROM roomtype WHERE typeName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,type);

			//5.执行
			return ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				close(null, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return 0;
	}

	public int updateRoom(int roomId, String type,int limitNum){
		Connection conn = null;//连接对象
		PreparedStatement ps = null;//sql语句的预编译对象（存sql语句的）
		try {
			conn = DBUtils.getConnection();

			//4.创建PreparedStatement对象
			String sql = "UPDATE roomtype SET typeName = ?,limitNum = ? WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,type);
			ps.setInt(2,limitNum);
			ps.setDouble(3,roomId);

			//5.执行
			return ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				close(null, ps, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return 0;
	}

	public int getCount(String type){
		Connection connection = DBUtils.getConnection();
		String sql = "SELECT COUNT(*) FROM room WHERE type = ?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(sql);

			statement.setString(1, type);
			rs = statement.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return 0;
	}

	public List<RoomType> getRoomTypeByPage(int index, int pageSize){
		List<RoomType> list = new ArrayList<>();

		Connection connection = null;   //连接对象
		PreparedStatement statement = null;   //SQL语句的预编译对象

		ResultSet rs = null;

		try {
			connection = DBUtils.getConnection();

			String sql;
			sql = "SELECT id,typeName,limitNum FROM roomtype LIMIT ?,?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, index);
			statement.setInt(2, pageSize);

			rs = statement.executeQuery();

			while (rs.next()) {
				RoomType roomtype = new RoomType();

				roomtype.setId(rs.getInt(1));
				roomtype.setTypeName(rs.getString(2));
				roomtype.setLimitNum(rs.getInt(3));
				list.add(roomtype);
			}
			return list;
		}catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return null;
	}

	public List<Room> getRoomByPage(int index, int pageSize, String value){
		List<Room> list = new ArrayList<>();

		Connection connection = null;   //连接对象
		PreparedStatement statement = null;   //SQL语句的预编译对象

		ResultSet rs = null;

		try {
			connection = DBUtils.getConnection();

			if (value != null) {
				String sql;
				sql = "SELECT id,type,floor,price FROM room WHERE type = ? LIMIT ?, ?";
				statement = connection.prepareStatement(sql);
				statement.setString(1, value);
				statement.setInt(2, index);
				statement.setInt(3, pageSize);
			}
			else {
				String sql;
				sql = "SELECT id,type,floor,price FROM room LIMIT ?, ?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, index);
				statement.setInt(2, pageSize);
			}

			rs = statement.executeQuery();

			while (rs.next()) {
				Room room = new Room();

				room.setRoomId(rs.getInt(1));
				room.setType(rs.getString(2));
				room.setFloor(rs.getInt(3));
				room.setPrice(rs.getDouble(4));
				list.add(room);
			}
			return list;
		}catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return null;
	}

	public int insertRoom(int id, String type, int floor, double price) {
		Connection connection = DBUtils.getConnection();
		PreparedStatement statement = null;

		try {
			String sql2 = "UPDATE floor SET roomNum = roomNum + 1 WHERE floor = ?";
			statement = connection.prepareStatement(sql2);
			statement.setInt(1, floor);
			statement.executeUpdate();
			
			
			String sql = "INSERT INTO room(id, type, floor, price) VALUES(?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, type);
			statement.setInt(3, floor);
			statement.setDouble(4, price);

			return statement.executeUpdate();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				DBUtils.close(null, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return 0;
	}

	public List<String> queryRoomType() {
		Connection connection = DBUtils.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> result = new ArrayList<>();

		try {
			String sql = "SELECT typeName FROM roomType";
			statement = connection.prepareStatement(sql);

			rs = statement.executeQuery();
			while (rs.next()) {
				String type = rs.getString(1);
				result.add(type);
			}
			return result;
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				DBUtils.close(rs, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return null;
	}

	public int deleteRoomById(int id) {
		Connection connection = DBUtils.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			String sql2 = "SELECT floor FROM room WHERE id = ?";
			statement = connection.prepareStatement(sql2);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			int floor = 0;
			if (rs.next()) {
				floor = rs.getInt(1);
			}
			
			if (floor != 0) {
				String sql3 = "UPDATE floor SET roomNum = roomNum - 1 WHERE floor = ?";
				statement = connection.prepareStatement(sql3);
				statement.setInt(1, floor);
				
				statement.executeUpdate();
			}
			
			
			String sql = "DELETE FROM room WHERE id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			return statement.executeUpdate();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				DBUtils.close(null, statement, connection);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}

		return 0;
	}

	@Override
	public double getRoomDeposit(int roomid) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		double deposit=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT deposit FROM room WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while(rs.next())
				deposit = rs.getDouble(1);
			return deposit;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(rs, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}
}
