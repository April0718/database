package ContentObjects;

import java.util.List;

public interface LabelDAO {
	public void insertLabel();
	public void updateLabel(int Fid,String labelName);
	public Boolean updateLabelCount(int Fid,int count);//更改标签标记次数
	public void deleteLabel(int Fid,String labelName);
	public List<Label> getFilmLabels(int Fid);//输入某个电影得到一个电影被用户打的标签
	public List<Label> getLabels(int Fid);//找到某部电影标记次数前几的标签，显示给用户，以供用户标记
	public void printLabelsName(List<Label> labels);//打印前10个标签名
}
