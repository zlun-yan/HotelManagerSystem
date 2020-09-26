package com.csu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.csu.dbUtil.DBUtils;

public class UserDaoImpl implements UserDao{

	@Override
	public String isClerk(String userName,String password) {
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = DBUtils.getConnection();
		String sql = "SELECT job FROM clerk WHERE jid = ? && password = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getString(1);
			else {
				return null;
			}
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {

			try {
				DBUtils.close(null, ps, conn);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		
		return null;
		
	}
}
