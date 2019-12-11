package ContentObjects;
import java.util.*;

public interface DiscussionDAO {
	
	//讨论区帖子具有帖子id不唯一性，需要
	public void insertDiscussion(Discussion disc);
	public void updateDiscussion();
	//好像用不上更新内容，一般帖子发出去是不允许修改的
	//或者是只允许编辑内容，其余不可以修改，甚至是时间
	
	public void deleteDiscussion(int discId,int floor);	
	public Discussion getDiscussionForOne(int discId,int floor);
    //这里返回只能是返回一条回复记录而已
	public ArrayList<Discussion> getDiscussion(int filmId,int floor);
	//此时会返回一大堆结果，故需要封装在list里面；
	public ArrayList<Discussion> getDiscussionAllFloor(int discId);
	//返回一个帖子的所有楼层；
}
