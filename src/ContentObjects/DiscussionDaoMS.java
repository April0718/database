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

	//����
	private static final String CHECK_POSTID_SQL="select Post_ID from Discussion where Post_ID=";
	//private static final String CHECK_FLOOR_SQL="select floor from Discussion where Post_ID=? and Floor=?";
	
	//answerΪtrueʱ��ʾ�������ظ�
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
	//����һ�����ۼ�¼
	public void createDiscussion(Discussion dic)throws IOException{
	
		int tempPost=-1;
		int tempFloor=-1;
		boolean isGo=true;
		boolean isNew=false;
		String optionStr=null;
		Scanner reader=new Scanner(System.in);
		System.out.println("�������������������ݣ�");
		System.out.println("�Ƿ񴴽����������ӵĻ����ϣ���Y/N��");
		optionStr=reader.nextLine();
		if(optionStr.equals("N")||optionStr.equals("n")) {	
		//��Ҫ����
		while(isGo) {
			System.out.println("����������ID��");
			tempPost=reader.nextInt();
			if(!checkUniqueness(tempPost,-1)) {			
				dic.setDiscussionId(tempPost);
			}
			isNew=true;
		}
		}else if(optionStr.equals("N")||optionStr.equals("n")) {
			System.out.println("�Ѵ��ڵ���������ID��");
			tempPost=reader.nextInt();
			dic.setDiscussionId(tempPost);
		}
		//------------------------------
		System.out.println("���۵�ӰID��");
		dic.setFilmId(reader.nextInt());
		System.out.println("�����û�ID��");
		dic.setUserId(reader.nextInt());
		System.out.println("���۱��⣺");
		dic.setTitle(reader.nextLine());
		System.out.println("�������ݣ�");
		dic.setContent(reader.nextLine());
		
		//��Ҫ����
		//
		while(isGo) {
			System.out.println("����¥�㣺");
		if(isNew==true) {
			dic.setFloor(reader.nextInt());
			break;
		}else if(isNew==false){				
				tempFloor=reader.nextInt();
				if(!checkUniqueness(tempPost,tempFloor)) {			
					dic.setDiscussionId(tempFloor);
				}else {
					System.out.println("��¥���Ѵ��ڣ�����������......");
				}				
			}
		}
	
		//-------------------------
		System.out.println("���ۻظ�¥�㣺");
		dic.setReplyFloor(reader.nextInt());
		System.out.println("�ύʱ�䣺");
		dic.setSubTime(reader.nextLine());
		System.out.println("��������");
		dic.setBeLiked(reader.nextInt());
	}
	
	//����һ������
	private static final String CREATE_DISCUSSION_SQL="INSERT INTO Discussion values(?,?,?,?,?,?,?,?,?)";
	//private static final String GET_IDNum_SQL="select count(Post_ID) from Discussion";
	public void insertDiscussion(Discussion disc) {
		Connection conn=null;
		PreparedStatement pst=null;
		
		try {
			conn=getConnection();
			System.out.println("----------------����ִ�в������-----------------------");
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
			
			
			/*�Զ�����id
			int idnum=getCountId(GET_IDNum_SQL);
			disc.setDiscussionId(idnum+1);//���õ���id�Ž�Film
			pst.setInt(1, disc.getDiscussionId());//����ID�����
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
		System.out.println("--------------���������ִ����ɣ�---------------------");
	}

	//����һ��������Ϣ
	//������SQL���
	//����¥���post_id�ɶ�λ����floor���ɸ���
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
		
		System.out.println("-------------����������Ҫ���µ���������ID----------");
		updateID=reader.nextInt();
		System.out.println("-------------����������Ҫ���µ�¥����----------");
		updateFloor=reader.nextInt();
		System.out.println("---------------��ѡ����Ҫ���µ�����------------------");
		System.out.println("1.����������Ϣ\t2.���µ�ӰID \t3.�����û�ID\t4.�������۱���");
		System.out.println("5.������������\t6.����¥����\t7.�����ύʱ��\t8.���»�����");
		option=reader.nextInt();
		
		Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------����ִ�и��²���-----------------------");
	    try {
	    	conn=getConnection();
	    	switch(option) {
	    	case 1:
	    		//����һ�����۵�ȫ����Ϣͨ��ɾ������ӵķ���
	    		stmt = conn.createStatement();
		    	String sqlDelete="delete from Discussion where Post_ID="+updateID+" and Floor="+updateFloor;
		    	stmt.executeUpdate(sqlDelete);
		    	createDiscussion(disc);
		    	
		    	//ִ��insertDiscussion
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
	    		//���µ�ӰID
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_FILMID);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ�ĵ�ӰID��");
		    	int filmId=reader.nextInt();
		    	pst.setInt(1, filmId);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 3:
	    		//�����û�ID
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_USERID);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ���û�ID��");
		    	int userId=reader.nextInt();
		    	pst.setInt(1, userId);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 4:
	    		//�������۱���
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_TITLE);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ�ı��⣺");
		    	String title=reader.nextLine();
		    	pst.setString(1, title);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 5:
	    		//������������
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_CONTENT);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ�����ݣ�");
		    	String content=reader.nextLine();
		    	pst.setString(1, content);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 6:
	    		//����¥����
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_SUBTIME);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ��¥������");
		    	int floor=reader.nextInt();
		    	pst.setInt(1, floor);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 7:
	    		//���»ظ�¥��
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_REPLYFLOOR);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ�Ļظ�¥�㣺");
		    	String subTime=reader.nextLine();
		    	pst.setString(1, subTime);
		    	pst.execute();
		    	pst.close();
	    	break;
	    	case 8:
	    		//���»�����
	    		pst=conn.prepareStatement(UPDATE_DISCUSSION_BELIKED);
		    	pst.setInt(2, updateID);
		    	pst.setInt(3, updateFloor);
		    	System.out.println("�������޸ĺ�Ļ�������");
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
	    System.out.println("--------------�����²���ִ����ɣ�---------------------");
	    
		
	}
    //ɾ������,��ʱ���ܲ�ֹһ��
	private static final String DELETE_DISCUSSION="delete from Discussion where Post_ID=? and Floor=?";
	public void deleteDiscussion(int discId,int floor) {
		Connection conn = null;
		PreparedStatement pst = null;
		Scanner reader=new Scanner(System.in);
		try {
			conn = getConnection();//�������ݿ�
			System.out.println("----------------����ִ��ɾ������-----------------------");
		
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

		System.out.println("--------------��ɾ������ִ�гɹ���---------------------");
		
	}

	//��ȡһ��������Ϣ
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
		//��������id��ȡ���и�����¥�㣬������¥���������У�
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
