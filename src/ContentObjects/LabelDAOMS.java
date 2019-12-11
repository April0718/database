package ContentObjects;

import ConnectedDao.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LabelDAOMS extends DAOBase implements LabelDAO{
	public void input(Label label) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("�������Ӱ��ǩ��");
		label.setLabelName(read.nextLine());
		System.out.println("�������ǩ��Ǵ�����");
		label.setCount(read.nextInt());
	}
	private static final String CREATE_LABEL_SQL="insert into Labels values(?,?,?)";
	private static final String GET_COUNT_ID_SQL="select count(distinct Film_ID) as countID from Labels";
	private static final String IF_EXSITS_UPDATE_LABEL_SQL="select * from Labels where Film_ID=? and Label_Name=?";
	private static final String DELETE_LABEL_SQL="delete from Labels where Film_ID=? and Label_Name=?";
	private static final String INSERT_UPDATE_NEW_LABEL_SQL="insert into Labels values(?,?,?)";
	private static final String UPDATE_LABEL_NAME_SQL="update Labels set Label_Name=? where Film_ID=? and Label_Name=?";
	private static final String UPDATE_LABEL_COUNT_SQL="update Labels set Label_Count=? where Film_ID=? and Label_Name=?";
	private static final String GET_FILM_LABEL_SQL="select * from Labels where Film_ID=?";
	private static final String GET_LABEL_SQL="select * from Labels where Film_ID=? order by Label_Count DESC";
	//���ı�ǩ��Ǵ���
	private static final String UPDATE_COUNT="update Labels set Label_Count=? where Film_ID=?";
	public void insertLabel() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		int countID=0;
		countID=getCountId(GET_COUNT_ID_SQL);
		try {
			conn=getConnection();
			Label label=new Label();
			input(label);
			pstmt=conn.prepareStatement(CREATE_LABEL_SQL);
			pstmt.setInt(1, countID+1);
			pstmt.setString(2, label.getLabelName());
			pstmt.setInt(3, label.getCount());
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
	public void updateLabel(int Fid,String labelName) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//�����жϲ�ѯ��ɾ���Ƿ�ɹ�
		Label label=null;
		int updateID=Fid;
		String updateLabelName=labelName;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_LABEL_SQL);
			pstmt.setInt(1, updateID);
			pstmt.setString(2, updateLabelName);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("��ѡ����Ҫ���еĸ��²���\n\t\t\t1.���µ�Ӱ��ǩ������Ϣ\t2.���µ�Ӱ��ǩ��\t3.���µ�Ӱ��ǩ��Ǵ���");
				int choice=read.nextInt();
				switch(choice) {
				case 1:
					//��ɾ��ԭ����¼��Ȼ������µ��޸ĺ��������Ϣ�ļ�¼
					pstmt=conn.prepareStatement(DELETE_LABEL_SQL);
					pstmt.setInt(1, updateID);
					pstmt.setString(2, updateLabelName);
					rs=pstmt.execute();
					if(rs) {
						//ɾ���ɹ����½�һ������
						label=new Label();
						input(label);
					}
					//�����¼�¼
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_LABEL_SQL);
					pstmt.setInt(1,updateID);
					pstmt.setString(2, label.getLabelName());
					pstmt.setInt(3, label.getCount());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("���µ�Ӱ��ǩ������Ϣ�ɹ���");
					}
					else {
						System.out.println("���µ�Ӱ��ǩ������Ϣʧ�ܣ�");
					}
					pstmt.close();
					break;
				case 2:
					pstmt=conn.prepareStatement(UPDATE_LABEL_NAME_SQL);
					String new_name=null; 
					System.out.print("�������޸ĺ�ĵ�Ӱ��ǩ����");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateLabelName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�޸ĵ�Ӱ��ǩ���ɹ���");
					}
					else
						System.out.println("�޸ĵ�Ӱ��ǩ��ʧ�ܣ�");
						pstmt.close();
					break;
				case 3:
					pstmt=conn.prepareStatement(UPDATE_LABEL_COUNT_SQL);
					int new_count=0; 
					System.out.print("�������޸ĺ�ĵ�Ӱ��ǩ��Ǵ�����");
					new_count=read.nextInt();
					pstmt.setInt(1, new_count);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateLabelName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("�ɹ��޸ĵ�Ӱ��ǩ��Ǵ�����");
					}
					else {
						System.out.println("ʧ���޸ĵ�Ӱ��ǩ��Ǵ�����");
					}
					pstmt.close();
					break;
				}
				
			}
			else {
				System.out.println("����ϵͳû�иõ�Ӱ��ǩ��¼!");
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
	public Boolean updateLabelCount(int Fid,int count) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(UPDATE_COUNT);
			pstmt.setInt(1, count);
			pstmt.setInt(2, Fid);
			result=pstmt.executeUpdate();
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
		if(result>0) {
			return true;
		}
		else {
			return false;
		}
	}
	public void deleteLabel(int Fid,String labelName) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_LABEL_SQL);
			pstmt.setInt(1, Fid);
			pstmt.setString(2, labelName);
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
	public List<Label> getFilmLabels(int Fid){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Label> labels=new ArrayList<Label>();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_FILM_LABEL_SQL);
			pstmt.setInt(1,Fid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Label label=new Label();
				label.setFid(rs.getInt(1));
				label.setLabelName(rs.getString(2));
				label.setCount(rs.getInt(3));
				labels.add(label);
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
		return labels;
	}
	public List<Label> getLabels(int Fid){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Label> labels=new ArrayList<Label>();//ֻ��ȡǰ10����ǩ��
		int num=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_LABEL_SQL);
			pstmt.setInt(1, Fid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Label label=new Label();
				label.setFid(rs.getInt(1));
				label.setLabelName(rs.getString(2));
				label.setCount(rs.getInt(3));
				labels.add(label);
				if(num==9) {
					break;//����10�����ֹ������ѭ��
				}
				num++;
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
		return labels;
	}
	public void printLabelsName(List<Label> labels) {
		int num=labels.size();
		for(int index=0;index<num;index++) {
			System.out.println("��ţ�"+index+"\t"+labels.get(index).getLabelName());
		}
	}
}
