package ContentObjects;

import java.util.List;

public interface ActorAwardDAO {
	public void insertActorAward();
	public void updateActorAward(int Aid);
	public void deleteActorAward(int Aid);
	public List<ActorAward> getActorAward(int Aid);//���һ����Ա��õ����н���
	public void print(List<ActorAward> actorawards);//����������Ͱ佱ʱ��
}
