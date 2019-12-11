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
public class RecordDAOMS extends DAOBase implements RecordDAO{
	public void input(Record record) throws IOException{
		Scanner read=new Scanner(System.in);
		System.out.println("请输入电影编号：");
		record.setFid(read.nextInt());
		System.out.println("请输入电影标记的标签：");
		record.setTag(read.nextLine());
		System.out.println("请输入电影想看的理由：");
		record.setReason(read.nextLine());
	}
	private static final String CREATE_RECORD_SQL="insert into Records values(?,?,?,?)";
	private static final String GET_COUNT_ID_SQL="select count(distinct User_ID) as countID from Records";
	private static final String IF_EXSITS_UPDATE_RECORD_SQL="select * from Records where User_ID=? and Film_ID=?";
	private static final String DELETE_RECORD_SQL="delete from Records where User_ID=? and Film_ID=?";
	private static final String INSERT_UPDATE_NEW_RECORD_SQL="insert into Records values(?,?,?,?)";
	private static final String UPDATE_RECORD_FID_SQL="update Records set Film_ID=? where User_ID=? and Film_ID=?";
	private static final String UPDATE_RECORD_TAG_SQL="update Records set Tag=? where User_ID=? and Film_ID=?";
	private static final String UPDATE_RECORD_REASON_SQL="update Records set Reason=? where User_ID=? and Film_ID=?";
	private static final String GET_FILM_RECORD_SQL="select * from Records where User_ID=? and Film_ID=?";
	
	public Boolean insertRecord(Record record) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(CREATE_RECORD_SQL);
			pstmt.setInt(1, record.getUserID());
			pstmt.setInt(2, record.getFid());
			pstmt.setString(3, record.getTag());
			pstmt.setString(4, record.getReason());
			result=pstmt.executeUpdate();
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
		if(result>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateRecord(int uID,int fID) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
		Record record=null;
		int updateUID=uID;
		int updateFID=fID;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(IF_EXSITS_UPDATE_RECORD_SQL);
			pstmt.setInt(1, updateUID);
			pstmt.setInt(2, updateFID);
			rs=pstmt.execute();
			pstmt.close();
			if(rs) {
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新用户标记电影的记录整体信息\t2.更新用户标记的电影编号\t3.更新用户给电影标记的标签4.更新用户想看电影的理由");
				int choice=read.nextInt();
				switch(choice) {
				case 1:
					//先删除原来记录，然后插入新的修改后的整体信息的记录
					pstmt=conn.prepareStatement(DELETE_RECORD_SQL);
					pstmt.setInt(1, updateUID);
					pstmt.setInt(2, updateFID);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个Actor对象
						record=new Record();
						input(record);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_RECORD_SQL);
					pstmt.setInt(1,updateUID);
					pstmt.setInt(2, record.getFid());
					pstmt.setString(3, record.getTag());
					pstmt.setString(4, record.getReason());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新用户标记电影的记录整体信息成功！");
					}
					else {
						System.out.println("更新用户标记电影的记录整体信息失败！");
					}
					pstmt.close();
					break;
				case 2:
					pstmt=conn.prepareStatement(UPDATE_RECORD_FID_SQL);
					int new_fid=0;
					System.out.print("请输入修改后的电影编号：");
					new_fid=read.nextInt();
					pstmt.setInt(1, new_fid);
					pstmt.setInt(2, updateUID);
					pstmt.setInt(3, updateFID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改用户标记的电影编号成功！");
					}
					else
						System.out.println("修改用户标记的电影编号失败！");
						pstmt.close();
					break;
				case 3:
					pstmt=conn.prepareStatement(UPDATE_RECORD_TAG_SQL);
					String new_tag=null; 
					System.out.print("请输入修改后的用户标记的标签：");
					new_tag=read.nextLine();
					pstmt.setString(1, new_tag);
					pstmt.setInt(2, updateUID);
					pstmt.setInt(3, updateFID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改用户给电影标记的标签！");
					}
					else {
						System.out.println("失败修改用户给电影标记的标签！");
					}
					pstmt.close();
					break;
				case 4:
					pstmt=conn.prepareStatement(UPDATE_RECORD_REASON_SQL);
					String new_reason=null; 
					System.out.print("请输入修改后的用户想看电影的理由：");
					new_reason=read.nextLine();
					pstmt.setString(1, new_reason);
					pstmt.setInt(2, updateUID);
					pstmt.setInt(3, updateFID);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改用户想看电影的理由！");
					}
					else {
						System.out.println("失败修改用户想看电影的理由！");
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
	public void deleteRecord(int uID,int fID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int result=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(DELETE_RECORD_SQL);
			pstmt.setInt(1, uID);
			pstmt.setInt(2, fID);
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
	public List<Record> getRecord(int uID,int fID){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Record> records=new ArrayList<Record>();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(GET_FILM_RECORD_SQL);
			pstmt.setInt(1,uID);
			pstmt.setInt(2,fID);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Record record=new Record();
				record.setUserID(rs.getInt(1));
				record.setFid(rs.getInt(2));
				record.setTag(rs.getString(3));
				record.setReason(rs.getString(4));
				records.add(record);
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
		return records;
	}
	public void print(List<Record> records) {
		
	}
}
