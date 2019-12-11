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

	//创建一个影评类,准确点描述是注入信息到传入的comt中
	public static void createComment(Comment comt)throws IOException{
		Scanner reader=new Scanner(System.in);
		System.out.println("请输入影评信息：");
		System.out.println("电影ID：");
		comt.setFilmId(reader.nextInt());
		System.out.println("用户ID：");
		comt.setUserId(reader.nextInt());
		System.out.println("评定星级：");
		comt.setMark(reader.nextInt());
		System.out.println("评论内容：");
		comt.setContent(reader.nextLine());
		System.out.println("评论时间：");
		comt.setSubTime(reader.nextLine());
		System.out.println("终端设备：");
		comt.setTerminal(reader.nextLine());
		System.out.println("获赞数：");
		comt.setBeLiked(reader.nextInt());
		
	}
	
	//插入一条影评
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
            
          //自动生成id
			int idnum=getCountId(GET_IDNum_SQL);
			comt.setCommentId(idnum+1);//将得到的id放进Comt
			pst.setInt(1, comt.getCommentId());//生成ID后插入
			
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

	//更新一条影评
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
		
		System.out.println("-------------请您输入需要更新的影评ID----------");
		updateID=reader.nextInt();
		System.out.println("---------------请选择您要更新的类型------------------");
		System.out.println("1.更新整体信息\t2.更新电影ID\t3.更新用户ID\t4.更新评定星级");
		System.out.println("5.更新内容\t6.更新提交时间 \t7.更新终端设备\t8.更新获赞数");
		option=reader.nextInt();
		
		Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------正在执行更新操作-----------------------");

	    try {
	    	conn=getConnection();
	    		    	
	    switch(option){
	    case 1:
	    	//更新一条影评的全部信息通过删除再添加的方法
	    	stmt = conn.createStatement();
	    	String sqlDelete="delete from Comment where Comment_ID="+updateID;
	    	stmt.executeUpdate(sqlDelete);
	    	createComment(comt);
	    	//执行insertComment
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
	    	//更新一条影评的电影ID
	    	pst=conn.prepareStatement(UPDATE_COMMENT_FILMID);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的电影ID：");
	    	int filmId=reader.nextInt();
	    	pst.setInt(1, filmId);
	    	pst.execute();
	    	pst.close();	
	    	break;
	    case 3:
	    	//更新一条影评的用户ID
	    	pst=conn.prepareStatement(UPDATE_COMMENT_USERID);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的用户ID：");
	    	int userId=reader.nextInt();
	    	pst.setInt(1, userId);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 4:
	    	//更新一条影评的评定星级
	    	pst=conn.prepareStatement(UPDATE_COMMENT_MARK);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的评定星级：");
	    	int mark=reader.nextInt();
	    	pst.setInt(1, mark);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 5:
	    	//更新一条影评的内容
	    	pst=conn.prepareStatement(UPDATE_COMMENT_CONTENT);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的内容：");
	    	String content=reader.nextLine();
	    	pst.setString(1, content);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 6:
	    	//更新一条影评的提交时间
	    	pst=conn.prepareStatement(UPDATE_COMMENT_SUBTIME);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的提交时间：");
	    	String subTime=reader.nextLine();
	    	pst.setString(1, subTime);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 7:
	    	//更新一条影评的终端设备
	    	pst=conn.prepareStatement(UPDATE_COMMENT_TERMINAL);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的终端设备：");
	    	String terminal=reader.nextLine();
	    	pst.setString(1, terminal);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 8:
	    	//更新一条影评的获赞数
	    	pst=conn.prepareStatement(UPDATE_COMMENT_BELIKED);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的获赞数：");
	    	int beLiked=reader.nextInt();
	    	pst.setInt(1, beLiked);
	    	pst.execute();
	    	pst.close();
	    	break;	    
	    default:
    	    System.out.println("错误的选择！即将返回......");
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
	    System.out.println("--------------！更新操作执行完成！---------------------");

	}

	//删除一条影评
	private static final String DELETE_COMMENT="delete from Comment where Comment_ID=";	
	public void deleteComment(int commentId) {
		Connection conn = null;
		Statement stmt = null;
		Scanner reader=new Scanner(System.in);
		try{
			conn = getConnection();
			System.out.println("----------------正在执行删除操作-----------------------");
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

		System.out.println("--------------！删除操作执行成功！---------------------");

	}

	//获取一条影评
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
