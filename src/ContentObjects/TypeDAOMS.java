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
		System.out.println("�������Ӱ����");
		type.setTypeName(read.nextLine());
		System.out.println("�������Ӱ���͵����ȼ���");
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
	
	public void updateType(int fid,String typeName) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//�����жϲ�ѯ��ɾ���Ƿ�ɹ�
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
				System.out.println("��ѡ����Ҫ���еĸ��²���\n\t\t\t1.���µ�Ӱ����������Ϣ\t2.���µ�Ӱ������\t3.���µ�Ӱ���͵����ȼ�");
				int choice=read.nextInt();
				switch(choice) {
				case 1:
					//��ɾ��ԭ����¼��Ȼ������µ��޸ĺ��������Ϣ�ļ�¼
					pstmt=conn.prepareStatement(DELETE_TYPE_SQL);
					pstmt.setInt(1, updateID);
					pstmt.setString(2, updateTypeName);
					rs=pstmt.execute();
					if(rs) {
						//ɾ���ɹ����½�һ��Actor����
						type=new Type();
						input(type);
					}
					//�����¼�¼
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_TYPE_SQL);
					pstmt.setInt(1,updateID);
					pstmt.setString(2, type.getTypeName());
					pstmt.setInt(3, type.getPri());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("���µ�Ӱ����������Ϣ�ɹ���");
					}
					else {
						System.out.println("���µ�Ӱ����������Ϣʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 2:
					pstmt=conn.prepareStatement(UPDATE_TYPE_NAME_SQL);
					String new_name=null; 
					System.out.print("�������޸ĺ����������");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateTypeName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ĵ�Ӱ���ͳɹ���");
					}
					else
						System.out.println("�޸ĵ�Ӱ����ʧ�ܣ�");
						pstmt.close();
					break;
				case 3:
					pstmt=conn.prepareStatement(UPDATE_TYPE_Priority_SQL);
					int new_pri=0; 
					System.out.print("�������޸ĺ�ĵ�Ӱ�������ȼ���");
					new_pri=read.nextInt();
					pstmt.setInt(1, new_pri);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateTypeName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�ɹ��޸ĵ�Ӱ�������ȼ���");
					}
					else {
						System.out.println("ʧ���޸ĵ�Ӱ�������ȼ���");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("����ϵͳû�иõ�Ӱ���ͼ�¼!");
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
