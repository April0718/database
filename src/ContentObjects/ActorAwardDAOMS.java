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
		System.out.println("����������������");
		actoraward.setAward(read.nextLine());
		System.out.println("����������ڣ�");
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
				System.out.println("�ɹ������"+"result"+"�м�¼��");
			}
			pstmt.close();
			conn.close();
		}catch(IOException e) {
			System.out.println("���ݳ�ʼ���쳣��");
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
		Boolean rs=false;//�����жϲ�ѯ��ɾ���Ƿ�ɹ�
		ActorAward actoraward=null;
		int updateID=Aid;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_ActorAward_SQL);
			pstmt.setInt(1, updateID);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("��ѡ����Ҫ���еĸ��²���\n\t\t\t1.������Ա��������Ϣ\t2.������Ա��õĽ�����\t3.���°佱ʱ��");
				int choice=read.nextInt();
				switch(choice) {
				case 1://������Ա��������Ϣ
					//��ɾ��ԭ����¼��Ȼ������µ��޸ĺ��������Ϣ�ļ�¼
					pstmt=conn.prepareStatement(DELETE_ActorAward_SQL);
					pstmt.setInt(1, updateID);
					rs=pstmt.execute();
					if(rs) {
						//ɾ���ɹ����½�һ��Actor����
						actoraward=new ActorAward();
						input(actoraward);
					}
					//�����¼�¼
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_AWARD_SQL);
					pstmt.setInt(1,updateID);
					pstmt.setString(2, actoraward.getAward());
					pstmt.setString(3, actoraward.getAcAwtime());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("������Ա��������Ϣ�ɹ���");
					}
					else {
						System.out.println("������Ա��������Ϣʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 2://������Ա��õĽ�����
					pstmt=conn.prepareStatement(UPDATE_ActorAward_NAME_SQL);
					String new_name=null; 
					System.out.print("�������޸ĺ�Ľ�������");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸Ļ�ý������ɹ���");
					}
					else
						System.out.println("�޸Ļ�ý�����ʧ�ܣ�");
						pstmt.close();
					break;
				case 3://���°佱ʱ��
					pstmt=conn.prepareStatement(UPDATE_ActorAward_TIME_SQL);
					String new_time=null; 
					System.out.print("�������޸ĺ�İ佱ʱ�䣺");
					new_time=read.nextLine();
					pstmt.setString(1, new_time);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�ɹ��޸���Ա��ʱ�䣡");
					}
					else {
						System.out.println("ʧ���޸���Ա��ʱ�䣡");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("����ϵͳû�и���Ա�񽱼�¼!");
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
				System.out.println("�ɹ�ɾ�����ݣ�");
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
		System.out.println("---------------����Ա���л���Ϣ-----------------");
		for(int i=0;i<actorawards.size();i++) {
			ActorAward acAward=new ActorAward();
			acAward=actorawards.get(i);
			System.out.println("��������"+acAward.getAward()+" ����"+acAward.getAcAwtime()+"��");
		}
		System.out.println("---------------------------------------");
	}
	
}
