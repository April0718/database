package ContentObjects;
import java.util.ArrayList;
import java.util.List;

import ConnectedDao.*;
//电影

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
	    System.out.println("***---------------------电影信息-------------------***");
	    System.out.println("电影ID："+filmID);
	    System.out.println("电影名称："+filmName);
	    System.out.println("电影海报URL："+poster);
	    //-------------------------------------------
	    System.out.print("电影类型：");
	    //获取电影类型
	    List<Type> types=new ArrayList<Type>();
	    if(types!=null) {
		    types=DaoFactory.getInstance().getTypeDAO().getType(filmID);
		    //打印电影类型
		    DaoFactory.getInstance().getTypeDAO().print(types);
	    }
	    System.out.println();
	    //-------------------------------------------
	    System.out.println("导演："+director);
	    System.out.println("编剧："+scriptwriter);
	    System.out.println("片长："+filmTime+"分钟");
	    System.out.println("语言："+language);
	    System.out.println("上映日期："+raleaseDate);
	    System.out.println("制片国家/地区："+country);
	    System.out.println("别名/又名："+otherName);
	    System.out.println("IMDb链接："+IMDb);
	    System.out.println("剧情简介：");
	    System.out.println(synopsis);
	}

}
