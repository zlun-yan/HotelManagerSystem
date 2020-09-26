package com.csu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.csu.bean.CustomInfo;
import com.csu.dbUtil.DBUtils;

public class CustomInfoImpl implements CustomInfoDao {

	@Override
	public int customsInsert(String name, String telephone, String idnum) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		try {
			String sql = "INSERT INTO customInfo VALUE(?,?,?,0,0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, idnum);
			ps.setString(2, name);
			ps.setString(3, telephone);

			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				DBUtils.close(null, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean isExsit(String id) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		ResultSet rs =null;
		
		boolean flag = false;
		try {
			String sql = "SELECT * FROM custominfo";
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();	//获取结果集
			while(rs.next()) {
				String Id = rs.getString(1).toString();
				if(id.equals(Id)) {
					flag = true;	//如果存在，则直接退出循环
					break;
				}
			}
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
		
		return flag;
	}

	@Override
	public Object[][] getCustomInfoRowDate(List<CustomInfo> list) {
		 if (list != null && list.size() > 0) {
	            Object[][] data = new Object[list.size()][5];
	            for (int i = 0; i < list.size(); i++) {
	                data[i][0] = list.get(i).getId();
	                data[i][1] = list.get(i).getName();
	                data[i][2] = list.get(i).getTelephone();
	                data[i][4] = list.get(i).getTotConsume();
	            }
	            return data;
	        }
	        return null;
	}

	@Override
	public Object[][] getCustomInfoRowDate(int index, int pageSize, String value) {
		return getCustomInfoRowDate(getCustomInfoByPage(index, pageSize, value));
	}

	@Override
	public List<CustomInfo> getCustomInfoByPage(int index, int pageSize, String value) {
		List<CustomInfo> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (value == null) {
                String sql = "SELECT * FROM custominfo LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, index);
                statement.setInt(2, pageSize);
            }
            else {
                String sql = "SELECT * FROM custominfo WHERE id = ? LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, value);
                statement.setInt(2, index);
                statement.setInt(3, pageSize);
            }

            rs = statement.executeQuery();

            while (rs.next()) {
                CustomInfo customs = new CustomInfo();

                customs.setId(rs.getString(1));
                customs.setName(rs.getString(2));
                customs.setTelephone(rs.getString(3));
                customs.setTotConsume(rs.getDouble(4));

                list.add(customs);
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
	public int getCount() {
		 Connection connection = null;
	        PreparedStatement statement = null;

	        ResultSet rs = null;

	        try {
	            connection = DBUtils.getConnection();

	            String sql = "SELECT COUNT(*) FROM custominfo";
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
	public double getTotConsume(String id) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		double consume=0;		
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT totConsume FROM custominfo WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();		//获取结果集
			while(rs.next())
				consume = rs.getDouble(1);	//赋值
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
	public int customInfoInsert(String name, String telephone, String idnum, String roomId) {
		Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO customInfo VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, telephone);
            statement.setString(3, idnum);
            statement.setString(4, roomId);

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
	public int getTimes(String id) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		int times=0;		
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT times FROM custominfo WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();		//获取结果集
			while(rs.next())
				times = rs.getInt(1);	//赋值
			return times;
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
	public int addTimes(String id,int times) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE custominfo SET times=? WHERE id = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, times);
			ps.setString(2, id);
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



	
}