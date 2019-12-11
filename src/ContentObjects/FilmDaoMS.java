package ContentObjects;
import ConnectedDao.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class FilmDaoMS extends DAOBase implements FilmDAO {

	//创建一个电影类,准确点描述是注入电影信息到传入的fi中
	public static void createFilm(Film fi)throws IOException{
		
		Scanner reader=new Scanner(System.in);
		System.out.println("请输入电影信息：");
		System.out.println("电影名字：");
		fi.setFilmName(reader.nextLine());
		System.out.println("电影海报：");
		fi.setPoster(reader.nextLine());
		System.out.println("导演：");
		fi.setDirector(reader.nextLine());
		System.out.println("编剧：");
		fi.setScriptwriter(reader.nextLine());
		System.out.println("片长：");
		fi.setFilmTime(reader.nextInt());
		System.out.println("语言：");
		fi.setLanguage(reader.nextLine());
		System.out.println("上映日期（xxxx-xx-xx)：");
		fi.setRaleaseDate(reader.nextLine());
		System.out.println("制片国家/地区：");
		fi.setCountry(reader.nextLine());
		System.out.println("又名/别名：");
		fi.setOtherName(reader.nextLine());
		System.out.println("IMDb链接：");
		fi.setIMDb(reader.nextLine());
		System.out.println("剧情简介：");
		fi.setSynopsis(reader.nextLine());
	}
	
	private static final String CREATE_FILM_SQL="INSERT INTO Films values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_IDNum_SQL="select count(Film_ID) from Films";
	
	//插入一个电影	
    public void insertFilm(Film fi) {
		Connection conn=null;
		PreparedStatement pst=null;
		/*Statement temp=null;
		ResultSet rs=null;*/
		
		try {
			conn=getConnection();
			System.out.println("----------------正在执行插入操作-----------------------");
			if(fi==null)
			   createFilm(fi);

			pst=conn.prepareStatement(CREATE_FILM_SQL);
			
			pst.setString(2, fi.getFilmName());
			pst.setString(3, fi.getPoster());
			pst.setString(4, fi.getDirector());			
			pst.setString(5, fi.getScriptwriter());
			pst.setInt(6, fi.getFilmTime());
			pst.setString(7, fi.getLanguage());
			pst.setString(8, fi.getRaleaseDate());
			pst.setString(9, fi.getCountry());
			pst.setString(10, fi.getOtherName());
			pst.setString(11, fi.getIMDb());
			pst.setString(12, fi.getSynopsis());
			
			
			//自动生成id
			int idnum=getCountId(GET_IDNum_SQL);
			fi.setFilmID(idnum+1);//将得到的id放进Film
			pst.setInt(1, fi.getFilmID());//生成ID后插入
			
			/*
			int idnum=-1;
			temp=conn.createStatement();
			rs=temp.executeQuery(GET_IDNum_SQL);
			while(rs.next()) {
				idnum=rs.getInt("finum");
			}
			fi.setFilmID(idnum+1);//将得到的id放进Film
			temp.close();
			rs.close();//后面finally还没有写判定是否已经关闭
			*/
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

	//更新一条电影信息
	//更新用SQL语句	
	private static final String UPDATE_FILM_NAME="update Films set Film_Name=? where Film_ID=?";
	private static final String UPDATE_FILM_DIRECTOR="update Films set Film_Director=? where Film_ID=?";
	private static final String UPDATE_FILM_WRITER="update Films set Writer=? where Film_ID=?";
	private static final String UPDATE_FILM_LONG="update Films set Show_Time=? where Film_ID=?";
	private static final String UPDATE_FILM_LANGUAGE="update Films set Language=? where Film_ID=?";
	private static final String UPDATE_FILM_DATE="update Films set Realease_info=? where Film_ID=?";
	private static final String UPDATE_FILM_COUNTRY="update Films set Studios=? where Film_ID=?";
	private static final String UPDATE_FILM_OTRNAME="update Films set Alias=? where Film_ID=?";
	private static final String UPDATE_FILM_IMDB="update Films set IMDb=? where Film_ID=?";
	private static final String UPDATE_FILM_INTR="update Films set Introduce=? where Film_ID=?";
	private static final String UPDATE_FILM_POSTER="update Films set Film_Poster=? where Film_ID=?";
	public void updateFilm() {
		Film fi= new Film();
		int updateID;
		int option;
		Scanner reader=new Scanner(System.in);
		
		System.out.println("-------------请您输入需要更新的电影ID----------");
		updateID=reader.nextInt();
		
		System.out.println("---------------请选择您要更新的类型------------------");
		System.out.println("1.更新整体信息\t2.更新电影名字 \t3.更新导演名\t4.更新编剧名");
		System.out.println("5.更新片长\t6.更新语言 \t7.更新上映日期\t8.更新制片国家/地区");
		System.out.println("9.更新别名/又名\t10.更新IMDb链接 \t11.更新剧情简介\t12.更新海报RUL");	    
	    option=reader.nextInt();
	   
	    Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------正在执行更新操作-----------------------");
	    //ResultSet rs=null;
	    try {
	    	conn=getConnection();
	    		    	
	    switch(option){
	    case 1:
	    	//更新一个电影的全部信息通过删除再添加的方法
	    	stmt = conn.createStatement();
	    	String sqlDelete="delete from Films where Fid="+updateID;
	    	stmt.executeUpdate(sqlDelete);
	    	createFilm(fi);
            
	    	//执行insertFilm
	    	pst=conn.prepareStatement(CREATE_FILM_SQL);
	    	pst.setInt(1, updateID);
			pst.setString(2, fi.getFilmName());
			pst.setString(3, fi.getDirector());
			pst.setString(4, fi.getScriptwriter());
			pst.setInt(5, fi.getFilmTime());
			pst.setString(6, fi.getLanguage());
			pst.setString(7, fi.getRaleaseDate());
			pst.setString(8, fi.getCountry());
			pst.setString(9, fi.getOtherName());
			pst.setString(10, fi.getIMDb());
			pst.setString(11, fi.getSynopsis());
			pst.execute();
			pst.close();
			stmt.close();
	    	break;
	    case 2:
	    	//更新一个电影的名字
	    	pst=conn.prepareStatement(UPDATE_FILM_NAME);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的电影名：");
	    	String name=reader.nextLine();
	    	pst.setString(1, name);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 3:
	    	//更新一个电影的导演名
	    	pst=conn.prepareStatement(UPDATE_FILM_DIRECTOR);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的导演名：");
	    	String director=reader.nextLine();
	    	pst.setString(1, director);
	    	pst.execute();
	    	pst.close();	    	
	    	break;
	    case 4:
	    	//更新一个电影的编剧名
	    	pst=conn.prepareStatement(UPDATE_FILM_WRITER);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的编剧名：");
	    	String writer=reader.nextLine();
	    	pst.setString(1, writer);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 5:
	    	//更新一个电影的片长
	    	pst=conn.prepareStatement(UPDATE_FILM_LONG);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的片长：");
	    	int time=reader.nextInt();
	    	pst.setInt(1, time);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 6:
	    	//更新一个电影的语言
	    	pst=conn.prepareStatement(UPDATE_FILM_LANGUAGE);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的语言：");
	    	String language=reader.nextLine();
	    	pst.setString(1,language);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 7:
	    	//更新一个电影的上映日期
	    	pst=conn.prepareStatement(UPDATE_FILM_DATE);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的上映日期：");
	    	String date=reader.nextLine();
	    	pst.setString(1,date);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 8:
	    	//更新一个电影的国家/地区
	    	pst=conn.prepareStatement(UPDATE_FILM_COUNTRY);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的国家/地区：");
	    	String country=reader.nextLine();
	    	pst.setString(1,country);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 9:
	    	//更新一个电影的别名/又名
	    	pst=conn.prepareStatement(UPDATE_FILM_OTRNAME);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的别名/又名：");
	    	String otrname=reader.nextLine();
	    	pst.setString(1,otrname);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 10:
	    	//更新一个电影的IMDb链接
	    	pst=conn.prepareStatement(UPDATE_FILM_IMDB);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的IMDb链接：");
	    	String IMDb=reader.nextLine();
	    	pst.setString(1,IMDb);
	    	pst.execute();
	    	pst.close();
	    	break;
        case 11:
        	//更新一个电影的剧情简介
	    	pst=conn.prepareStatement(UPDATE_FILM_INTR);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的剧情简介：");
	    	String intr=reader.nextLine();
	    	pst.setString(1,intr);
	    	pst.execute();
	    	pst.close();
	    	break;	
        case 12:
        	//更新一个电影的海报
	    	pst=conn.prepareStatement(UPDATE_FILM_POSTER);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("请输入修改后的海报URL：");
	    	String poster=reader.nextLine();
	    	pst.setString(1,poster);
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

	private static final String DELETE_FILM="delete from Films where Film_ID=";
	//删除一个电影
	public void deleteFilm(int fid) {
		Connection conn = null;
		Statement stmt = null;
		Scanner reader=new Scanner(System.in);
		//int Fid ;
		try {
			conn = getConnection();//连接数据库
			System.out.println("----------------正在执行删除操作-----------------------");
			//System.out.println("请输入要删除的电影ID：");		 
	 		//Fid=reader.nextInt();
			stmt = conn.createStatement();
			String sql = DELETE_FILM+fid;
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

	private static final String GET_FILM="select * from Films where Film_ID=";
	//获取一个电影信息
	public Film getFilm(int fid) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Film fi=new Film();
		//Scanner reader=new Scanner(System.in);
		
		//System.out.println("请输入您索要查询电影的ID：");
		//int ID;
		//ID=reader.nextInt();
		String sql=GET_FILM+fid;
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
		    //将查询出的结果从rs里导出放入fi中,在此函数中返回结果只有唯一一行
			while(rs.next()) {
				fi.setFilmID(fid);
				fi.setFilmName(rs.getString("Film_Name"));
				fi.setPoster(rs.getString("Film_Poster"));
				fi.setDirector(rs.getString("Director"));
				fi.setScriptwriter(rs.getString("Writer"));
				fi.setFilmTime(rs.getInt("Show_Time"));
				fi.setLanguage(rs.getString("Language"));
				fi.setRaleaseDate(rs.getString("Release_info"));
				fi.setCountry(rs.getString("Studios"));
				fi.setOtherName(rs.getString("Alias"));
				fi.setIMDb(rs.getString("IMDb"));
				fi.setSynopsis(rs.getString("Introduce"));
				//fi.print();//将电影信息输出
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
          return fi;
		
	}
	
	private static final String GET_FILM_BYNAME="select * from Films where Film_Name=";
	public ArrayList<Film> getFilmByName(String fiName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Film> list=new ArrayList<Film>();
		Film fi=new Film();
		Scanner reader=new Scanner(System.in);
		
		//System.out.println("请输入您索要查询电影名：");
		//String fname;
		//fname=reader.nextLine();
		String sql=GET_FILM_BYNAME+"'"+fiName+"'";
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
			//将查询出的结果从rs里导出放入fi中,在此函数中返回结果可能有多行
			//通过next（）逐行读取
			while(rs.next()) {
				fi.setFilmID(rs.getInt("Film_ID"));
				fi.setFilmName(fiName);
				fi.setPoster(rs.getString("Film_Poster"));
				fi.setDirector(rs.getString("Director"));
				fi.setScriptwriter(rs.getString("Writer"));
				fi.setFilmTime(rs.getInt("Show_Time"));
				fi.setLanguage(rs.getString("Language"));
				fi.setRaleaseDate(rs.getString("Release_info"));
				fi.setCountry(rs.getString("Studios"));
				fi.setOtherName(rs.getString("Alias"));
				fi.setIMDb(rs.getString("IMDb"));
				fi.setSynopsis(rs.getString("Introduce"));
				//fi.print();//将电影信息输出
				list.add(fi);
			}
						
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
