package DouBan;

public interface CommentDAO {
	public void insertComment(Comment com);
	public void updateComment(Comment com);
	public void deleteComment(int Uid,int Fid);
	public Comment getComment(int Uid,int Fid);
}
