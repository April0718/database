package ContentObjects;
import java.util.*;
import java.util.ArrayList;

public interface CommentDAO {
	
	public void insertComment(Comment comt);
	public void updateComment();
	public void deleteComment(int commentId);
	public Comment getCommentByComId(int commentId);
	public ArrayList<Comment> getComment(int filmId);

}
