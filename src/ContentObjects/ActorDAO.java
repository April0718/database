package ContentObjects;

import java.util.List;

public interface ActorDAO {
	public void insertActor();
	public void updateActor(int Actor_ID);
	public void deleteActor(int Actor_ID);
	public Actor getActor(int Actor_ID);//����Actor_ID��ѯ�õ�һ����Ա��������Ϣ����һ��Actor
}
