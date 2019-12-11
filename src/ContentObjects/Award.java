package ContentObjects;

public class Award {
	private int Fid;//电影ID
	private String Awname;//奖项名
	private String Awtime;//颁奖时间
	
	public int getFid() {
		return Fid;
	}
	public void setFid(int fid) {
		Fid = fid;
	}
	public String getAwname() {
		return Awname;
	}
	public void setAwname(String awname) {
		Awname = awname;
	}
	public String getAwtime() {
		return Awtime;
	}
	public void setAwtime(String awtime) {
		Awtime = awtime;
	}
	
	
}
