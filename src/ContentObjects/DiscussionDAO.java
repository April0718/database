package ContentObjects;
import java.util.*;

public interface DiscussionDAO {
	
	//���������Ӿ�������id��Ψһ�ԣ���Ҫ
	public void insertDiscussion(Discussion disc);
	public void updateDiscussion();
	//�����ò��ϸ������ݣ�һ�����ӷ���ȥ�ǲ������޸ĵ�
	//������ֻ����༭���ݣ����಻�����޸ģ�������ʱ��
	
	public void deleteDiscussion(int discId,int floor);	
	public Discussion getDiscussionForOne(int discId,int floor);
    //���ﷵ��ֻ���Ƿ���һ���ظ���¼����
	public ArrayList<Discussion> getDiscussion(int filmId,int floor);
	//��ʱ�᷵��һ��ѽ��������Ҫ��װ��list���棻
	public ArrayList<Discussion> getDiscussionAllFloor(int discId);
	//����һ�����ӵ�����¥�㣻
}
