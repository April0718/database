package ContentObjects;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ConnectedDao.*;
public class TypeDAOMS extends DAOBase implements TypeDAO{
	public void input(Type type) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("请输入电影类型");
		type.setTypeName(read.nextLine());
		System.out.println("请输入电影类型的优先级：");
		type.setPri(read.nextInt());
	}
	private static final String CREATE_TYPE_SQL="insert into Types values(?,?,?)";
	private static final String GET_COUNT_ID_SQL="select count(distinct Film_ID) as countID from Types";
	private static final String IF_EXSITS_UPDATE_TYPE_SQL="select * from Types where Film_ID=? and Film_Type=?";
	private static final String DELETE_TYPE_SQL="delete from Types where Film_ID=? and Film_Type=?";
	private static final String INSERT_UPDATE_NEW_TYPE_SQL="insert into Types values(?,?,?)";
	private static final String UPDATE_TYPE_NAME_SQL="update Types set Film_Type=? where Film_ID=? and Film_Type=?";
	private static final String UPDATE_TYPE_Priority_SQL="update Types set Film_Priority=? where Film_ID=? and Film_Type=?";
	private static final String GET_FILM_TYPE_SQL="select * from Types where Film_ID=? order by Film_Priority DESC";
	
	public void insertType() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		int countID=0;
		countID=getCountId(GET_COUNT_ID_SQL);
		try {
			conn=getConnection();
			Type type=new Type();
			input(type);
			pstmt=conn.prepareStatement(CREATE_TYPE_SQL);
			pstmt.setInt(1, countID+1);
			pstmt.setString(2, type.getTypeName());
			pstmt.setInt(3, type.getPri());
			result=pstmt.executeUpdate();
			if(result>0) {
				System.out.println("成功插入第"+"result"+"行记录！");
			}
			pstmt.close();
			conn.close();
		}catch(IOException e) {
			System.out.println("数据初始化异常！");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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
	}
	
	public void updateType(int fid,String typeName) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
		Type type=null;
		int updateID=fid;
		String updateTypeName=typeName;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_TYPE_SQL);
			pstmt.setInt(1, updateID);
			pstmt.setString(2, updateTypeName);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新电影类型整体信息\t2.更新电影的类型\t3.更新电影类型的优先级");
				int choice=read.nextInt();
				switch(choice) {
				case 1:
					//先删除原来记录，然后插入新的修改后的整体信息的记录
					pstmt=conn.prepareStatement(DELETE_TYPE_SQL);
					pstmt.setInt(1, updateID);
					pstmt.setString(2, updateTypeName);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个Actor对象
						type=new Type();
						input(type);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_TYPE_SQL);
					pstmt.setInt(1,updateID);
					pstmt.setString(2, type.getTypeName());
					pstmt.setInt(3, type.getPri());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新电影类型整体信息成功！");
					}
					else {
						System.out.println("更新电影类型整体信息失败！");
					}
					pstmt.close();
					break;
				case 2:
					pstmt=conn.prepareStatement(UPDATE_TYPE_NAME_SQL);
					String new_name=null; 
					System.out.print("请输入修改后的类型名：");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateTypeName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改电影类型成功！");
					}
					else
						System.out.println("修改电影类型失败！");
						pstmt.close();
					break;
				case 3:
					pstmt=conn.prepareStatement(UPDATE_TYPE_Priority_SQL);
					int new_pri=0; 
					System.out.print("请输入修改后的电影类型优先级：");
					new_pri=read.nextInt();
					pstmt.setInt(1, new_pri);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateTypeName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改电影类型优先级！");
					}
					else {
						System.out.println("失败修改电影类型优先级！");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("出错！系统没有该电影类型记录!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
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
	}
	public void deleteType(int fid,String typeName) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_TYPE_SQL);
			pstmt.setInt(1, fid);
			pstmt.setString(2, typeName);
			result=pstmt.executeUpdate();
			if(result>0) {
				System.out.println("成功删除数据！");
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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
	}
	public List<Type> getType(int Fid){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Type> types=new ArrayList<Type>();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_FILM_TYPE_SQL);
			pstmt.setInt(1,Fid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Type type=new Type();
				type.setFid(rs.getInt(1));
				type.setTypeName(rs.getString(2));
				type.setPri(rs.getInt(3));
				types.add(type);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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
		return types;
	}
	public void print(List<Type> types) {
		int len=types.size();
		for(int i=0;i<len;i++) {
			Type type=new Type();
			type=types.get(i);
			if(i==len-1) {
				System.out.print(type.getTypeName());
			}
			else {
				System.out.print(type.getTypeName()+"/");
			}
			
		}
	}
}
