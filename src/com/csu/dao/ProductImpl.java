package com.csu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.csu.bean.Product;
import com.csu.dbUtil.DBUtils;
import com.mysql.fabric.xmlrpc.base.Array;

public class ProductImpl implements ProductDao {

	@Override
		public List<String> getNames() {
		List<String> list = new ArrayList();
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		ResultSet rs =null;
		
		
		try {
			String sql = "SELECT * FROM product";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString(2);
				list.add(name);
			}
			return list;
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
		
		return null;
	}

	@Override
	public double getPrice(String name) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		ResultSet rs =null;
		
		conn = DBUtils.getConnection();
		
		try {
			String sql = "SELECT price FROM product WHERE name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,name);
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
	public int minusProduct(String name, int num) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对象
		conn = DBUtils.getConnection();
		
		
		try {
			String sql = "UPDATE product SET lastNum=? WHERE name = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, name);
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
	public int getProductNum(String name) {
		Connection conn = null;// 连接对象
		PreparedStatement ps = null; // sql语句的编译对
		ResultSet rs =null;
		int num=0;
		
		conn = DBUtils.getConnection();
		try {
			String sql = "SELECT lastNum FROM product WHERE name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while(rs.next())
				num = rs.getInt(1);
			return num;
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
	    public int getCount() {
	        Connection connection = null;
	        PreparedStatement statement = null;

	        ResultSet rs = null;

	        try {
	            connection = DBUtils.getConnection();

	            String sql = "SELECT COUNT(*) FROM product";
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
	    public List<Product> getProductByPage(int index, int pageSize, String value) {
	        List<Product> list = new ArrayList<>();

	        Connection connection = null;   //连接对象
	        PreparedStatement statement = null;   //SQL语句的预编译对象

	        ResultSet rs = null;

	        try {
	            connection = DBUtils.getConnection();
	            if (value == null) {
	                String sql = "SELECT * FROM product LIMIT ?, ?";
	                statement = connection.prepareStatement(sql);

	                statement.setInt(1, index);
	                statement.setInt(2, pageSize);
	            }
	            else {
	                String sql = "SELECT * FROM product WHERE name LIKE ? LIMIT ?, ?";
	                statement = connection.prepareStatement(sql);

	                statement.setString(1, "%" + value + "%");
	                statement.setInt(2, index);
	                statement.setInt(3, pageSize);
	            }

	            rs = statement.executeQuery();

	            while (rs.next()) {
	                Product product = new Product();

	                product.setProductId(rs.getInt(1));
	                product.setProductName(rs.getString(2));
	                product.setPrice(rs.getDouble(3));
	                product.setLastNum(rs.getInt(4));

	                list.add(product);
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
	    public Object[][] getProductRowDate(List<Product> list) {
	        if (list != null && list.size() > 0) {
	            Object[][] data = new Object[list.size()][4];
	            for (int i = 0; i < list.size(); i++) {
	                data[i][0] = list.get(i).getProductId();
	                data[i][1] = list.get(i).getProductName();
	                data[i][2] = list.get(i).getPrice();
	                data[i][3] = list.get(i).getLastNum();
	            }
	            return data;
	        }
	        return null;
	    }

	    @Override
	    public Object[][] getProductRowDate(int index, int pageSize, String value) {
	        return getProductRowDate(getProductByPage(index, pageSize, value));
	    }

	    @Override
	    public int queryProductByName(String name) {
	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;
	        ResultSet rs = null;

	        try {
	            String sql = "SELECT COUNT(id) FROM product WHERE name = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, name);

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

	        return 2;
	    }

	    @Override
	    public int queryProductById(int id) {
	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;
	        ResultSet rs = null;

	        try {
	            String sql = "SELECT COUNT(id) FROM product WHERE id = ?";
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

	        return 2;
	    }

	    @Override
	    public int insertProductByName(String name, double price, int num) {
	        //首先判断名叫 name  的商品在表中是否存在
	        if (queryProductByName(name) != 0) return 0;

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;

	        try {
	            String sql = "INSERT INTO product VALUES (null, ?, ?, ?)";
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, name);
	            statement.setDouble(2, price);
	            statement.setInt(3, num);
	            System.out.println(name + "  " + price + "  " + num);

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
	    public int updateProductWithName(String preName, String newName) {
	        //首先判断名叫 name  的商品在表中是否存在
	        if (queryProductByName(preName) != 1) return 0;

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;

	        try {
	            String sql = "UPDATE product SET name = ? WHERE name = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, newName);
	            statement.setString(2, preName);

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
	    public int updateProductWithPrice(String productName, double price) {
	        //首先判断名叫 name  的商品在表中是否存在
	        if (queryProductByName(productName) != 1) return 0;

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;

	        try {
	            String sql = "UPDATE product SET price = ? WHERE name = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setDouble(1, price);
	            statement.setString(2, productName);

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
	    public int updateProductWithNum(int id, int changeNum) {
	        //首先判断名叫 name  的商品在表中是否存在
	        if (queryProductById(id) != 1) return 0;

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;

	        try {
	            String sql = "UPDATE product SET lastNum = lastNum + ? WHERE id = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setInt(1, changeNum);
	            statement.setInt(2, id);

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
	    public int deleteProductByName(String productName) {
	        //首先判断名叫 name  的商品在表中是否存在
	        if (queryProductByName(productName) != 1) {
	            return 0;  //在这里最好加一个对话框 提示商品信息  需要再次确认
	        }

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;

	        try {
	            String sql = "DELETE FROM product WHERE name = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, productName);

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
	    public int deleteProductById(int id) {
	        //首先判断名叫 name  的商品在表中是否存在
	        if (queryProductById(id) != 1) {
	            return 0;  //在这里最好加一个对话框 提示商品信息  需要再次确认
	        }

	        Connection connection = DBUtils.getConnection();
	        PreparedStatement statement = null;

	        try {
	            String sql = "DELETE FROM product WHERE id = ?";
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

}
