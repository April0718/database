package ContentObjects;

public class Actor {
	private int Actor_ID;
	private String Actor_Name;//����
	private String Actor_Sex;//�Ա�
	private String Actor_Constellation;//����
	private String Actor_Birth;//��������
	private String Actor_BirthPlace;//������
	private String Profession;//ְҵ
	private String Other_ForeignName;//������
	private String Other_ChineseName;//������
	private String imdb_number;
	private String Offical_Website;//�ٷ���վ
	private String Actor_Intro;//���
	private String Actor_Photo;//��Ƭ
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
		System.out.println("------------------��Ա��Ϣ----------------");
		System.out.println("��Ա������"+Actor_Name);
		System.out.println("��Ա�Ա�"+Actor_Sex);
		System.out.println("��Ա������"+Actor_Constellation);
		System.out.println("��Ա�������ڣ�"+Actor_Birth);
		System.out.println("��Ա�����أ�"+Actor_BirthPlace);
		System.out.println("��Աְҵ��"+Profession);
		System.out.println("��Ա��������"+Other_ForeignName);
		System.out.println("��Ա��������"+Other_ChineseName);
		System.out.println("��Աimdb��ţ�"+imdb_number);
		System.out.println("��Ա�ٷ���վ��"+Offical_Website);
		System.out.println("��Ա��飺"+Actor_Intro);
		System.out.println("��Ա��Ƭ��"+Actor_Photo);
		System.out.println("----------------------------------");
	}
}

