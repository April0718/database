package DouBan;

public class Comment {
	private int Uid;
	private int Fid;
	private String C_content;
	private int startlevel;
	private String terminal;
	private int otherlike;
	public int getUid() {
		return Uid;
	}
	public void setUid(int uid) {
		Uid = uid;
	}
	public int getFid() {
		return Fid;
	}
	public void setFid(int fid) {
		Fid = fid;
	}
	public String getC_content() {
		return C_content;
	}
	public void setC_content(String c_content) {
		C_content = c_content;
	}
	public int getStartlevel() {
		return startlevel;
	}
	public void setStartlevel(int startlevel) {
		this.startlevel = startlevel;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public int getOtherlike() {
		return otherlike;
	}
	public void setOtherlike(int otherlike) {
		this.otherlike = otherlike;
	}
	
	
}
