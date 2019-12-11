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
		System.out.println("�������Ӱ�ı�ţ�");
		award.setFid(read.nextInt());
		System.out.println("�����뽱������");
		award.setAwname(read.nextLine());
		System.out.println("������佱���ڣ�");
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
	//�����Ӱ�����¼
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
	public void updateAward(String Awname) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//�����жϲ�ѯ��ɾ���Ƿ�ɹ�
		Award award=null;
		String updateName=Awname;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_AWARD_SQL);
			pstmt.setString(1, updateName);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("��ѡ����Ҫ���еĸ��²���\n\t\t\t1.���½���������Ϣ\t2.���µ�Ӱ���\t3.���½�����\t4.���°佱ʱ��");
				int choice=read.nextInt();
				switch(choice) {
				case 1://���½���������Ϣ
					//��ɾ��ԭ����¼��Ȼ������µ��޸ĺ��������Ϣ�ļ�¼
					pstmt=conn.prepareStatement(DELETE_AWARD_SQL);
					pstmt.setString(1, updateName);
					rs=pstmt.execute();
					if(rs) {
						//ɾ���ɹ����½�һ��Actor����
						award=new Award();
						input(award);
					}
					//�����¼�¼
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_AWARD_SQL);
					pstmt.setInt(1,award.getFid());
					pstmt.setString(2, award.getAwname());
					pstmt.setString(3, award.getAwtime());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("���½���������Ϣ�ɹ���");
					}
					else {
						System.out.println("���½���������Ϣʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 2://������Ա���ݵĵ�Ӱ���
					pstmt=conn.prepareStatement(UPDATE_AWARD_FID_SQL);
					int new_Fid=0; 
					System.out.print("�������޸ĺ�ĵ�Ӱ��ţ�");
					new_Fid=read.nextInt();
					pstmt.setInt(1, new_Fid);
					pstmt.setString(2, updateName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸Ļ�ý�����Ϊ��"+updateName+"�ĵ�Ӱ�ųɹ���");
					}
					else
						System.out.println("�޸Ļ�ý�����Ϊ��"+updateName+"�ĵ�Ӱ��ʧ�ܣ�");
						pstmt.close();
					break;
				case 3://���½�����
					pstmt=conn.prepareStatement(UPDATE_AWARD_NAME_SQL);
					String new_Awname=null; 
					System.out.print("�������޸ĺ�Ľ�������");
					new_Awname=read.nextLine();
					pstmt.setString(1, new_Awname);
					pstmt.setString(2, updateName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�ɹ��޸�ԭ���"+updateName+"Ϊ"+new_Awname);
					}
					else {
						System.out.println("ʧ���޸�ԭ���"+updateName+"Ϊ"+new_Awname);
					}
					pstmt.close();
					break;
				case 4://���½���佱ʱ��
					pstmt=conn.prepareStatement(UPDATE_AWARD_TIME_SQL);
					String new_Awtime=null; 
					System.out.print("�������޸ĺ�Ľ���佱ʱ�䣺");
					new_Awtime=read.nextLine();
					pstmt.setString(1, new_Awtime);
					pstmt.setString(2, updateName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�ɹ��޸Ľ��"+updateName+"�İ佱ʱ��Ϊ"+new_Awtime);
					}
					else {
						System.out.println("ʧ���޸Ľ��"+updateName+"�İ佱ʱ��Ϊ"+new_Awtime);
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("����ϵͳû�иý����¼!");
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
	//���ݽ�����ɾ�������¼
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
	//���ݵ�ӰID��ѯ��Ӱ��õĽ���
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
	//�������
	public void print(List<Award> FAws) {
		Award award=null;
		for(int i=0;i<FAws.size();i++) {
			award=new Award();
			award=FAws.get(i);
			System.out.println("��������"+award.getAwname()+"  ����"+award.getAwtime()+"��");
		}
		System.out.println("--------------------------------------");
	}
}
