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

	//����һ����Ӱ��,׼ȷ��������ע���Ӱ��Ϣ�������fi��
	public static void createFilm(Film fi)throws IOException{
		
		Scanner reader=new Scanner(System.in);
		System.out.println("�������Ӱ��Ϣ��");
		System.out.println("��Ӱ���֣�");
		fi.setFilmName(reader.nextLine());
		System.out.println("��Ӱ������");
		fi.setPoster(reader.nextLine());
		System.out.println("���ݣ�");
		fi.setDirector(reader.nextLine());
		System.out.println("��磺");
		fi.setScriptwriter(reader.nextLine());
		System.out.println("Ƭ����");
		fi.setFilmTime(reader.nextInt());
		System.out.println("���ԣ�");
		fi.setLanguage(reader.nextLine());
		System.out.println("��ӳ���ڣ�xxxx-xx-xx)��");
		fi.setRaleaseDate(reader.nextLine());
		System.out.println("��Ƭ����/������");
		fi.setCountry(reader.nextLine());
		System.out.println("����/������");
		fi.setOtherName(reader.nextLine());
		System.out.println("IMDb���ӣ�");
		fi.setIMDb(reader.nextLine());
		System.out.println("�����飺");
		fi.setSynopsis(reader.nextLine());
	}
	
	private static final String CREATE_FILM_SQL="INSERT INTO Films values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_IDNum_SQL="select count(Film_ID) from Films";
	
	//����һ����Ӱ	
    public void insertFilm(Film fi) {
		Connection conn=null;
		PreparedStatement pst=null;
		/*Statement temp=null;
		ResultSet rs=null;*/
		
		try {
			conn=getConnection();
			System.out.println("----------------����ִ�в������-----------------------");
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
			
			
			//�Զ�����id
			int idnum=getCountId(GET_IDNum_SQL);
			fi.setFilmID(idnum+1);//���õ���id�Ž�Film
			pst.setInt(1, fi.getFilmID());//����ID�����
			
			/*
			int idnum=-1;
			temp=conn.createStatement();
			rs=temp.executeQuery(GET_IDNum_SQL);
			while(rs.next()) {
				idnum=rs.getInt("finum");
			}
			fi.setFilmID(idnum+1);//���õ���id�Ž�Film
			temp.close();
			rs.close();//����finally��û��д�ж��Ƿ��Ѿ��ر�
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

		System.out.println("--------------���������ִ����ɣ�---------------------");
	}

	//����һ����Ӱ��Ϣ
	//������SQL���	
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
		
		System.out.println("-------------����������Ҫ���µĵ�ӰID----------");
		updateID=reader.nextInt();
		
		System.out.println("---------------��ѡ����Ҫ���µ�����------------------");
		System.out.println("1.����������Ϣ\t2.���µ�Ӱ���� \t3.���µ�����\t4.���±����");
		System.out.println("5.����Ƭ��\t6.�������� \t7.������ӳ����\t8.������Ƭ����/����");
		System.out.println("9.���±���/����\t10.����IMDb���� \t11.���¾�����\t12.���º���RUL");	    
	    option=reader.nextInt();
	   
	    Connection conn=null;
	    Statement stmt=null;
	    PreparedStatement pst=null;
	    System.out.println("----------------����ִ�и��²���-----------------------");
	    //ResultSet rs=null;
	    try {
	    	conn=getConnection();
	    		    	
	    switch(option){
	    case 1:
	    	//����һ����Ӱ��ȫ����Ϣͨ��ɾ������ӵķ���
	    	stmt = conn.createStatement();
	    	String sqlDelete="delete from Films where Fid="+updateID;
	    	stmt.executeUpdate(sqlDelete);
	    	createFilm(fi);
            
	    	//ִ��insertFilm
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
	    	//����һ����Ӱ������
	    	pst=conn.prepareStatement(UPDATE_FILM_NAME);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ĵ�Ӱ����");
	    	String name=reader.nextLine();
	    	pst.setString(1, name);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 3:
	    	//����һ����Ӱ�ĵ�����
	    	pst=conn.prepareStatement(UPDATE_FILM_DIRECTOR);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ĵ�������");
	    	String director=reader.nextLine();
	    	pst.setString(1, director);
	    	pst.execute();
	    	pst.close();	    	
	    	break;
	    case 4:
	    	//����һ����Ӱ�ı����
	    	pst=conn.prepareStatement(UPDATE_FILM_WRITER);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ı������");
	    	String writer=reader.nextLine();
	    	pst.setString(1, writer);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 5:
	    	//����һ����Ӱ��Ƭ��
	    	pst=conn.prepareStatement(UPDATE_FILM_LONG);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ��Ƭ����");
	    	int time=reader.nextInt();
	    	pst.setInt(1, time);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 6:
	    	//����һ����Ӱ������
	    	pst=conn.prepareStatement(UPDATE_FILM_LANGUAGE);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�����ԣ�");
	    	String language=reader.nextLine();
	    	pst.setString(1,language);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 7:
	    	//����һ����Ӱ����ӳ����
	    	pst=conn.prepareStatement(UPDATE_FILM_DATE);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ����ӳ���ڣ�");
	    	String date=reader.nextLine();
	    	pst.setString(1,date);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 8:
	    	//����һ����Ӱ�Ĺ���/����
	    	pst=conn.prepareStatement(UPDATE_FILM_COUNTRY);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�Ĺ���/������");
	    	String country=reader.nextLine();
	    	pst.setString(1,country);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 9:
	    	//����һ����Ӱ�ı���/����
	    	pst=conn.prepareStatement(UPDATE_FILM_OTRNAME);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ı���/������");
	    	String otrname=reader.nextLine();
	    	pst.setString(1,otrname);
	    	pst.execute();
	    	pst.close();
	    	break;
	    case 10:
	    	//����һ����Ӱ��IMDb����
	    	pst=conn.prepareStatement(UPDATE_FILM_IMDB);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ��IMDb���ӣ�");
	    	String IMDb=reader.nextLine();
	    	pst.setString(1,IMDb);
	    	pst.execute();
	    	pst.close();
	    	break;
        case 11:
        	//����һ����Ӱ�ľ�����
	    	pst=conn.prepareStatement(UPDATE_FILM_INTR);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ľ����飺");
	    	String intr=reader.nextLine();
	    	pst.setString(1,intr);
	    	pst.execute();
	    	pst.close();
	    	break;	
        case 12:
        	//����һ����Ӱ�ĺ���
	    	pst=conn.prepareStatement(UPDATE_FILM_POSTER);
	    	pst.setInt(2, updateID);
	    	
	    	System.out.println("�������޸ĺ�ĺ���URL��");
	    	String poster=reader.nextLine();
	    	pst.setString(1,poster);
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

	private static final String DELETE_FILM="delete from Films where Film_ID=";
	//ɾ��һ����Ӱ
	public void deleteFilm(int fid) {
		Connection conn = null;
		Statement stmt = null;
		Scanner reader=new Scanner(System.in);
		//int Fid ;
		try {
			conn = getConnection();//�������ݿ�
			System.out.println("----------------����ִ��ɾ������-----------------------");
			//System.out.println("������Ҫɾ���ĵ�ӰID��");		 
	 		//Fid=reader.nextInt();
			stmt = conn.createStatement();
			String sql = DELETE_FILM+fid;
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

	private static final String GET_FILM="select * from Films where Film_ID=";
	//��ȡһ����Ӱ��Ϣ
	public Film getFilm(int fid) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Film fi=new Film();
		//Scanner reader=new Scanner(System.in);
		
		//System.out.println("����������Ҫ��ѯ��Ӱ��ID��");
		//int ID;
		//ID=reader.nextInt();
		String sql=GET_FILM+fid;
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
		    //����ѯ���Ľ����rs�ﵼ������fi��,�ڴ˺����з��ؽ��ֻ��Ψһһ��
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
				//fi.print();//����Ӱ��Ϣ���
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
		
		//System.out.println("����������Ҫ��ѯ��Ӱ����");
		//String fname;
		//fname=reader.nextLine();
		String sql=GET_FILM_BYNAME+"'"+fiName+"'";
		try {
			conn = getConnection();
			stmt = conn.createStatement();	
			rs=stmt.executeQuery(sql);
			
			//����ѯ���Ľ����rs�ﵼ������fi��,�ڴ˺����з��ؽ�������ж���
			//ͨ��next�������ж�ȡ
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
				//fi.print();//����Ӱ��Ϣ���
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
