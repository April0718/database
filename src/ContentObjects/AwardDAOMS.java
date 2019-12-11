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
public class AwardDAOMS extends DAOBase implements AwardDAO{
	public void input(Award award) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("请输入电影的编号：");
		award.setFid(read.nextInt());
		System.out.println("请输入奖项名：");
		award.setAwname(read.nextLine());
		System.out.println("请输入颁奖日期：");
		award.setAwtime(read.nextLine());
	}
	private static final String CREATE_AWARD_SQL="insert into Film_Awards values(?,?,?,?)";
	private static final String IF_EXSITS_UPDATE_AWARD_SQL="select * from Film_Awards where Award=?";
	private static final String DELETE_AWARD_SQL="delete from Film_Awards where Award=?";
	private static final String INSERT_UPDATE_NEW_AWARD_SQL="insert into Film_Awards values(?,?,?)";
	private static final String UPDATE_AWARD_FID_SQL="update Film_Awards set Fid=? where Award=?";
	private static final String UPDATE_AWARD_NAME_SQL="update Film_Awards set Award=? where Award=?";
	private static final String UPDATE_AWARD_TIME_SQL="update Film_Awards set Award_Time=? where Award=?";
	private static final String GET_AWARD_SQL="select * from Film_Awards where Film_ID=?";
	//插入电影奖项记录
	public void insertAward() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			Award award=new Award();
			input(award);
			pstmt=conn.prepareStatement(CREATE_AWARD_SQL);
			pstmt.setInt(1, award.getFid());
			pstmt.setString(2, award.getAwname());
			pstmt.setString(3, award.getAwtime());
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
	//更新演员信息
	public void updateAward(String Awname) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
		Award award=null;
		String updateName=Awname;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_AWARD_SQL);
			pstmt.setString(1, updateName);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新奖项整体信息\t2.更新电影编号\t3.更新奖项名\t4.更新颁奖时间");
				int choice=read.nextInt();
				switch(choice) {
				case 1://更新奖项整体信息
					//先删除原来记录，然后插入新的修改后的整体信息的记录
					pstmt=conn.prepareStatement(DELETE_AWARD_SQL);
					pstmt.setString(1, updateName);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个Actor对象
						award=new Award();
						input(award);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_AWARD_SQL);
					pstmt.setInt(1,award.getFid());
					pstmt.setString(2, award.getAwname());
					pstmt.setString(3, award.getAwtime());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新奖项整体信息成功！");
					}
					else {
						System.out.println("更新奖项整体信息失败！");
					}
					pstmt.close();
					break;
				case 2://更新演员参演的电影编号
					pstmt=conn.prepareStatement(UPDATE_AWARD_FID_SQL);
					int new_Fid=0; 
					System.out.print("请输入修改后的电影编号：");
					new_Fid=read.nextInt();
					pstmt.setInt(1, new_Fid);
					pstmt.setString(2, updateName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改获得奖项名为："+updateName+"的电影号成功！");
					}
					else
						System.out.println("修改获得奖项名为："+updateName+"的电影号失败！");
						pstmt.close();
					break;
				case 3://更新奖项名
					pstmt=conn.prepareStatement(UPDATE_AWARD_NAME_SQL);
					String new_Awname=null; 
					System.out.print("请输入修改后的奖项名：");
					new_Awname=read.nextLine();
					pstmt.setString(1, new_Awname);
					pstmt.setString(2, updateName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改原奖项："+updateName+"为"+new_Awname);
					}
					else {
						System.out.println("失败修改原奖项："+updateName+"为"+new_Awname);
					}
					pstmt.close();
					break;
				case 4://更新奖项颁奖时间
					pstmt=conn.prepareStatement(UPDATE_AWARD_TIME_SQL);
					String new_Awtime=null; 
					System.out.print("请输入修改后的奖项颁奖时间：");
					new_Awtime=read.nextLine();
					pstmt.setString(1, new_Awtime);
					pstmt.setString(2, updateName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改奖项："+updateName+"的颁奖时间为"+new_Awtime);
					}
					else {
						System.out.println("失败修改奖项："+updateName+"的颁奖时间为"+new_Awtime);
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("出错！系统没有该奖项记录!");
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
	//根据奖项名删除奖项记录
	public void deleteAward(String Awname) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_AWARD_SQL);
			pstmt.setString(1, Awname);
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
	//根据电影ID查询电影获得的奖项
	public List<Award> getAward(int Fid){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Award> awards=new ArrayList<Award>();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_AWARD_SQL);
			pstmt.setInt(1,Fid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Award award=new Award();
				award.setFid(rs.getInt(1));
				award.setAwname(rs.getString(2));
				award.setAwtime(rs.getString(3));
				awards.add(award);
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
		return awards;
	}
	//奖项输出
	public void print(List<Award> FAws) {
		Award award=null;
		for(int i=0;i<FAws.size();i++) {
			award=new Award();
			award=FAws.get(i);
			System.out.println("奖项名："+award.getAwname()+"  颁于"+award.getAwtime()+"年");
		}
		System.out.println("--------------------------------------");
	}
}
