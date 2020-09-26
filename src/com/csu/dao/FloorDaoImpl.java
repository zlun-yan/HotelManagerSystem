package com.csu.dao;

import com.csu.bean.Floor;
import com.csu.dbUtil.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.csu.dbUtil.DBUtils.close;

public class FloorDaoImpl implements FloorDao {
    public int addFloor(int floor, int roomCount){
        Connection conn = null;//连接对象
        PreparedStatement ps = null;//sql语句的预编译对象（存sql语句的）
        try {
            conn = DBUtils.getConnection();

            //4.创建PreparedStatement对象
            String sql = "INSERT INTO floor(floor,roomNum) VALUES(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,floor);
            ps.setInt(2,roomCount);

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

    public int changeFloor(int floor, int roomCount){
        Connection conn = null;//连接对象
        PreparedStatement ps = null;//sql语句的预编译对象（存sql语句的）
        try {
            conn = DBUtils.getConnection();

            //4.创建PreparedStatement对象
            String sql = "UPDATE floor SET roomNum = ? WHERE floor = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,roomCount);
            ps.setInt(2,floor);

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

    public int deleteFloor(int floor){
        Connection conn = null;//连接对象
        PreparedStatement ps = null;//sql语句的预编译对象（存sql语句的）
        try {
            conn = DBUtils.getConnection();

            //4.创建PreparedStatement对象
            String sql = "DELETE FROM floor WHERE floor = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,floor);

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

    public List<Floor> getFloorByPage(int index, int pageSize){
        List<Floor> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();

            String sql;
            sql = "SELECT * FROM floor LIMIT ?,?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, index);
            statement.setInt(2, pageSize);

            rs = statement.executeQuery();

            while (rs.next()) {
                Floor floor = new Floor();

                floor.setFloor(rs.getInt(1));
                floor.setLastRoom(rs.getInt(2));
                list.add(floor);
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

    public int getCount(){
        Connection connection = DBUtils.getConnection();
        String sql = "SELECT COUNT(*) FROM floor";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
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
                close(rs, statement, connection);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return 0;
    }
}
