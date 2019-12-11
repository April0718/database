package ContentObjects;

public class Discussion {
	private int discussionId;
	private int filmId;
	private int userId;
	private String title;
	private String content;
	private int floor;
	private int  replyFloor;
	private String subTime;
	private int beLiked;
	
	public void print() {
		System.out.println("***-------------------������ָ��������-----------------***");
	    System.out.println("����ID��"+discussionId);
	    System.out.println("��ӰID��"+filmId);
	    System.out.println("�û�ID��"+userId);
	    System.out.println("���ӱ��⣺"+title);
	    System.out.println("�������ݣ�"+content);
	    System.out.println("¥������"+floor);
	    System.out.println("�ظ�¥������"+replyFloor);
	    System.out.println("�ύʱ�䣺"+subTime);
	    System.out.println("��������"+beLiked);
	   
	    System.out.println("***--------------------------------------------***");
	}
	
	public int getReplyFloor() {
		return replyFloor;
	}
	public void setReplyFloor(int replyFloor) {
		this.replyFloor = replyFloor;
	}
	public int getBeLiked() {
		return beLiked;
	}
	public void setBeLiked(int beLiked) {
		this.beLiked = beLiked;
	}
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getDiscussionId() {
		return discussionId;
	}
	public void setDiscussionId(int discussionId) {
		this.discussionId = discussionId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	

}
