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
		System.out.println("请输入电影标签名");
		label.setLabelName(read.nextLine());
		System.out.println("请输入标签标记次数：");
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
	//更改标签标记次数
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
	public void updateLabel(int Fid,String labelName) {
		Scanner read=new Scanner(System.in);
		Connection conn=null;
		PreparedStatement pstmt=null;
		Boolean rs=false;//用来判断查询、删除是否成功
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
				System.out.println("请选择想要进行的更新操作\n\t\t\t1.更新电影标签整体信息\t2.更新电影标签名\t3.更新电影标签标记次数");
				int choice=read.nextInt();
				switch(choice) {
				case 1:
					//先删除原来记录，然后插入新的修改后的整体信息的记录
					pstmt=conn.prepareStatement(DELETE_LABEL_SQL);
					pstmt.setInt(1, updateID);
					pstmt.setString(2, updateLabelName);
					rs=pstmt.execute();
					if(rs) {
						//删除成功则新建一个对象
						label=new Label();
						input(label);
					}
					//插入新纪录
					pstmt=conn.prepareStatement(INSERT_UPDATE_NEW_LABEL_SQL);
					pstmt.setInt(1,updateID);
					pstmt.setString(2, label.getLabelName());
					pstmt.setInt(3, label.getCount());
					rs=pstmt.execute();
					if(rs) {
						System.out.println("更新电影标签整体信息成功！");
					}
					else {
						System.out.println("更新电影标签整体信息失败！");
					}
					pstmt.close();
					break;
				case 2:
					pstmt=conn.prepareStatement(UPDATE_LABEL_NAME_SQL);
					String new_name=null; 
					System.out.print("请输入修改后的电影标签名：");
					new_name=read.nextLine();
					pstmt.setString(1, new_name);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateLabelName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("修改电影标签名成功！");
					}
					else
						System.out.println("修改电影标签名失败！");
						pstmt.close();
					break;
				case 3:
					pstmt=conn.prepareStatement(UPDATE_LABEL_COUNT_SQL);
					int new_count=0; 
					System.out.print("请输入修改后的电影标签标记次数：");
					new_count=read.nextInt();
					pstmt.setInt(1, new_count);
					pstmt.setInt(2, updateID);
					pstmt.setString(3, updateLabelName);
					rs=pstmt.execute();
					if(rs) {
						System.out.println("成功修改电影标签标记次数！");
					}
					else {
						System.out.println("失败修改电影标签标记次数！");
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
		List<Label> labels=new ArrayList<Label>();//只获取前10个标签名
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
					break;//到第10个则截止，跳出循环
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
			System.out.println("序号："+index+"\t"+labels.get(index).getLabelName());
		}
	}
}
