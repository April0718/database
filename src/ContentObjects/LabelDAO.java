package ContentObjects;

import java.util.List;

public interface LabelDAO {
	public void insertLabel();
	public void updateLabel(int Fid,String labelName);
	public Boolean updateLabelCount(int Fid,int count);//���ı�ǩ��Ǵ���
	public void deleteLabel(int Fid,String labelName);
	public List<Label> getFilmLabels(int Fid);//����ĳ����Ӱ�õ�һ����Ӱ���û���ı�ǩ
	public List<Label> getLabels(int Fid);//�ҵ�ĳ����Ӱ��Ǵ���ǰ���ı�ǩ����ʾ���û����Թ��û����
	public void printLabelsName(List<Label> labels);//��ӡǰ10����ǩ��
}
