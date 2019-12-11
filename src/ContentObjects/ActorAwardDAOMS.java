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
public class ActorAwardDAOMS extends DAOBase implements ActorAwardDAO{
	public void input(ActorAward actoraward) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("请输入所获奖项名：");
		actoraward.setAward(read.nextLine());
		System.out.println("请输入获奖日期：");
		actoraward.setAcAwtime(read.nextLine());
	}
	private static final String CREATE_ActorAward_SQL="insert into Actor_Awards values(?,?,?)";
	private static final String GET_COUNT_ID_SQL="select COUNT(Actor_ID) as countID from Actor_Awards";
	private static final String IF_EXSITS_UPDATE_ActorAward_SQL="select * from Actor_Awards where Actor_ID=?";
	private static final String DELETE_ActorAward_SQL="delete from Actor_Awards where Actor_ID=?";
	private static final String INSERT_UPDATE_NEW_AWARD_SQL="insert into Actor_Awards values(?,?,?)";
	private static final String UPDATE_ActorAward_NAME_SQL="update Actor_Awards set Award=? where Actor_ID=?";
	private static final String UPDATE_ActorAward_TIME_SQL="update Actor_Awards set Award_Time=? where Actor_ID=?";
	private static final String GET_ActorAward_SQL="select * from Actor_Awards where Actor_ID=?";
	public void insertActorAward() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		int countID=0;
		countID=getCountId(GET_COUNT_ID_SQL);
		try {
			conn=getConnection();
			ActorAward actoraward=new ActorAward();
			input(actoraward);
			pstmt=conn.prepareStatement(CREATE_ActorAward_SQL);
			pstmt.setInt(1, countID+1);
			pstmt.setString(2, actoraward.getAward());
			pstmt.setString(3, actoraward.getAcAwtime());
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
	
	public void updateActorAward(int Aid) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
		ActorAward actoraward=null;
		int updateID=Aid;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_ActorAward_SQL);
			pstmt.setInt(1, updateID);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新演员获奖整体信息\t2.更新演员获得的奖项名\t3.更新颁奖时间");
				int choice=read.nextInt();
				switch(choice) {
				case 1://更新演员获奖整体信息
					//先删除原来记录，然后插入新的修改后的整体信息的记录
					pstmt=conn.prepareStatement(DELETE_ActorAward_SQL);
					pstmt.setInt(1, updateID);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个Actor对象
						actoraward=new ActorAward();
						input(actoraward);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_AWARD_SQL);
					pstmt.setInt(1,updateID);
					pstmt.setString(2, actoraward.getAward());
					pstmt.setString(3, actoraward.getAcAwtime());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新演员获奖整体信息成功！");
					}
					else {
						System.out.println("更新演员获奖整体信息失败！");
					}
					pstmt.close();
					break;
				case 2://更新演员获得的奖项名
					pstmt=conn.prepareStatement(UPDATE_ActorAward_NAME_SQL);
					String new_name=null; 
					System.out.print("请输入修改后的奖项名：");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改获得奖项名成功！");
					}
					else
						System.out.println("修改获得奖项名失败！");
						pstmt.close();
					break;
				case 3://更新颁奖时间
					pstmt=conn.prepareStatement(UPDATE_ActorAward_TIME_SQL);
					String new_time=null; 
					System.out.print("请输入修改后的颁奖时间：");
					new_time=read.nextLine();
					pstmt.setString(1, new_time);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改演员获奖时间！");
					}
					else {
						System.out.println("失败修改演员获奖时间！");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("出错！系统没有该演员获奖记录!");
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
	public void deleteActorAward(int Aid) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_ActorAward_SQL);
			pstmt.setInt(1, Aid);
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
	public List<ActorAward> getActorAward(int Aid){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<ActorAward> actorawards=new ArrayList<ActorAward>();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_ActorAward_SQL);
			pstmt.setInt(1,Aid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				ActorAward actoraward=new ActorAward();
				actoraward.setAid(rs.getInt(1));
				actoraward.setAward(rs.getString(2));
				actoraward.setAcAwtime(rs.getString(3));
				actorawards.add(actoraward);
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
		return actorawards;
	}
	public void print(List<ActorAward> actorawards) {
		System.out.println("---------------该演员所有获奖信息-----------------");
		for(int i=0;i<actorawards.size();i++) {
			ActorAward acAward=new ActorAward();
			acAward=actorawards.get(i);
			System.out.println("奖项名："+acAward.getAward()+" 颁于"+acAward.getAcAwtime()+"年");
		}
		System.out.println("---------------------------------------");
	}
	
}
