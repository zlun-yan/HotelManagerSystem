package com.csu.dao;

import com.csu.bean.Clerk;
import com.csu.bean.Product;
import com.csu.dbUtil.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClerkImpl implements ClerkDao{
    @Override
    public int getCount() {
        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();

            String sql = "SELECT COUNT(id) FROM clerk";
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
    public List<Clerk> getClerkByPageWithName(int index, int pageSize, String value) {
        List<Clerk> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (value == null) {
                String sql = "SELECT * FROM clerk LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, index);
                statement.setInt(2, pageSize);
            }
            else {
                String sql = "SELECT * FROM clerk WHERE name LIKE ? LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, "%" + value + "%");
                statement.setInt(2, index);
                statement.setInt(3, pageSize);
            }

            rs = statement.executeQuery();

            while (rs.next()) {
                Clerk clerk = new Clerk();
                clerk.setId(rs.getInt(1));
                clerk.setName(rs.getString(2));
                clerk.setJob(rs.getString(3));
                clerk.setJid(rs.getString(4));
                clerk.setPassword(rs.getString(5));
                list.add(clerk);
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
    public List<Clerk> getClerkByPageWithJob(int index, int pageSize, String value) {
        List<Clerk> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (value == null) {
                String sql = "SELECT * FROM clerk LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, index);
                statement.setInt(2, pageSize);
            }
            else {
                String sql = "SELECT * FROM clerk WHERE job LIKE ? LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, "%" + value + "%");
                statement.setInt(2, index);
                statement.setInt(3, pageSize);
            }

            rs = statement.executeQuery();

            while (rs.next()) {
                Clerk clerk = new Clerk();
                clerk.setId(rs.getInt(1));
                clerk.setName(rs.getString(2));
                clerk.setJob(rs.getString(3));
                clerk.setJid(rs.getString(4));
                clerk.setPassword(rs.getString(5));
                list.add(clerk);
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
    public List<Clerk> getClerkByPageWithJid(int index, int pageSize, String value) {
        List<Clerk> list = new ArrayList<>();

        Connection connection = null;   //连接对象
        PreparedStatement statement = null;   //SQL语句的预编译对象

        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (value == null) {
                String sql = "SELECT * FROM clerk LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setInt(1, index);
                statement.setInt(2, pageSize);
            }
            else {
                String sql = "SELECT * FROM clerk WHERE jid = ? LIMIT ?, ?";
                statement = connection.prepareStatement(sql);

                statement.setString(1, value);
                statement.setInt(2, index);
                statement.setInt(3, pageSize);
            }

            rs = statement.executeQuery();

            while (rs.next()) {
                Clerk clerk = new Clerk();
                clerk.setId(rs.getInt(1));
                clerk.setName(rs.getString(2));
                clerk.setJob(rs.getString(3));
                clerk.setJid(rs.getString(4));
                clerk.setPassword(rs.getString(5));
                list.add(clerk);
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
    public Object[][] getClerkRowDate(List<Clerk> list) {
        if (list != null && list.size() > 0) {
            Object[][] data = new Object[list.size()][5];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = list.get(i).getId();
                data[i][1] = list.get(i).getName();
                data[i][2] = list.get(i).getJob();
                data[i][3] = list.get(i).getJid();
                data[i][4] = list.get(i).getPassword();
            }
            return data;
        }
        return null;
    }

    @Override
    public Object[][] getClerkRowDateWithName(int index, int pageSize, String value) {
        return getClerkRowDate(getClerkByPageWithName(index, pageSize, value));
    }

    @Override
    public Object[][] getClerkRowDateWithJob(int index, int pageSize, String value) {
        return getClerkRowDate(getClerkByPageWithJob(index, pageSize, value));
    }

    @Override
    public Object[][] getClerkRowDateWithJid(int index, int pageSize, String value) {
        return getClerkRowDate(getClerkByPageWithJid(index, pageSize, value));
    }

    @Override
    public int insertClerk(String name, String job) {
        Connection connection = DBUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO clerk(name, job) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, job);

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            String sql = "SELECT MAX(id) FROM clerk";
            statement = connection.prepareStatement(sql);

            rs = statement.executeQuery();
            if (rs.next()) {
                String jid = "";
                // jid  加上日期
                String[] dates = DBUtils.getDateString().split("-");
                for (String s : dates) {
                    jid += s;
                }

                //jid  加上职位标识
                if (job.equals("waiter")) {
                    jid += "03";
                }
                else if (job.equals("manager")) {
                    jid += "02";
                }
                else {
                    jid += "01";
                }

                //jid  加上id % 100
                jid += String.format("%02d", rs.getInt(1) % 100);

                String sql2 = "UPDATE clerk SET jid = ?, password = ? WHERE id = ?";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, jid);
                statement.setString(2, jid);
                statement.setInt(3, rs.getInt(1));

                return statement.executeUpdate();
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
    public int updateJobWithJid(String jid, String job) {
        Connection connection = DBUtils.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "UPDATE clerk SET job = ? WHERE jid = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, job);
            statement.setString(2, jid);

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
    public int deleteClerkWithJid(String jid) {
        Connection connection = DBUtils.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "DELETE FROM clerk WHERE jid = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, jid);

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
    public List<Clerk> getClerkByJid(String jid) {
        // TODO 自动生成的方法存根

        List<Clerk> list = new ArrayList<>();
        Connection conn = null;//接连对象
        PreparedStatement ps = null;//sql语句的预编译对象
        ResultSet rs = null;

        conn = DBUtils.getConnection();
        //4、创建PreparedStatement对象
        try {
            String sql = "SELECT * FROM clerk WHERE jid = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, jid);
            //5、执行
            rs = ps.executeQuery();
            //6、解析查询结果集
            while(rs.next()){
                Clerk clerk = new Clerk();
                clerk.setId(rs.getInt(1));//取第一个字段的值\
                clerk.setName(rs.getString(2));
                clerk.setJob(rs.getString(3));
                clerk.setJid(rs.getString(4));
                clerk.setPassword(rs.getString(5));
                list.add(clerk);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                DBUtils.close(rs, ps, conn);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int updateClerk(String id, String name, String job, String pwd) {
        // TODO 自动生成的方法存根
        Connection conn = null;//接连对象
        PreparedStatement ps = null;//sql语句的预编译对象
        conn = DBUtils.getConnection();
        //4、创建PreparedStatement对象
        try {
            String sql = "UPDATE clerk SET name=?,job=?,password=? WHERE id =?";
            ps = conn.prepareStatement(sql);
            //5、执行
            ps.setString(1, name);
            ps.setString(2, job);
            ps.setString(3, pwd);
            ps.setString(4, id);
            return ps.executeUpdate();
            //6、解析查询结果集

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                DBUtils.close(null, ps, conn);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void updateName(String id, String name) {
        // TODO 自动生成的方法存根
        Connection conn = null;//接连对象
        PreparedStatement ps = null;//sql语句的预编译对象
        conn = DBUtils.getConnection();
        //4、创建PreparedStatement对象
        try {
            String sql = "UPDATE clerk SET name=? WHERE id =?";
            ps = conn.prepareStatement(sql);
            //5、执行
            ps.setString(1, name);
            ps.setString(2, id);
            //6、解析查询结果集
            ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                DBUtils.close(null, ps, conn);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void updatePwd(String id, String password) {
        // TODO 自动生成的方法存根
        Connection conn = null;//接连对象
        PreparedStatement ps = null;//sql语句的预编译对象
        conn = DBUtils.getConnection();
        //4、创建PreparedStatement对象
        try {
            String sql = "UPDATE clerk SET password=? WHERE id =?";
            ps = conn.prepareStatement(sql);
            //5、执行
            ps.setString(1, password);
            ps.setString(2, id);
            //6、解析查询结果集
            ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                DBUtils.close(null, ps, conn);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
