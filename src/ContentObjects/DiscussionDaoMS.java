package ContentObjects;
import ConnectedDao.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class DiscussionDaoMS extends DAOBase implements DiscussionDAO {

	//查重
	private static final String CHECK_POSTID_SQL="select Post_ID from Discussion where Post_ID=";
	//private static final String CHECK_FLOOR_SQL="select floor from Discussion where Post_ID=? and Floor=?";
	
	//answer为true时表示库里有重复
	public  boolean checkUniqueness(int postId,int floor) {
		boolean answer=false;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql=null;
		    //String temp="-1";
			if(floor!=-1) {
				sql="select floor from Discussion where Post_ID="+postId+" and Floor="+floor;
			}else if(floor==-1){			
				sql=CHECK_POSTID_SQL+postId;
			}
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				answer=true;
			}
			
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
		return answer;
	}
	//创建一个讨论记录
	public void createDiscussion(Discussion dic)throws IOException{
	
		int tempPost=-1;
		int tempFloor=-1;
		boolean isGo=true;
		boolean isNew=false;
		String optionStr=null;
		Scanner reader=new Scanner(System.in);
		System.out.println("请输入讨论区讨论内容：");
		System.out.println("是否创建在已有帖子的基础上？（Y/N）");
		optionStr=reader.nextLine();
		if(optionStr.equals("N")||optionStr.equals("n")) {	
		//需要查重
		while(isGo) {
			System.out.println("新讨论帖子ID：");
			tempPost=reader.nextInt();
			if(!checkUniqueness(tempPost,-1)) {			
				dic.setDiscussionId(tempPost);
			}
			isNew=true;
		}
		}else if(optionStr.equals("N")||optionStr.equals("n")) {
			System.out.println("已存在的讨论帖子ID：");
			tempPost=reader.nextInt();
			dic.setDiscussionId(tempPost);
		}
		//------------------------------
		System.out.println("讨论电影ID：");
		dic.setFilmId(reader.nextInt());
		System.out.println("讨论用户ID：");
		dic.setUserId(reader.nextInt());
		System.out.println("讨论标题：");
		dic.setTitle(reader.nextLine());
		System.out.println("讨论内容：");
		dic.setContent(reader.nextLine());
		
		//需要查重
		//
		while(isGo) {
			System.out.println("讨论楼层：");
		if(isNew==true) {
			dic.setFloor(reader.nextInt());
			break;
		}else if(isNew==false){				
				tempFloor=reader.nextInt();
				if(!checkUniqueness(tempPost,tempFloor)) {			
					dic.setDiscussionId(tempFloor);
				}else {
					System.out.println("该楼层已存在，请重新输入......");
				}				
			}
		}
	
		//-------------------------
		System.out.println("讨论回复楼层：");
		dic.setReplyFloor(reader.nextInt());
		System.out.println("提交时间：");
		dic.setSubTime(reader.nextLine());
		System.out.println("获赞数：");
		dic.setBeLiked(reader.nextInt());
	}
	
	//插入一个讨论
	private static final String CREATE_DISCUSSION_SQL="INSERT INTO Discussion values(?,?,?,?,?,?,?,?,?)";
	//private static final String GET_IDNum_SQL="select count(Post_ID) from Discussion";
	public void insertDiscussion(Discussion disc) {
		Connection conn=null;
		PreparedStatement pst=null;
		
		try {
			conn=getConnection();
			System.out.println("----------------正在执行插入操作-----------------------");
			if(disc==null)
			   createDiscussion(disc);
			pst=conn.prepareStatement(CREATE_DISCUSSION_SQL);
			pst.setInt(1, disc.getDiscussionId());
			pst.setInt(2, disc.getFloor());
			pst.setInt(3, disc.getFilmId());
			pst.setInt(4, disc.getUserId());
			pst.setString(5, disc.getTitle());
			pst.setString(6, disc.getContent());
			pst.setInt(7, disc.getReplyFloor());
			pst.setString(8, disc.getSubTime());
			pst.setInt(9, disc.getBeLiked());
			
			
			/*自动生成id
			int idnum=getCountId(GET_IDNum_SQL);
			disc.setDiscussionId(idnum+1);//将得到的id放进Film
			pst.setInt(1, disc.getDiscussionId());//生成ID后插入
			*/
			pst.execute();
			pst.close();
			conn.close();			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(pst!=null)
				try {
					pst.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}
		System.out.println("--------------！插入操作执行完成！---------------------");
	}

	//更新一条讨论信息
	//更新用SQL语句
	//由于楼层和post_id可定位，故floor不可更新
	private static final String UPDATE_DISCUSSION_FILMID="update DISCUSSION set Film_ID=? where Post_ID=? and Floor=?";
	private static final String UPDATE_DISCUSSION_USERID="update DISCUSSION set User_ID=? where Post_ID=? and Floor=?";
	private static final String UPDATE_DISCUSSION_TITLE="update DISCUSSION set Post_Title=? where Post_ID=? and Floor?";
	private static final String UPDATE_DISCUSSION_CONTENT="update DISCUSSION set Post_Content=? where Post_ID=? and Floor=?";
	//private static final String UPDATE_DISCUSSION_FLOOR="update DISCUSSION set Floor=? where Post_ID=? and Floor=?";
	private static final String UPDATE_DISCUSSION_REPLYFLOOR="update DISCUSSION set Reply_Floor=? where Post_ID=? and Floor=?";
	private static final String UPDATE_DISCUSSION_SUBTIME="update DISCUSSION set Submit_time=? where Post_ID=? and Floor=?";
	private static final String UPDATE_DISCUSSION_BELIKED="update DISCUSSION set Other_Like=? where Post_ID=? and Floor=?";

	public void updateDiscussion() {
		Discussion disc=new Discussion();
		int updateID;
		int updateFloor;
		int option;
		Scanner reader=new Scanner(System.in);
		
		System.out.println("-------------请您输入需要更新的讨论帖子ID----------");
		updateID=reader.nextInt();
		System.out.println("-------------请您输入需要更新的楼层数----------");
		updateFloor=reader.nextInt();
		System.out.println("---------------请选择您要更新的类型------------------");
		System.out.println("1.更新整体信息\t2.更新电影ID \t3.更新用户ID\t4.更新讨论标题");
		System.out.println("5.更新讨论内容\t6.更新楼层数\t7.更新提交时间\t8.更新获赞数");
		option=reader.nextInt();
		
		Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------正在执行更新操作-----------------------");
	    try {
	    	conn=getConnection();
	    	switch(option) {
	    	case 1:
	    		//更新一条讨论的全部信息通过删除再添加的方法
	    		stmt = conn.createStatement();
		    	String sqlDelete="delete from Discussion where Post_ID="+updateID+" and Floor="+updateFloor;
		    	stmt.executeUpdate(sqlDelete);
		    	createDiscussion(disc);
		    	
		    	//执行insertDiscussion
		    	pst=conn.prepareStatement(CREATE_DISCUSSION_SQL);
		    	pst.setInt(1, updateID);
		    	pst.setInt(2, updateFloor);
				pst.setInt(3, disc.getFilmId());
				pst.setInt(4, disc.getUserId());
				pst.setString(5, disc.getTitle());
				pst.setString(6, disc.getContent());
				pst.setInt(7, disc.getReplyFloor());
				pst.setString(8, disc.getSubTime());
				pst.setInt(9, disc.getBeLiked());
				pst.execute();
				pst.close();
				stmt.close();		    	
	    	break;
	    	case 2:
	    		//更新电影ID
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_FILMID);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的电影ID：");
		    	int filmId=reader.nextInt();
		    	pst.setInt(1, filmId);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 3:
	    		//更新用户ID
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_USERID);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的用户ID：");
		    	int userId=reader.nextInt();
		    	pst.setInt(1, userId);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 4:
	    		//更新讨论标题
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_TITLE);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的标题：");
		    	String title=reader.nextLine();
		    	pst.setString(1, title);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 5:
	    		//更新讨论内容
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_CONTENT);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的内容：");
		    	String content=reader.nextLine();
		    	pst.setString(1, content);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 6:
	    		//更新楼层数
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_SUBTIME);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的楼层数：");
		    	int floor=reader.nextInt();
		    	pst.setInt(1, floor);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 7:
	    		//更新回复楼层
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_REPLYFLOOR);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的回复楼层：");
		    	String subTime=reader.nextLine();
		    	pst.setString(1, subTime);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 8:
	    		//更新获赞数
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_BELIKED);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("请输入修改后的获赞数：");
		    	int beLiked=reader.nextInt();
		    	pst.setInt(1,beLiked);
		    	pst.execute();
		    	pst.close();
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
    //删除讨论,此时可能不止一条
	private static final String DELETE_DISCUSSION="delete from Discussion where Post_ID=? and Floor=?";
	public void deleteDiscussion(int discId,int floor) {
		Connection conn = null;
		PreparedStatement pst = null;
		Scanner reader=new Scanner(System.in);
		try {
			conn = getConnection();//连接数据库
			System.out.println("----------------正在执行删除操作-----------------------");
		
			pst = conn.prepareStatement(DELETE_DISCUSSION);
			pst.setInt(1, discId);
			pst.setInt(2, floor);
			pst.execute();
			pst.close();
		}catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			if(pst!=null)
				try {
					pst.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}

		System.out.println("--------------！删除操作执行成功！---------------------");
		
	}

	//获取一条讨论信息
	//private static final String GET_DISCUSSION="select * from Discussion where Post_ID=? and Floor=?";
	public Discussion getDiscussionForOne(int discId,int floor) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Discussion disc=new Discussion();
		//Scanner reader=new Scanner(System.in);
		String sql="select * from Discussion where Post_ID="+discId+" and Floor="+floor;
		try {
			conn = getConnection();
			//pst=conn.prepareStatement(GET_DISCUSSION);
			//pst.setInt(1, discId);
			//pst.setInt(2, floor);
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				disc.setDiscussionId(rs.getInt("Post_ID"));
				disc.setFilmId(rs.getInt("Film_ID"));
				disc.setUserId(rs.getInt("User_ID"));
				disc.setTitle(rs.getString("Post_Title"));
				disc.setContent(rs.getString("Post_Content"));
				disc.setFloor(rs.getInt("Floor"));
				disc.setReplyFloor(rs.getInt("Reply_Floor"));
				disc.setBeLiked(rs.getInt("Other_Like"));
				//disc.print();
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
	          return disc;	
     }
	
	public ArrayList<Discussion> getDiscussion(int filmId, int floor) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Discussion> list=new ArrayList<Discussion>();
		String sql="select * from Discussion where Film_ID="+filmId+" and Floor="+floor;
		try {
			conn = getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				Discussion disc=new Discussion();
				disc.setDiscussionId(rs.getInt("Post_ID"));
				disc.setFilmId(rs.getInt("Film_ID"));
				disc.setUserId(rs.getInt("User_ID"));
				disc.setTitle(rs.getString("Post_Title"));
				disc.setContent(rs.getString("Post_Content"));
				disc.setFloor(rs.getInt("Floor"));
				disc.setReplyFloor(rs.getInt("Reply_Floor"));
				disc.setBeLiked(rs.getInt("Other_Like"));
				//disc.print();
				list.add(disc);
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
	
	
	public ArrayList<Discussion> getDiscussionAllFloor(int discId) {
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Discussion> list=new ArrayList<Discussion>();
		String sql="select * from Discussion where Post_ID="+discId+" order by Floor ASC";
		//根据帖子id获取所有该帖子楼层，并依据楼层升序排列；
		try {
			conn = getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				Discussion disc=new Discussion();
				disc.setDiscussionId(rs.getInt("Post_ID"));
				disc.setFilmId(rs.getInt("Film_ID"));
				disc.setUserId(rs.getInt("User_ID"));
				disc.setTitle(rs.getString("Post_Title"));
				disc.setContent(rs.getString("Post_Content"));
				disc.setFloor(rs.getInt("Floor"));
				disc.setReplyFloor(rs.getInt("Reply_Floor"));
				disc.setBeLiked(rs.getInt("Other_Like"));
				//disc.print();
				list.add(disc);
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
