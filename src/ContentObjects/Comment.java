package ContentObjects;

public class Comment {
	
	private int commentId;
	private int filmId;
	private int userId;
	private int mark;
	private String content;
	private String subTime;
	private String terminal;
	private int beLiked;
	public void print() {
		System.out.println("***-------------------某用户影评内容-----------------***");
	    System.out.println("影评ID："+commentId);
	    System.out.println("电影ID："+filmId);
	    System.out.println("用户ID："+userId);
	    System.out.println("评定星级："+mark);
	    System.out.println("影评内容："+content);	    
	    System.out.println("提交时间："+subTime);
	    System.out.println("获赞数："+beLiked);
	   	System.out.println("终端设备："+terminal);
	    System.out.println("***--------------------------------------------***");
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubTime() {
		return subTime;
	}
	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public int getBeLiked() {
		return beLiked;
	}
	public void setBeLiked(int beLiked) {
		this.beLiked = beLiked;
	}

}
