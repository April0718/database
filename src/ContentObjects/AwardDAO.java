package ContentObjects;

import java.util.List;

public interface AwardDAO {
	public void insertAward();
	public void updateAward(String Awname);
	public void deleteAward(String Awname);
	public List<Award> getAward(int Fid);//得到一部电影获得的所有奖项对象
	public void print(List<Award> FAws);//输出一部电影的所有奖项信息
}
