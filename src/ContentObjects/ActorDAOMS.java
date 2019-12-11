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

public class ActorDAOMS extends DAOBase implements ActorDAO{
	public void input(Actor actor) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("请输入演员的名字：");
		actor.setActor_Name(read.nextLine());
		System.out.println("请输入演员的性别：");
		actor.setActor_Sex(read.nextLine());
		System.out.println("请输入演员的星座：");
		actor.setActor_Constellation(read.nextLine());
		System.out.println("请输入演员的出生日期：");
		actor.setActor_Birth(read.nextLine());
		System.out.println("请输入演员的出生地：");
		actor.setActor_BirthPlace(read.nextLine());
		System.out.println("请输入演员的职业：");
		actor.setProfession(read.nextLine());
		System.out.println("请输入演员的外文名：");
		actor.setOther_ForeignName(read.nextLine());
		System.out.println("请输入演员的中文名：");
		actor.setOther_ChineseName(read.nextLine());
		System.out.println("请输入演员的IMDB编号：");
		actor.setImdb_number(read.nextLine());
		System.out.println("请输入演员的官方网站：");
		actor.setOffical_Website(read.nextLine());
		System.out.println("请输入演员的简介：");
		actor.setActor_Intro(read.nextLine());
		System.out.println("请输入演员的照片链接地址：");
		actor.setActor_Photo(read.nextLine());
	}
	
	private static final String CREATE_ACTOR_SQL="insert into Actors values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ACTOR_ID_COUNT_SQL="select count(Actor_ID) as countId form Actors";
	private static final String DELETE_ACTOR_SQL="delete from Actors where Actor_ID=?";
	private static final String Get_ACTOR_SQL="select * from Actors where Actor_ID=?";
	//update
	private static final String IF_EXISTS_UPDATE_ACTOR_SQL="select * from Actors where Actor_ID=?";
	private static final String DELETE_UPDATE_OLD_ACTOR_SQL="delete from Actors where Actor_ID=?";
	private static final String INSERT_UPDATE_NEW_ACTOR_SQL="insert into Actors values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_ACTOR_Name_SQL="update Actors set Actor_Name=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Sex_SQL="update Actors set Actor_Sex=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Constellation_SQL="update Actors set Actor_Constellation=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Birth_SQL="update Actors set Actor_Birth=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Birthplace_SQL="update Actors set Actor_BirthPlace=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Profession_SQL="update Actors set Profession=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_ForeignName_SQL="update Actors set Other_ForeignName=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_ChineseName_SQL="update Actors set Other_ChineseName=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Imdb_SQL="update Actors set imdb_number=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_OffiWeb_SQL="update Actors set Offical_Website=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Intro_SQL="update Actors set Actor_Intro=? where Actor_ID=?";
	private static final String UPDATE_ACTOR_Photo_SQL="update Actors set Actor_Photo=? where Actor_ID=?";
	
	
	//插入新建演员记录
	public void insertActor() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		int countId=getCountId(GET_ACTOR_ID_COUNT_SQL);
		try {
			conn=getConnection();
			Actor actor=new Actor();
			input(actor);
			pstmt=conn.prepareStatement(CREATE_ACTOR_SQL);
			pstmt.setInt(1, countId+1);
			pstmt.setString(2, actor.getActor_Name());
			pstmt.setString(3, actor.getActor_Sex());
			pstmt.setString(4, actor.getActor_Constellation());
			pstmt.setString(5, actor.getActor_Birth());
			pstmt.setString(6, actor.getActor_BirthPlace());
			pstmt.setString(7, actor.getProfession());
			pstmt.setString(8,actor.getOther_ForeignName());
			pstmt.setString(9, actor.getOther_ChineseName());
			pstmt.setString(10, actor.getImdb_number());
			pstmt.setString(11, actor.getOffical_Website());
			pstmt.setString(12, actor.getActor_Intro());
			pstmt.setString(13, actor.getActor_Photo());
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
	public void updateActor(int Actor_ID) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
		Actor actor=null;
		int updateID=Actor_ID;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXISTS_UPDATE_ACTOR_SQL);
			pstmt.setInt(1, updateID);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新演员整体信息\t2.更新演员姓名\t3.更新演员性别\t4.更新演员星座\t5.更新演员出生日期\t6.更新演员出生日期");
				System.out.println("------------7.更新演员职业\t8.更新演员外文名\t9.更新演员中文名\t10.更新演员IMDB\t11.更新演员官方网站\t12.更新演员简介\t13.更新演员照片-----");
				int choice=read.nextInt();
				switch(choice) {
				case 1://更新整体信息
					//先删除原来记录，然后插入新的修改完整体信息的记录
					pstmt=conn.prepareStatement(DELETE_UPDATE_OLD_ACTOR_SQL);
					pstmt.setInt(1, updateID);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个Actor对象
						actor=new Actor();
						input(actor);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_ACTOR_SQL);
					pstmt.setInt(1, updateID);
					pstmt.setString(2, actor.getActor_Name());
					pstmt.setString(3, actor.getActor_Sex());
					pstmt.setString(4, actor.getActor_Constellation());
					pstmt.setString(5, actor.getActor_Birth());
					pstmt.setString(6, actor.getActor_BirthPlace());
					pstmt.setString(7, actor.getProfession());
					pstmt.setString(8,actor.getOther_ForeignName());
					pstmt.setString(9, actor.getOther_ChineseName());
					pstmt.setString(10, actor.getImdb_number());
					pstmt.setString(11, actor.getOffical_Website());
					pstmt.setString(12, actor.getActor_Intro());
					pstmt.setString(13, actor.getActor_Photo());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新编号为："+updateID+"的演员整体信息成功！");
					}
					else {
						System.out.println("更新编号为："+updateID+"的演员整体信息失败！");
					}
					pstmt.close();
					break;
				case 2://更新演员姓名
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Name_SQL);
					String new_name=null;
					System.out.print("请输入修改后的演员姓名：");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员姓名成功！");
					}
					else
						System.out.println("修改编号为："+updateID+"的演员姓名失败！");
						pstmt.close();
					break;
				case 3://更新演员的性别
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Sex_SQL);
					String new_Aname=null; 
					System.out.print("请输入修改后的演员姓名：");
					new_Aname=read.nextLine();
					pstmt.setString(1, new_Aname);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员的姓名成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员的姓名失败！");
					}
					pstmt.close();
					break;
				case 4://更新演员星座
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Constellation_SQL);
					String new_cons; 
					System.out.print("请输入修改后的演员星座：");
					new_cons=read.nextLine();
					pstmt.setString(1, new_cons);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员星座成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员星座失败！");
					}
					pstmt.close();
					break;
				case 5://更新演员出生日期
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Birth_SQL);
					String new_date; 
					System.out.print("请输入修改后的演员出生日期：");
					new_date=read.nextLine();
					pstmt.setString(1, new_date);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员出生日期成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员出生日期失败！");
					}
					pstmt.close();
					break;
				case 6://更新演员出生地
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Birthplace_SQL);
					String new_place; 
					System.out.print("请输入修改后的演员出生地：");
					new_place=read.nextLine();
					pstmt.setString(1, new_place);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员出生地成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员出生地失败！");
					}
					pstmt.close();
					break;
				case 7://更新演员的职业
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Profession_SQL);
					String new_prof; 
					System.out.print("请输入修改后的演员职业：");
					new_prof=read.nextLine();
					pstmt.setString(1, new_prof);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员职业成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员职业失败！");
					}
					pstmt.close();
					break;
				case 8://更新演员外文名
					pstmt=conn.prepareStatement(UPDATE_ACTOR_ForeignName_SQL);
					String new_fn; 
					System.out.print("请输入修改后的演员外文名：");
					new_fn=read.nextLine();
					pstmt.setString(1, new_fn);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员外文名成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员外文名失败！");
					}
					pstmt.close();
					break;
				case 9://更新演员的中文名
					pstmt=conn.prepareStatement(UPDATE_ACTOR_ChineseName_SQL);
					String new_cn; 
					System.out.print("请输入修改后的演员中文名：");
					new_cn=read.nextLine();
					pstmt.setString(1, new_cn);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员中文名成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员中文名失败！");
					}
					pstmt.close();
					break;
				case 10://更新演员的IMDB
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Imdb_SQL);
					String new_IMDB; 
					System.out.print("请输入修改后的IMDB：");
					new_IMDB=read.nextLine();
					pstmt.setString(1, new_IMDB);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员IMDB成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员IMDB失败！");
					}
					pstmt.close();
					break;
				case 11://更新演员的官方网站
					pstmt=conn.prepareStatement(UPDATE_ACTOR_OffiWeb_SQL);
					String new_web; 
					System.out.print("请输入修改后的演员官方网站：");
					new_web=read.nextLine();
					pstmt.setString(1, new_web);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员官方网站成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员官方网站失败！");
					}
					pstmt.close();
					break;
				case 12://更新演员的简介
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Intro_SQL);
					String new_intro; 
					System.out.print("请输入修改后的演员简介：");
					new_intro=read.nextLine();
					pstmt.setString(1, new_intro);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员简介成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员简介失败！");
					}
					pstmt.close();
					break;
				case 13://更新演员的照片
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Photo_SQL);
					String new_photo; 
					System.out.print("请输入修改后的演员照片链接：");
					new_photo=read.nextLine();
					pstmt.setString(1, new_photo);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改编号为："+updateID+"的演员照片成功！");
					}
					else {
						System.out.println("修改编号为："+updateID+"的演员照片失败！");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("出错！系统没有该演员记录!");
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
	//依据演员id删除记录
	public void deleteActor(int Actor_ID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_ACTOR_SQL);
			pstmt.setInt(1, Actor_ID);
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
	
	//依据Actor_ID查询一个演员全部信息
	public Actor getActor(int Actor_ID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Actor actor=new Actor();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(Get_ACTOR_SQL);
			pstmt.setInt(1,Actor_ID);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				actor.setActor_ID(rs.getInt(1));
				actor.setActor_Name(rs.getString(2));
				actor.setActor_Sex(rs.getString(3));
				actor.setActor_Constellation(rs.getString(4));
				actor.setActor_Birth(rs.getString(5));
				actor.setActor_BirthPlace(rs.getString(6));
				actor.setProfession(rs.getString(7));
				actor.setOther_ForeignName(rs.getString(8));
				actor.setOther_ChineseName(rs.getString(9));
				actor.setImdb_number(rs.getString(10));
				actor.setOffical_Website(rs.getString(11));
				actor.setActor_Intro(rs.getString(12));
				actor.setActor_Photo(rs.getString(13));
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
		return actor;
	}
}
