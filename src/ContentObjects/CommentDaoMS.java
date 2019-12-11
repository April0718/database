package ContentObjects;
import ConnectedDao.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CommentDaoMS extends DAOBase implements CommentDAO {

	//����һ��Ӱ����,׼ȷ��������ע����Ϣ�������comt��
	public static void createComment(Comment comt)throws IOException{
		Scanner reader=new Scanner(System.in);
		System.out.println("������Ӱ����Ϣ��");
		System.out.println("��ӰID��");
		comt.setFilmId(reader.nextInt());
		System.out.println("�û�ID��");
		comt.setUserId(reader.nextInt());
		System.out.println("�����Ǽ���");
		comt.setMark(reader.nextInt());
		System.out.println("�������ݣ�");
		comt.setContent(reader.nextLine());
		System.out.println("����ʱ�䣺");
		comt.setSubTime(reader.nextLine());
		System.out.println("�ն��豸��");
		comt.setTerminal(reader.nextLine());
		System.out.println("��������");
		comt.setBeLiked(reader.nextInt());
		
	}
	
	//����һ��Ӱ��
	private static final String CREATE_COMMENT_SQL="INSERT INTO Comment values(?,?,?,?,?,?,?,?)";
	private static final String GET_IDNum_SQL="select count(Comment_ID) from Comment";
	public void insertComment(Comment comt) {
		Connection conn=null;
		PreparedStatement pst=null;
		
		try {
			conn=getConnection();
			if(comt==null)
			   createComment(comt);
			
			pst=conn.prepareStatement(CREATE_COMMENT_SQL);
            pst.setInt(2, comt.getFilmId());
            pst.setInt(3, comt.getUserId());
            pst.setInt(4, comt.getMark());
            pst.setString(5, comt.getContent());
            pst.setString(6, comt.getSubTime());
            pst.setString(7, comt.getTerminal());
            pst.setInt(8, comt.getBeLiked());
            
          //�Զ�����id
			int idnum=getCountId(GET_IDNum_SQL);
			comt.setCommentId(idnum+1);//���õ���id�Ž�Comt
			pst.setInt(1, comt.getCommentId());//����ID�����
			
			pst.execute();
			pst.close();
			conn.close();
            
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		if(pst!=null)
			try {
				pst.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}
}

	//����һ��Ӱ��
	private static final String UPDATE_COMMENT_FILMID="update Comment set Film_ID=? where Comment_ID=?";	
	private static final String UPDATE_COMMENT_USERID="update Comment set User_ID=? where Comment_ID=?";
	private static final String UPDATE_COMMENT_MARK="update Comment set StarLevel=? where Comment_ID=?";
	private static final String UPDATE_COMMENT_CONTENT="update Comment set Content=? where Comment_ID=?";
	private static final String UPDATE_COMMENT_SUBTIME="update Comment set Comment_Time=? where Comment_ID=?";
	private static final String UPDATE_COMMENT_TERMINAL="update Comment set Terminal=? where Comment_ID=?";	
	private static final String UPDATE_COMMENT_BELIKED="update Comment set OtherLike=? where Comment_ID=?";	
	public void updateComment() {
		Comment comt=new Comment();
		int updateID;
		int option;
		Scanner reader=new Scanner(System.in);
		
		System.out.println("-------------����������Ҫ���µ�Ӱ��ID----------");
		updateID=reader.nextInt();
		System.out.println("---------------��ѡ����Ҫ���µ�����------------------");
		System.out.println("1.����������Ϣ\t2.���µ�ӰID\t3.�����û�ID\t4.���������Ǽ�");
		System.out.println("5.��������\t6.�����ύʱ�� \t7.�����ն��豸\t8.���»�����");
		option=reader.nextInt();
		
		Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------����ִ�и��²���-----------------------");

	    try {
	    	conn=getConnection();
	    		    	
	    switch(option){
	    case 1:
	    	//����һ��Ӱ����ȫ����Ϣͨ��ɾ������ӵķ���
	    	stmt = conn.createStatement();
	    	String sqlDelete="delete from Comment where Comment_ID="+updateID;
	    	stmt.executeUpdate(sqlDelete);
	    	createComment(comt);
	    	//ִ��insertComment
	    	pst=conn.prepareStatement(CREATE_COMMENT_SQL);
	    	pst.setInt(1, updateID);
	    	pst.setInt(2, comt.getFilmId());
	    	pst.setInt(3, comt.getUserId());
	    	pst.setInt(4, comt.getMark());
	    	pst.setString(5, comt.getContent());
	    	pst.setString(6, comt.getSubTime());
	    	pst.setString(7, comt.getTerminal());
	    	pst.setInt(8, comt.getBeLiked());
	    	pst.execute();
			pst.close();
			stmt.close();
	    	break;
	    case 2:
	    	//����һ��Ӱ���ĵ�ӰID
	    	pst=conn.prepareStatement(UPDATE_COMMENT_FILMID);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ĵ�ӰID��");
	    	int filmId=reader.nextInt();
	    	pst.setInt(1, filmId);
	    	pst.execute();
	    	pst.close();	
	    	break;
	    case 3:
	    	//����һ��Ӱ�����û�ID
	    	pst=conn.prepareStatement(UPDATE_COMMENT_USERID);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ���û�ID��");
	    	int userId=reader.nextInt();
	    	pst.setInt(1, userId);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 4:
	    	//����һ��Ӱ���������Ǽ�
	    	pst=conn.prepareStatement(UPDATE_COMMENT_MARK);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�������Ǽ���");
	    	int mark=reader.nextInt();
	    	pst.setInt(1, mark);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 5:
	    	//����һ��Ӱ��������
	    	pst=conn.prepareStatement(UPDATE_COMMENT_CONTENT);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�����ݣ�");
	    	String content=reader.nextLine();
	    	pst.setString(1, content);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 6:
	    	//����һ��Ӱ�����ύʱ��
	    	pst=conn.prepareStatement(UPDATE_COMMENT_SUBTIME);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ���ύʱ�䣺");
	    	String subTime=reader.nextLine();
	    	pst.setString(1, subTime);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 7:
	    	//����һ��Ӱ�����ն��豸
	    	pst=conn.prepareStatement(UPDATE_COMMENT_TERMINAL);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ���ն��豸��");
	    	String terminal=reader.nextLine();
	    	pst.setString(1, terminal);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 8:
	    	//����һ��Ӱ���Ļ�����
	    	pst=conn.prepareStatement(UPDATE_COMMENT_BELIKED);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�Ļ�������");
	    	int beLiked=reader.nextInt();
	    	pst.setInt(1, beLiked);
	    	pst.execute();
	    	pst.close();
	    	break;	    
	    default:
    	    System.out.println("�����ѡ�񣡼�������......");
    	    break;
	    }
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	if(pst!=null) {
	    		try {
					pst.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
	    	}
	    	if(stmt!=null) {
	    		try {
					stmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
	    	}
	    }
	    System.out.println("--------------�����²���ִ����ɣ�---------------------");

	}

	//ɾ��һ��Ӱ��
	private static final String DELETE_COMMENT="delete from Comment where Comment_ID=";	
	public void deleteComment(int commentId) {
		Connection conn = null;
		Statement stmt = null;
		Scanner reader=new Scanner(System.in);
		try{
			conn = getConnection();
			System.out.println("----------------����ִ��ɾ������-----------------------");
			stmt = conn.createStatement();
			String sql = DELETE_COMMENT+commentId;
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}

		System.out.println("--------------��ɾ������ִ�гɹ���---------------------");

	}

	//��ȡһ��Ӱ��
	private static final String GET_COMMENT="select * from Commment where Comment_ID=";

	public Comment getCommentByComId(int commentId) {
		Comment comt=new Comment();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql=GET_COMMENT+commentId;
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				comt.setCommentId(commentId);
				comt.setFilmId(rs.getInt("Film_ID"));
				comt.setUserId(rs.getInt("User_ID"));
				comt.setMark(rs.getInt("StarLevel"));
				comt.setContent(rs.getString("Content"));
				comt.setSubTime(rs.getString("Comment_Time"));
				comt.setTerminal(rs.getString("Terminal"));
				comt.setBeLiked(rs.getInt("OtherLike"));
				
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return comt;
	}

	private static final String GET_COMMENTBYFILMID="select * from Comment where Film_ID=";
	public ArrayList<Comment> getComment(int filmId) {
		ArrayList<Comment> list=new ArrayList<Comment>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql=GET_COMMENTBYFILMID+filmId;
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Comment comt=new Comment();
				comt.setCommentId(rs.getInt("Comment_ID"));
				comt.setFilmId(filmId);
				comt.setUserId(rs.getInt("User_ID"));
				comt.setMark(rs.getInt("Star_Level"));
				comt.setContent(rs.getString("Content"));
				comt.setSubTime(rs.getString("Comment_Time"));
				comt.setTerminal(rs.getString("Terminal"));
				comt.setBeLiked(rs.getInt("OtherLike"));
				list.add(comt);
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}

}
