package ContentObjects;

import java.util.List;

public interface AwardDAO {
	public void insertAward();
	public void updateAward(String Awname);
	public void deleteAward(String Awname);
	public List<Award> getAward(int Fid);//�õ�һ����Ӱ��õ����н������
	public void print(List<Award> FAws);//���һ����Ӱ�����н�����Ϣ
}
