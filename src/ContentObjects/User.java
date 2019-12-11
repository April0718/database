package ContentObjects;
//用户类
public class User {
	private int userID;
	private String phoneNumber;
	private String loginAccount;
	private String email;
	private String password;
	private String nickname;
	private String headimg;
	
	
	public void print() {
		System.out.println("***-------------------用户信息-----------------***");
	    System.out.println("用户ID："+userID);
	    System.out.println("用户名："+loginAccount);
	    System.out.println("手机号码："+phoneNumber);
	    System.out.println("邮箱地址："+email);
	    System.out.println("密码："+password);
	    System.out.println("昵称："+nickname);
	    System.out.println("头像："+headimg);
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
