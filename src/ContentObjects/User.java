package ContentObjects;
//�û���
public class User {
	private int userID;
	private String phoneNumber;
	private String loginAccount;
	private String email;
	private String password;
	private String nickname;
	private String headimg;
	
	
	public void print() {
		System.out.println("***-------------------�û���Ϣ-----------------***");
	    System.out.println("�û�ID��"+userID);
	    System.out.println("�û�����"+loginAccount);
	    System.out.println("�ֻ����룺"+phoneNumber);
	    System.out.println("�����ַ��"+email);
	    System.out.println("���룺"+password);
	    System.out.println("�ǳƣ�"+nickname);
	    System.out.println("ͷ��"+headimg);
	    System.out.println("***--------------------------------------------***");
	}
	
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
