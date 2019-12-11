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
		System.out.println("��������Ա�����֣�");
		actor.setActor_Name(read.nextLine());
		System.out.println("��������Ա���Ա�");
		actor.setActor_Sex(read.nextLine());
		System.out.println("��������Ա��������");
		actor.setActor_Constellation(read.nextLine());
		System.out.println("��������Ա�ĳ������ڣ�");
		actor.setActor_Birth(read.nextLine());
		System.out.println("��������Ա�ĳ����أ�");
		actor.setActor_BirthPlace(read.nextLine());
		System.out.println("��������Ա��ְҵ��");
		actor.setProfession(read.nextLine());
		System.out.println("��������Ա����������");
		actor.setOther_ForeignName(read.nextLine());
		System.out.println("��������Ա����������");
		actor.setOther_ChineseName(read.nextLine());
		System.out.println("��������Ա��IMDB��ţ�");
		actor.setImdb_number(read.nextLine());
		System.out.println("��������Ա�Ĺٷ���վ��");
		actor.setOffical_Website(read.nextLine());
		System.out.println("��������Ա�ļ�飺");
		actor.setActor_Intro(read.nextLine());
		System.out.println("��������Ա����Ƭ���ӵ�ַ��");
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
	
	
	//�����½���Ա��¼
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
	//������Ա��Ϣ
	public void updateActor(int Actor_ID) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//�����жϲ�ѯ��ɾ���Ƿ�ɹ�
		Actor actor=null;
		int updateID=Actor_ID;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXISTS_UPDATE_ACTOR_SQL);
			pstmt.setInt(1, updateID);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("��ѡ����Ҫ���еĸ��²���\n\t\t\t1.������Ա������Ϣ\t2.������Ա����\t3.������Ա�Ա�\t4.������Ա����\t5.������Ա��������\t6.������Ա��������");
				System.out.println("------------7.������Աְҵ\t8.������Ա������\t9.������Ա������\t10.������ԱIMDB\t11.������Ա�ٷ���վ\t12.������Ա���\t13.������Ա��Ƭ-----");
				int choice=read.nextInt();
				switch(choice) {
				case 1://����������Ϣ
					//��ɾ��ԭ����¼��Ȼ������µ��޸���������Ϣ�ļ�¼
					pstmt=conn.prepareStatement(DELETE_UPDATE_OLD_ACTOR_SQL);
					pstmt.setInt(1, updateID);
					rs=pstmt.execute();
					if(rs) {
						//ɾ���ɹ����½�һ��Actor����
						actor=new Actor();
						input(actor);
					}
					//�����¼�¼
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
						System.out.println("���±��Ϊ��"+updateID+"����Ա������Ϣ�ɹ���");
					}
					else {
						System.out.println("���±��Ϊ��"+updateID+"����Ա������Ϣʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 2://������Ա����
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Name_SQL);
					String new_name=null;
					System.out.print("�������޸ĺ����Ա������");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�����ɹ���");
					}
					else
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա����ʧ�ܣ�");
						pstmt.close();
					break;
				case 3://������Ա���Ա�
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Sex_SQL);
					String new_Aname=null; 
					System.out.print("�������޸ĺ����Ա������");
					new_Aname=read.nextLine();
					pstmt.setString(1, new_Aname);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�������ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա������ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 4://������Ա����
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Constellation_SQL);
					String new_cons; 
					System.out.print("�������޸ĺ����Ա������");
					new_cons=read.nextLine();
					pstmt.setString(1, new_cons);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�����ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա����ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 5://������Ա��������
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Birth_SQL);
					String new_date; 
					System.out.print("�������޸ĺ����Ա�������ڣ�");
					new_date=read.nextLine();
					pstmt.setString(1, new_date);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�������ڳɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա��������ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 6://������Ա������
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Birthplace_SQL);
					String new_place; 
					System.out.print("�������޸ĺ����Ա�����أ�");
					new_place=read.nextLine();
					pstmt.setString(1, new_place);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�����سɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա������ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 7://������Ա��ְҵ
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Profession_SQL);
					String new_prof; 
					System.out.print("�������޸ĺ����Աְҵ��");
					new_prof=read.nextLine();
					pstmt.setString(1, new_prof);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Աְҵ�ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Աְҵʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 8://������Ա������
					pstmt=conn.prepareStatement(UPDATE_ACTOR_ForeignName_SQL);
					String new_fn; 
					System.out.print("�������޸ĺ����Ա��������");
					new_fn=read.nextLine();
					pstmt.setString(1, new_fn);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�������ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա������ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 9://������Ա��������
					pstmt=conn.prepareStatement(UPDATE_ACTOR_ChineseName_SQL);
					String new_cn; 
					System.out.print("�������޸ĺ����Ա��������");
					new_cn=read.nextLine();
					pstmt.setString(1, new_cn);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�������ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա������ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 10://������Ա��IMDB
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Imdb_SQL);
					String new_IMDB; 
					System.out.print("�������޸ĺ��IMDB��");
					new_IMDB=read.nextLine();
					pstmt.setString(1, new_IMDB);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����ԱIMDB�ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����ԱIMDBʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 11://������Ա�Ĺٷ���վ
					pstmt=conn.prepareStatement(UPDATE_ACTOR_OffiWeb_SQL);
					String new_web; 
					System.out.print("�������޸ĺ����Ա�ٷ���վ��");
					new_web=read.nextLine();
					pstmt.setString(1, new_web);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�ٷ���վ�ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա�ٷ���վʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 12://������Ա�ļ��
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Intro_SQL);
					String new_intro; 
					System.out.print("�������޸ĺ����Ա��飺");
					new_intro=read.nextLine();
					pstmt.setString(1, new_intro);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա���ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա���ʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 13://������Ա����Ƭ
					pstmt=conn.prepareStatement(UPDATE_ACTOR_Photo_SQL);
					String new_photo; 
					System.out.print("�������޸ĺ����Ա��Ƭ���ӣ�");
					new_photo=read.nextLine();
					pstmt.setString(1, new_photo);
					pstmt.setInt(2, updateID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա��Ƭ�ɹ���");
					}
					else {
						System.out.println("�޸ı��Ϊ��"+updateID+"����Ա��Ƭʧ�ܣ�");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("����ϵͳû�и���Ա��¼!");
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
	//������Աidɾ����¼
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
	
	//����Actor_ID��ѯһ����Աȫ����Ϣ
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
