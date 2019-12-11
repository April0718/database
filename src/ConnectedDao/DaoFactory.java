package ConnectedDao;
import ContentObjects.*;
public class DaoFactory {
	private static DaoFactory instance;
	static {
		instance=new DaoFactory();//new 一个Instance的工厂类
	}
	private DaoFactory() {//构造函数
	}
	public static DaoFactory getInstance() {
		return instance;
	}
	public ActorDAO getActorDAO() {
		ActorDAO actorDAO=new ActorDAOMS();
		return actorDAO;
	}
	public ActorAwardDAO getActorAwardDAO (){
		ActorAwardDAO actorawardDAO = new ActorAwardDAOMS();
		return actorawardDAO;	
	}
	public AwardDAO getAwardDAO() {
		AwardDAO awardDAO=new AwardDAOMS();
		return awardDAO;
	}
	public LabelDAO getLabelDAO() {
		LabelDAO labelDAO=new LabelDAOMS();
		return labelDAO;
	}
	public RecordDAO getRecordDAO() {
		RecordDAO recordDAO=new RecordDAOMS();
		return recordDAO;
	}
	public RoleDAO getRoleDAO() {
		RoleDAO roleDAO=new RoleDAOMS();
		return roleDAO;
	}
	public TypeDAO getTypeDAO() {
		TypeDAO typeDAO=new TypeDAOMS();
		return typeDAO;
	}
	public CommentDAO getCommentDAO() {
		CommentDAO commentDAO=new CommentDaoMS();
		return commentDAO;
	}
	public DiscussionDAO getDiscussionDAO() {
		DiscussionDAO discussionDAO=new DiscussionDaoMS();
		return discussionDAO;
	}
	public FilmDAO getFilmDAO() {
		FilmDAO filmDAO=new FilmDaoMS();
		return filmDAO;
	}
	public UserDAO getUserDAO() {
		UserDAO userDAO=new UserDaoMS();
		return userDAO;
	}
}
