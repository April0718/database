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
		System.out.println("***-------------------讨论区指定层内容-----------------***");
	    System.out.println("帖子ID："+discussionId);
	    System.out.println("电影ID："+filmId);
	    System.out.println("用户ID："+userId);
	    System.out.println("帖子标题："+title);
	    System.out.println("帖子内容："+content);
	    System.out.println("楼层数："+floor);
	    System.out.println("回复楼层数："+replyFloor);
	    System.out.println("提交时间："+subTime);
	    System.out.println("获赞数："+beLiked);
	   
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
