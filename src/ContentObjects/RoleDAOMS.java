package ContentObjects;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ConnectedDao.*;

public class RoleDAOMS extends DAOBase implements RoleDAO{
	
	
	public void input(Role role) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("请输入演员参演的电影编号：");
		role.setFid(read.nextInt());
		System.out.println("请输入演员饰演的角色名：");
		role.setRoleName(read.nextLine());
		System.out.println("请输入演员角色位（主角1/配角2）：");
		role.setStatus(read.nextInt());
	}
	

	private static final String GET_ACTORS="select Actor_ID from Roles where Film_ID=?";
	private static final String ACTORS_SUM="select COUNT(Actor_ID) as Actor_Count from Roles where Film_ID=?";
	private static final String CREATE_ROLE_SQL="insert into Roles values(?,?,?,?)";
	private static final String GET_COUNT_ID_SQL="select count(distinct Actor_ID) as countID from Roles";
	private static final String IF_EXSITS_UPDATE_ROLE_SQL="select * from Roles where Actor_ID=? and Film_ID=?";
	private static final String DELETE_ROLE_SQL="delete from Roles where Actor_ID=? and Film_ID=?";
	private static final String INSERT_UPDATE_NEW_ROLE_SQL="insert into Roles values(?,?,?,?)";
	private static final String UPDATE_ROLE_FID_SQL="update Roles set Film_ID=? where Actor_ID=? and Film_ID=?";
	private static final String UPDATE_ROLE_NAME_SQL="update Roles set Role_Name=? where Actor_ID=? and Film_ID=?";
	private static final String UPDATE_ROLE_STATUS_SQL="update Roles set Label_Count=? where Actor_ID=? and Film_ID=?";
	private static final String GET_ACTOR_ROLE_SQL="select * from Roles where Actor_ID=? and Film_ID=?";
	public void insertRole() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		int countID=0;
		countID=getCountId(GET_COUNT_ID_SQL);
		try {
			conn=getConnection();
			Role role=new Role();
			input(role);
			pstmt=conn.prepareStatement(CREATE_ROLE_SQL);
			pstmt.setInt(1, countID+1);
			pstmt.setInt(2, role.getFid());
			pstmt.setString(3, role.getRoleName());
			pstmt.setInt(4, role.getStatus());
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
	public void updateRole(int Aid,int Fid) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
		Role role=null;
		int updateAID=Aid;
		int updateFID=Fid;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_ROLE_SQL);
			pstmt.setInt(1, updateAID);
			pstmt.setInt(2, updateFID);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新演员参演电影的整体信息\t2.更新演员参演的电影编号\t3.更新演员演绎的角色名\t4.更新演员在电影中的角色位");
				int choice=read.nextInt();
				switch(choice) {
				case 1:
					//先删除原来记录，然后插入新的修改后的整体信息的记录
					pstmt=conn.prepareStatement(DELETE_ROLE_SQL);
					pstmt.setInt(1, updateAID);
					pstmt.setInt(2, updateFID);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个对象
						role=new Role();
						input(role);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_ROLE_SQL);
					pstmt.setInt(1,updateAID);
					pstmt.setInt(2, role.getFid());
					pstmt.setString(3, role.getRoleName());
					pstmt.setInt(4, role.getStatus());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新演员参演电影的整体信息成功！");
					}
					else {
						System.out.println("更新演员参演电影的整体信息失败！");
					}
					pstmt.close();
					break;
				case 2:
					pstmt=conn.prepareStatement(UPDATE_ROLE_FID_SQL);
					int new_fid=0; 
					System.out.print("请输入修改后的电影编号：");
					new_fid=read.nextInt();
					pstmt.setInt(1, new_fid);
					pstmt.setInt(2, updateAID);
					pstmt.setInt(3, updateFID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改演员参演的电影编号成功！");
					}
					else
						System.out.println("修改演员参演的电影编号失败！");
						pstmt.close();
					break;
				case 3:
					pstmt=conn.prepareStatement(UPDATE_ROLE_NAME_SQL);
					String new_roleName=null; 
					System.out.print("请输入修改后的演员演绎的角色名：");
					new_roleName=read.nextLine();
					pstmt.setString(1, new_roleName);
					pstmt.setInt(2, updateAID);
					pstmt.setInt(3, updateFID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改演员演绎的角色名！");
					}
					else {
						System.out.println("失败修改演员演绎的角色名！");
					}
					pstmt.close();
					break;
				case 4:
					pstmt=conn.prepareStatement(UPDATE_ROLE_STATUS_SQL);
					String new_status=null; 
					System.out.print("请输入修改后的演员在电影中的角色位：");
					new_status=read.nextLine();
					pstmt.setString(1, new_status);
					pstmt.setInt(2, updateAID);
					pstmt.setInt(3, updateFID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改演员在电影中的角色位！");
					}
					else {
						System.out.println("失败修改演员在电影中的角色位！");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("出错！系统没有该电影标签记录!");
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
	public void deleteRole(int Aid,int Fid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_ROLE_SQL);
			pstmt.setInt(1, Aid);
			pstmt.setInt(2, Fid);
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
	public int[] getActorsID(int Fid) {
		Connection conn=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		int[] IDs=null;
		int num=0;//定义一部电影的演员数量
		int n=0;
		try {
			//获得一部电影参演的所有演员ID
			conn=getConnection();
			pstmt1=conn.prepareStatement(GET_ACTORS);
			pstmt1.setInt(1, Fid);
			rs1=pstmt1.executeQuery();
			
			//获得一部电影参演的演员数量
			pstmt2=conn.prepareStatement(ACTORS_SUM);
			pstmt2.setInt(1, Fid);
			rs2=pstmt2.executeQuery();
			while(rs2.next()) {
				num=rs2.getInt("Actor_Count");
			}
			//给演员ID数组赋值
			IDs=new int[num];
			while(rs1.next()) {
				IDs[n]=rs1.getInt(1);
				n++;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt1!=null&&pstmt2!=null) {
				try {
					pstmt1.close();
					pstmt2.close();
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
		return IDs;
	}
	public Role getRole(int Aid,int Fid){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Role role=new Role();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_ACTOR_ROLE_SQL);
			pstmt.setInt(1,Aid);
			pstmt.setInt(2, Fid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				role.setAid(rs.getInt(1));
				role.setFid(rs.getInt(2));
				role.setRoleName(rs.getString(3));
				role.setStatus(rs.getInt(4));
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
		return role;
	}
	public void print(List<Role> roles) {
		
	}
}
