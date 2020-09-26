package com.csu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.csu.bean.Income;
import com.csu.dbUtil.DBUtils;

public class IncomeImpl implements IncomeDao{

	@Override
	public boolean isExsit(String date) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		ResultSet rs =null;
		
		boolean flag = false;
		try {
			String sql = "SELECT * FROM income";
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				String Id = rs.getString(2).toString();
				if(date.equals(Id)) {
					flag = true;
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
	public int insert(String date, double income, int stayNum,int bookingNum,int leaveNum) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		try {
			String sql = "INSERT INTO income VALUE(NULL,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,date);
			ps.setDouble(2, income);
			ps.setInt(3, stayNum);
			ps.setInt(4, bookingNum);
			ps.setInt(5, leaveNum);
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public double getIncome(String date) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		double income=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT income FROM income WHERE date = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
				income = rs.getDouble(1);
			return income;
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
	public int getStayNum(String date) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		int totPeo=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT stayNum FROM income WHERE date = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
				totPeo = rs.getInt(1);
			return totPeo;
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
	public int updateIncome(String date, double income) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE income SET income=? WHERE date = ? ";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, income);
			ps.setString(2, date);
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
	public int updateStayNum(String date, int stayPeo) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE income SET stayNum=? WHERE date = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, stayPeo);
			ps.setString(2, date);
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
	public int getBookNum(String date) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		int totPeo=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT bookNum FROM income WHERE date = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
				totPeo = rs.getInt(1);
			return totPeo;
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
	public int getLeaveNum(String date) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		int leaveNum=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT leaveNum FROM income WHERE date = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			while(rs.next())
				leaveNum = rs.getInt(1);
			return leaveNum;
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
	public int updateBookNum(String date, int bookNum) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE income SET bookNum=? WHERE date = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bookNum);
			ps.setString(2, date);
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
	public int updateLeaveNum(String date, int leaveNum) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE income SET leaveNum=? WHERE date = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, leaveNum);
			ps.setString(2, date);
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
    public int getCount() {
        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();

            String sql = "SELECT COUNT(*) FROM income";
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
    public List<Income> getIncomeByPage(int index, int pageSize, String value, int state) {
        List<Income> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (value == null) {
                String sql = "SELECT date, income, bookNum, stayNum, leaveNum FROM income LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, index);
                statement.setInt(2, pageSize);

                rs = statement.executeQuery();

                while (rs.next()) {
                    Income income = new Income();
                    income.setDate(rs.getString(1));
                    income.setIncome(rs.getDouble(2));
                    income.setBookNum(rs.getInt(3));
                    income.setStayNum(rs.getInt(4));
                    income.setLeaveNum(rs.getInt(5));
                    list.add(income);
                }
                return list;
            }
            else if (state == 0) {
                String sql = "SELECT SUM(income), SUM(bookNum), SUM(stayNum), SUM(leaveNum) FROM income WHERE date = ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, value);
            }
            else if (state == 1){
                // value 年-月 按月查询
                String sql = "SELECT SUM(income), SUM(bookNum), SUM(stayNum), SUM(leaveNum) FROM income WHERE date LIKE ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, value + "___");
            }
            else {
                // value 年 按年查询
                String sql = "SELECT SUM(income), SUM(bookNum), SUM(stayNum), SUM(leaveNum) FROM income WHERE date LIKE ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, value + "______");
            }

            rs = statement.executeQuery();

            if (rs.next()) {
                Income income = new Income();
                income.setDate(value);
                income.setIncome(rs.getDouble(1));
                income.setBookNum(rs.getInt(2));
                income.setStayNum(rs.getInt(3));
                income.setLeaveNum(rs.getInt(4));
                list.add(income);
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
    public Object[][] getIncomeRowDate(List<Income> list) {
        if (list != null && list.size() > 0) {
            Object[][] data = new Object[list.size()][5];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = list.get(i).getDate();
                data[i][1] = list.get(i).getIncome();
                data[i][2] = list.get(i).getBookNum();
                data[i][3] = list.get(i).getStayNum();
                data[i][4] = list.get(i).getLeaveNum();
            }
            return data;
        }
        return null;
    }

    @Override
    public Object[][] getIncomeRowDate(int index, int pageSize, String value, int state) {
        return getIncomeRowDate(getIncomeByPage(index, pageSize, value, state));
    }

    @Override
    public String[] getTheFirstDay() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT date FROM income WHERE id = 1";  //以id为1的地方作为存入记录的起始点  是否会有问题？
            statement = connection.prepareStatement(sql);

            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(1).split("-");
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



}