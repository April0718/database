package ContentObjects;
import java.util.ArrayList;
import java.util.List;

import ConnectedDao.*;
//��Ӱ

public class Film {

	private int filmID;
	private String filmName;
	private String poster;
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}

	private String director;
	private String scriptwriter;
	private int filmTime;
	private String language;
	private String raleaseDate;
	private String country;
	private String otherName;
	private String IMDb;
	private String synopsis;
	

	public int getFilmID() {
		return filmID;
	}
	public void setFilmID(int filmID) {
		this.filmID = filmID;
	}
	public String getFilmName() {
		return filmName;
	}
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getScriptwriter() {
		return scriptwriter;
	}
	public void setScriptwriter(String scriptwriter) {
		this.scriptwriter = scriptwriter;
	}
	public int getFilmTime() {
		return filmTime;
	}
	public void setFilmTime(int filmTime) {
		this.filmTime = filmTime;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRaleaseDate() {
		return raleaseDate;
	}
	public void setRaleaseDate(String raleaseDate) {
		this.raleaseDate = raleaseDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getIMDb() {
		return IMDb;
	}
	public void setIMDb(String iMDb) {
		IMDb = iMDb;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public void print() {
	    System.out.println("***---------------------��Ӱ��Ϣ-------------------***");
	    System.out.println("��ӰID��"+filmID);
	    System.out.println("��Ӱ���ƣ�"+filmName);
	    System.out.println("��Ӱ����URL��"+poster);
	    //-------------------------------------------
	    System.out.print("��Ӱ���ͣ�");
	    //��ȡ��Ӱ����
	    List<Type> types=new ArrayList<Type>();
	    if(types!=null) {
		    types=DaoFactory.getInstance().getTypeDAO().getType(filmID);
		    //��ӡ��Ӱ����
		    DaoFactory.getInstance().getTypeDAO().print(types);
	    }
	    System.out.println();
	    //-------------------------------------------
	    System.out.println("���ݣ�"+director);
	    System.out.println("��磺"+scriptwriter);
	    System.out.println("Ƭ����"+filmTime+"����");
	    System.out.println("���ԣ�"+language);
	    System.out.println("��ӳ���ڣ�"+raleaseDate);
	    System.out.println("��Ƭ����/������"+country);
	    System.out.println("����/������"+otherName);
	    System.out.println("IMDb���ӣ�"+IMDb);
	    System.out.println("�����飺");
	    System.out.println(synopsis);
	}

}
