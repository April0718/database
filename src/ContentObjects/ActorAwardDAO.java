package ContentObjects;

import java.util.List;

public interface ActorAwardDAO {
	public void insertActorAward();
	public void updateActorAward(int Aid);
	public void deleteActorAward(int Aid);
	public List<ActorAward> getActorAward(int Aid);//获得一个演员获得的所有奖项
	public void print(List<ActorAward> actorawards);//输出奖项名和颁奖时间
}
