package ContentObjects;

import java.util.List;

public interface TypeDAO {
	public void insertType();
	public void updateType(int fid,String typeName);
	public void deleteType(int fid,String typeName);
	public List<Type> getType(int Fid);//����Ӱ�Ų���������һ����Ӱ�����ͣ��������ȼ�����洢��һ��list��
	public void print(List<Type> types);//��ӡ��Ӱ����
}
