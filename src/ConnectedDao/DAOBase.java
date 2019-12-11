package ConnectedDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;;

//每一个DAO对象都有一个连接方法，抽象为DAOBase连接基类
public class DAOBase implements DAO{
	public  Connection getConnection() {
		Connection con=null;
		String dirveName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url="jdbc:sqlserver://192.168.56.6:1433;databaseName=Douban_Film_System";
		String username="yuan";
		String password="123";
		try {
			Class.forName(dirveName);
			con=DriverManager.getConnection(url,username,password);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public int getCountId(String sql) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int countId=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				countId=(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return countId;
	}
}
