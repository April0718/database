package ContentObjects;

public class Actor {
	private int Actor_ID;
	private String Actor_Name;//姓名
	private String Actor_Sex;//性别
	private String Actor_Constellation;//星座
	private String Actor_Birth;//出生日期
	private String Actor_BirthPlace;//出生地
	private String Profession;//职业
	private String Other_ForeignName;//外文名
	private String Other_ChineseName;//中文名
	private String imdb_number;
	private String Offical_Website;//官方网站
	private String Actor_Intro;//简介
	private String Actor_Photo;//照片
	public int getActor_ID() {
		return Actor_ID;
	}
	public void setActor_ID(int actor_ID) {
		Actor_ID = actor_ID;
	}
	public String getActor_Name() {
		return Actor_Name;
	}
	public void setActor_Name(String actor_Name) {
		Actor_Name = actor_Name;
	}
	public String getActor_Sex() {
		return Actor_Sex;
	}
	public void setActor_Sex(String actor_Sex) {
		Actor_Sex = actor_Sex;
	}
	public String getActor_Constellation() {
		return Actor_Constellation;
	}
	public void setActor_Constellation(String actor_Constellation) {
		Actor_Constellation = actor_Constellation;
	}
	public String getActor_Birth() {
		return Actor_Birth;
	}
	public void setActor_Birth(String actor_Birth) {
		Actor_Birth = actor_Birth;
	}
	public String getActor_BirthPlace() {
		return Actor_BirthPlace;
	}
	public void setActor_BirthPlace(String actor_BirthPlace) {
		Actor_BirthPlace = actor_BirthPlace;
	}
	public String getProfession() {
		return Profession;
	}
	public void setProfession(String profession) {
		Profession = profession;
	}
	public String getOther_ForeignName() {
		return Other_ForeignName;
	}
	public void setOther_ForeignName(String other_ForeignName) {
		Other_ForeignName = other_ForeignName;
	}
	public String getOther_ChineseName() {
		return Other_ChineseName;
	}
	public void setOther_ChineseName(String other_ChineseName) {
		Other_ChineseName = other_ChineseName;
	}
	public String getImdb_number() {
		return imdb_number;
	}
	public void setImdb_number(String imdb_number) {
		this.imdb_number = imdb_number;
	}
	public String getOffical_Website() {
		return Offical_Website;
	}
	public void setOffical_Website(String offical_Website) {
		Offical_Website = offical_Website;
	}
	public String getActor_Intro() {
		return Actor_Intro;
	}
	public void setActor_Intro(String actor_Intro) {
		Actor_Intro = actor_Intro;
	}
	public String getActor_Photo() {
		return Actor_Photo;
	}
	public void setActor_Photo(String actor_Photo) {
		Actor_Photo = actor_Photo;
	}
	public void print() {
		System.out.println("------------------演员信息----------------");
		System.out.println("演员姓名："+Actor_Name);
		System.out.println("演员性别："+Actor_Sex);
		System.out.println("演员星座："+Actor_Constellation);
		System.out.println("演员出生日期："+Actor_Birth);
		System.out.println("演员出生地："+Actor_BirthPlace);
		System.out.println("演员职业："+Profession);
		System.out.println("演员外文名："+Other_ForeignName);
		System.out.println("演员中文名："+Other_ChineseName);
		System.out.println("演员imdb编号："+imdb_number);
		System.out.println("演员官方网站："+Offical_Website);
		System.out.println("演员简介："+Actor_Intro);
		System.out.println("演员照片："+Actor_Photo);
		System.out.println("----------------------------------");
	}
}

