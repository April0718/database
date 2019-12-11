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
	//查重用
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
	
	//创建一个用户
	public  void createUser(User us)throws IOException{
		Scanner reader=new Scanner(System.in);
		String optionStr=null;
		String temp=null;
		boolean isGo=true;
	    System.out.println("请输入用户信息：");
		//此处需要从数据库调取数据判断是否为可用用户名，
		//用户名作为可登录账号属性应为唯一
		while(isGo) {		    
		    System.out.println("用户名：");
		    temp=reader.nextLine();//temp值为用户名
			if(!checkUniqueness(temp,"account")) {							
				us.setLoginAccount(temp);
				isGo=false;
			}else {
				System.out.println("该用户名已被占用！请重新输入......");
			}
		}
		System.out.println("是否绑定手机号码？（Y/N）");
		optionStr=reader.nextLine();
		if(optionStr.equals("Y")||optionStr.equals("y")) {
			while(isGo) {			
				System.out.println("手机号码：");	
				temp=reader.nextLine();
				if(!checkUniqueness(temp,"phone")) {
					us.setPhoneNumber(temp);
					isGo=false;
				}else {
					System.out.println("该手机号已被绑定！请重新输入......");
				}
			}
		}else if(optionStr.equals("N")||optionStr.equals("n")) {
			us.setPhoneNumber("null");
		}
		System.out.println("是否绑定邮箱地址？（Y/N）");
		optionStr=reader.nextLine();
		if(optionStr.equals("Y")||optionStr.equals("y")) {
			while(isGo) {			
				System.out.println("邮箱地址：");
				temp=reader.nextLine();
				if(!checkUniqueness(temp,"phone")) {
					us.setEmail(temp);
					isGo=false;
				}else {
					System.out.println("该邮箱已被绑定！请重新输入......");
				}
			}
		}else if(optionStr.equals("N")||optionStr.equals("n")) {
			us.setEmail("null");
		}
		System.out.println("密码：");
		us.setPassword(reader.nextLine());
		System.out.println("用户昵称：");
		us.setNickname(reader.nextLine());
		System.out.println("头像URL：");
		us.setHeadimg(reader.nextLine());
		
		reader.close();
	}
	private static final String CREATE_USER_SQL="insert into Users values(?,?,?,?,?,?,?)";
	private static final String GET_IDNum_SQL="select count(User_ID) from Users";
	//插入一个用户
	public void insertUser() {
		Connection conn=null;
		PreparedStatement pst=null;
		User us=new User();	
		try {
			conn=getConnection();
			System.out.println("----------------正在执行插入操作-----------------------");
			
			createUser(us);
			pst=conn.prepareStatement(CREATE_USER_SQL);
			pst.setString(2, us.getLoginAccount());
			pst.setString(3, us.getPhoneNumber());
			pst.setString(4, us.getEmail());
			pst.setString(5, us.getPassword());
			pst.setString(6,us.getNickname() );
			pst.setString(7, us.getHeadimg());
			
			//自动生成id
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
		
		System.out.println("--------------！插入操作执行完成！---------------------");

	}

	//更新一个用户信息
	//更新用SQL语句
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
		
		System.out.println("-------------请您输入需要更新的用户ID----------");
		updateID=reader.nextInt();
		
		System.out.println("---------------请选择您要更新的类型------------------");
		System.out.println("1.更新整体信息\t2.更新用户名 \t3.更新手机号码\t4.更新邮箱地址");
		System.out.println("5.更新用户密码\t6.更新用户昵称 \t7.更新用户头像URL");
		option=reader.nextInt();
		
		Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------正在执行更新操作-----------------------");
	    try {
	    	conn=getConnection();
	    	switch(option) {
	    	case 1:
	    		
	    		//更新一个用户的全部信息通过删除再添加的方法
	    		stmt = conn.createStatement();
		    	String sqlDelete="delete from Users where User_ID="+updateID;
		    	stmt.executeUpdate(sqlDelete);
		    	createUser(us);
		    	
		    	//执行insert
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
	    		//更新用户名
	    		pst=conn.prepareStatement(UPDATE_USER_LOGINACCOUNT);
	    		pst.setInt(2, updateID);
		    	//--------------此处需要查重！
		    	//---------------------------------------
	    		boolean isGo=true;
	    	    System.out.println("请输入修改后的用户名：");
			    String account=reader.nextLine();
			    while(isGo) {				
			    	if(checkUniqueness(account,"account")) {												
			    		us.setLoginAccount(account);					
			    		isGo=false;				
			    	}else {					
			    		System.out.println("该用户名已被占用！请重新输入......");			    		
			    	}
			    }
		    	pst.setString(1, account);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 3:
	    		//更新用户手机号
	    		pst=conn.prepareStatement(UPDATE_USER_PHONENUMBER);
	    		pst.setInt(2, updateID);
	    		
		    	
		    	//--------------此处需要查重！
		    	//---------------------------------------
		    	boolean isGo1=true;
	    	    System.out.println("请输入修改后的手机号：");
			    String phone=reader.nextLine();
			    while(isGo1) {				
			    	if(checkUniqueness(phone,"account")) {												
			    		us.setLoginAccount(phone);					
			    		isGo=false;				
			    	}else {					
			    		System.out.println("该手机号已被绑定！请重新输入......");			    		
			    	}
			    }
		    	pst.setString(1, phone);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 4:
	    		//更新用户邮箱地址
	    		pst=conn.prepareStatement(UPDATE_USER_EMAIL);
	    		pst.setInt(2, updateID);
		    	//--------------此处需要查重！
		    	//---------------------------------------
		    	boolean isGo2=true;
	    	    System.out.println("请输入修改后的用户名：");
			    String email=reader.nextLine();
			    while(isGo2) {				
			    	if(checkUniqueness(email,"account")) {												
			    		us.setLoginAccount(email);					
			    		isGo=false;				
			    	}else {					
			    		System.out.println("该邮箱已被绑定！请重新输入......");			    		
			    	}
			    }
		    	pst.setString(1, email);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 5:
	    		//更新用户密码
	    		pst=conn.prepareStatement(UPDATE_USER_PASSWORD);
	    		pst.setInt(2, updateID);
	    		System.out.println("请输入修改后的密码：");
		    	String psd=reader.nextLine();
		    	pst.setString(1, psd);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 6:
	    		//更新用户昵称
	    		pst=conn.prepareStatement(UPDATE_USER_NICKNAME);
	    		pst.setInt(2, updateID);
	    		System.out.println("请输入修改后的昵称：");
		    	String nickname=reader.nextLine();		
		    	pst.setString(1, nickname);
		    	pst.execute();
		    	pst.close();
	    		break;
	    	case 7:
	    		//更新用户头像URL
	    		pst=conn.prepareStatement(UPDATE_USER_HEADIMG);
	    		pst.setInt(2, updateID);
	    		System.out.println("请输入修改后的头像URL：");
		    	String headimg=reader.nextLine();		
		    	pst.setString(1, headimg);
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

	//删除一个用户
	private static final String DELETE_USER="delete from Users where User_ID=";
	public void deleteUser(int uid) {
		Connection conn = null;
		Statement stmt = null;
		Scanner reader=new Scanner(System.in);
		try {
			conn = getConnection();//连接数据库
			System.out.println("----------------正在执行删除操作-----------------------");
			stmt = conn.createStatement();
			String sql = DELETE_USER+uid;
			stmt.executeUpdate(sql);
			//显式释放资源
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

	//获取一个用户信息
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
			
		    //将查询出的结果从rs里导出放入us中,在此函数中返回结果只有唯一一行
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
		//分析传来的str属于哪一类
		String type=null;//记录类型
		if(str.contains("@")) {
			type="eamil";
		}else if(str.length()==11&&isAllNumber(str)) {
			type="phone";
		}else type="userId";
		
		//对应相关SQL语句进行查询
		String sql="select * from Users where ";
		if(type=="email") {
			sql=sql+"Email_ID='"+str+"'";
		}else if(type=="phone") {
			sql=sql+"Telephone_ID='"+str+"'";
		}else if(type=="userId") {
			sql=sql+"DouBan_ID='"+str+"'";
		}
		
		//SQL语句分装成功，开始查询
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User us=new User();
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
		    //将查询出的结果从rs里导出放入us中,在此函数中返回结果只有唯一一行
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
