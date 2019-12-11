package ContentObjects;
import ConnectedDao.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Scanner;
public class UserDaoMS extends DAOBase implements UserDAO {

	private static final String CHECK_USERID_SQL="select User_ID from Users where User_ID=";
	private static final String CHECK_PHONE_SQL="select Telephone_ID from Users where Telephone_ID=";
	private static final String CHECK_EMAIL_SQL="select Email_ID from Users where Email_ID=";
	//������
	public  boolean checkUniqueness(String str,String type) {
		boolean answer=false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			
		    String sql=null;
		   
			if(type.equals("account")) {
			sql=CHECK_USERID_SQL+str;
		
			}else if(type.equals("phone")) {
			sql=CHECK_PHONE_SQL+str;
		
			}else if(type.equals("email")) {
			sql=CHECK_EMAIL_SQL+str;
		
			}
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				answer=true;
			}
			
			
			}catch(Exception e) {
				e.printStackTrace();
		}
		return answer;
	}
	
	//����һ���û�
	public  void createUser(User us)throws IOException{
		Scanner reader=new Scanner(System.in);
		String optionStr=null;
		String temp=null;
		boolean isGo=true;
	    System.out.println("�������û���Ϣ��");
		//�˴���Ҫ�����ݿ��ȡ�����ж��Ƿ�Ϊ�����û�����
		//�û�����Ϊ�ɵ�¼�˺�����ӦΪΨһ
		while(isGo) {		    
		    System.out.println("�û�����");
		    temp=reader.nextLine();//tempֵΪ�û���
			if(!checkUniqueness(temp,"account")) {							
				us.setLoginAccount(temp);
				isGo=false;
			}else {
				System.out.println("���û����ѱ�ռ�ã�����������......");
			}
		}
		System.out.println("�Ƿ���ֻ����룿��Y/N��");
		optionStr=reader.nextLine();
		if(optionStr.equals("Y")||optionStr.equals("y")) {
			while(isGo) {			
				System.out.println("�ֻ����룺");	
				temp=reader.nextLine();
				if(!checkUniqueness(temp,"phone")) {
					us.setPhoneNumber(temp);
					isGo=false;
				}else {
					System.out.println("���ֻ����ѱ��󶨣�����������......");
				}
			}
		}else if(optionStr.equals("N")||optionStr.equals("n")) {
			us.setPhoneNumber("null");
		}
		System.out.println("�Ƿ�������ַ����Y/N��");
		optionStr=reader.nextLine();
		if(optionStr.equals("Y")||optionStr.equals("y")) {
			while(isGo) {			
				System.out.println("�����ַ��");
				temp=reader.nextLine();
				if(!checkUniqueness(temp,"phone")) {
					us.setEmail(temp);
					isGo=false;
				}else {
					System.out.println("�������ѱ��󶨣�����������......");
				}
			}
		}else if(optionStr.equals("N")||optionStr.equals("n")) {
			us.setEmail("null");
		}
		System.out.println("���룺");
		us.setPassword(reader.nextLine());
		System.out.println("�û��ǳƣ�");
		us.setNickname(reader.nextLine());
		System.out.println("ͷ��URL��");
		us.setHeadimg(reader.nextLine());
		
		reader.close();
	}
	private static final String CREATE_USER_SQL="insert into Users values(?,?,?,?,?,?,?)";
	private static final String GET_IDNum_SQL="select count(User_ID) from Users";
	//����һ���û�
	public void insertUser() {
		Connection conn=null;
		PreparedStatement pst=null;
		User us=new User();	
		try {
			conn=getConnection();
			System.out.println("----------------����ִ�в������-----------------------");
			
			createUser(us);
			pst=conn.prepareStatement(CREATE_USER_SQL);
			pst.setString(2, us.getLoginAccount());
			pst.setString(3, us.getPhoneNumber());
			pst.setString(4, us.getEmail());
			pst.setString(5, us.getPassword());
			pst.setString(6,us.getNickname() );
			pst.setString(7, us.getHeadimg());
			
			//�Զ�����id
			int idnum=getCountId(GET_IDNum_SQL);
			us.setUserID(idnum+1);
			pst.setInt(1, us.getUserID());
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
		
		System.out.println("--------------���������ִ����ɣ�---------------------");

	}

	//����һ���û���Ϣ
	//������SQL���
	private static final String UPDATE_USER_LOGINACCOUNT="update Users set Douban_ID=? where User_ID=?";
	private static final String UPDATE_USER_PHONENUMBER="update Users set Telephone_ID=? where User_ID=?";
	private static final String UPDATE_USER_EMAIL="update Users set Email_ID=? where User_ID=?";
	private static final String UPDATE_USER_PASSWORD="update Users set Password=? where User_ID=?";
	private static final String UPDATE_USER_NICKNAME="update Users set Nickname=? where User_ID=?";
	private static final String UPDATE_USER_HEADIMG="update Users set Headimg=? where User_ID=?";
	public void updateUser() {
		int updateID;
		int option;
		User us=new User();
		Scanner reader=new Scanner(System.in);
		
		System.out.println("-------------����������Ҫ���µ��û�ID----------");
		updateID=reader.nextInt();
		
		System.out.println("---------------��ѡ����Ҫ���µ�����------------------");
		System.out.println("1.����������Ϣ\t2.�����û��� \t3.�����ֻ�����\t4.���������ַ");
		System.out.println("5.�����û�����\t6.�����û��ǳ� \t7.�����û�ͷ��URL");
		option=reader.nextInt();
		
		Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------����ִ�и��²���-----------------------");
	    try {
	    	conn=getConnection();
	    	switch(option) {
	    	case 1:
	    		
	    		//����һ���û���ȫ����Ϣͨ��ɾ������ӵķ���
	    		stmt = conn.createStatement();
		    	String sqlDelete="delete from Users where User_ID="+updateID;
		    	stmt.executeUpdate(sqlDelete);
		    	createUser(us);
		    	
		    	//ִ��insert
		    	pst=conn.prepareStatement(CREATE_USER_SQL);
		    	pst.setInt(1, updateID);
		    	pst.setString(2, us.getLoginAccount());
				pst.setString(3, us.getPhoneNumber());
				pst.setString(4, us.getEmail());
				pst.setString(5, us.getPassword());
				pst.setString(6,us.getNickname() );
				pst.setString(7, us.getHeadimg());
				pst.execute();
				pst.close();
				stmt.close();
		    			    	
	    		break;
	    	case 2:
	    		//�����û���
	    		pst=conn.prepareStatement(UPDATE_USER_LOGINACCOUNT);
	    		pst.setInt(2, updateID);
		    	//--------------�˴���Ҫ���أ�
		    	//---------------------------------------
	    		boolean isGo=true;
	    	    System.out.println("�������޸ĺ���û�����");
			    String account=reader.nextLine();
			    while(isGo) {				
			    	if(checkUniqueness(account,"account")) {												
			    		us.setLoginAccount(account);					
			    		isGo=false;				
			    	}else {					
			    		System.out.println("���û����ѱ�ռ�ã�����������......");			    		
			    	}
			    }
		    	pst.setString(1, account);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 3:
	    		//�����û��ֻ���
	    		pst=conn.prepareStatement(UPDATE_USER_PHONENUMBER);
	    		pst.setInt(2, updateID);
	    		
		    	
		    	//--------------�˴���Ҫ���أ�
		    	//---------------------------------------
		    	boolean isGo1=true;
	    	    System.out.println("�������޸ĺ���ֻ��ţ�");
			    String phone=reader.nextLine();
			    while(isGo1) {				
			    	if(checkUniqueness(phone,"account")) {												
			    		us.setLoginAccount(phone);					
			    		isGo=false;				
			    	}else {					
			    		System.out.println("���ֻ����ѱ��󶨣�����������......");			    		
			    	}
			    }
		    	pst.setString(1, phone);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 4:
	    		//�����û������ַ
	    		pst=conn.prepareStatement(UPDATE_USER_EMAIL);
	    		pst.setInt(2, updateID);
		    	//--------------�˴���Ҫ���أ�
		    	//---------------------------------------
		    	boolean isGo2=true;
	    	    System.out.println("�������޸ĺ���û�����");
			    String email=reader.nextLine();
			    while(isGo2) {				
			    	if(checkUniqueness(email,"account")) {												
			    		us.setLoginAccount(email);					
			    		isGo=false;				
			    	}else {					
			    		System.out.println("�������ѱ��󶨣�����������......");			    		
			    	}
			    }
		    	pst.setString(1, email);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 5:
	    		//�����û�����
	    		pst=conn.prepareStatement(UPDATE_USER_PASSWORD);
	    		pst.setInt(2, updateID);
	    		System.out.println("�������޸ĺ�����룺");
		    	String psd=reader.nextLine();
		    	pst.setString(1, psd);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 6:
	    		//�����û��ǳ�
	    		pst=conn.prepareStatement(UPDATE_USER_NICKNAME);
	    		pst.setInt(2, updateID);
	    		System.out.println("�������޸ĺ���ǳƣ�");
		    	String nickname=reader.nextLine();		
		    	pst.setString(1, nickname);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 7:
	    		//�����û�ͷ��URL
	    		pst=conn.prepareStatement(UPDATE_USER_HEADIMG);
	    		pst.setInt(2, updateID);
	    		System.out.println("�������޸ĺ��ͷ��URL��");
		    	String headimg=reader.nextLine();		
		    	pst.setString(1, headimg);
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

	//ɾ��һ���û�
	private static final String DELETE_USER="delete from Users where User_ID=";
	public void deleteUser(int uid) {
		Connection conn = null;
		Statement stmt = null;
		Scanner reader=new Scanner(System.in);
		try {
			conn = getConnection();//�������ݿ�
			System.out.println("----------------����ִ��ɾ������-----------------------");
			stmt = conn.createStatement();
			String sql = DELETE_USER+uid;
			stmt.executeUpdate(sql);
			//��ʽ�ͷ���Դ
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

	//��ȡһ���û���Ϣ
	private static final String GET_USER="select * from Users where User_ID=";
	public User getUser(int uid) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User us=new User();
		String sql=GET_USER+uid;
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
		    //����ѯ���Ľ����rs�ﵼ������us��,�ڴ˺����з��ؽ��ֻ��Ψһһ��
			while(rs.next()) {
				us.setUserID(uid);
				us.setLoginAccount(rs.getString("Douban_ID"));
				us.setPhoneNumber(rs.getString("Telephone_ID"));
				us.setEmail(rs.getString("Email_ID"));
				us.setPassword(rs.getString("Password"));
				us.setNickname(rs.getString("Nickname"));
				us.setHeadimg(rs.getString("Headimg"));
				//us.print();
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
	        return us;
	}

	public boolean isAllNumber(String str) {
		for (int i = 0; i < str.length(); i++){
			   if (!Character.isDigit(str.charAt(i))){
			    return false;
			   }
			  }
			  return true;

	}
	
	public User getUser(String str) {
		//����������str������һ��
		String type=null;//��¼����
		if(str.contains("@")) {
			type="eamil";
		}else if(str.length()==11&&isAllNumber(str)) {
			type="phone";
		}else type="userId";
		
		//��Ӧ���SQL�����в�ѯ
		String sql="select * from Users where ";
		if(type=="email") {
			sql=sql+"Email_ID='"+str+"'";
		}else if(type=="phone") {
			sql=sql+"Telephone_ID='"+str+"'";
		}else if(type=="userId") {
			sql=sql+"DouBan_ID='"+str+"'";
		}
		
		//SQL����װ�ɹ�����ʼ��ѯ
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User us=new User();
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
		    //����ѯ���Ľ����rs�ﵼ������us��,�ڴ˺����з��ؽ��ֻ��Ψһһ��
			while(rs.next()) {
				us.setUserID(rs.getInt("User_ID"));
				us.setLoginAccount(rs.getString("Douban_ID"));
				us.setPhoneNumber(rs.getString("Telephone_ID"));
				us.setEmail(rs.getString("Email_ID"));
				us.setPassword(rs.getString("Password"));
				us.setNickname(rs.getString("Nickname"));
				us.setHeadimg(rs.getString("Headimg"));
				//us.print();
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
	        return us;
	}
}
