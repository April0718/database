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
		System.out.println("***-------------------ĳ�û�Ӱ������-----------------***");
	    System.out.println("Ӱ��ID��"+commentId);
	    System.out.println("��ӰID��"+filmId);
	    System.out.println("�û�ID��"+userId);
	    System.out.println("�����Ǽ���"+mark);
	    System.out.println("Ӱ�����ݣ�"+content);	    
	    System.out.println("�ύʱ�䣺"+subTime);
	    System.out.println("��������"+beLiked);
	   	System.out.println("�ն��豸��"+terminal);
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
