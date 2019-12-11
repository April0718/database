package ContentObjects;

import java.util.List;

public interface ActorDAO {
	public void insertActor();
	public void updateActor(int Actor_ID);
	public void deleteActor(int Actor_ID);
	public Actor getActor(int Actor_ID);//根据Actor_ID查询得到一个演员的完整信息返回一个Actor
}
