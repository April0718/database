package ContentObjects;

import java.util.List;

public interface TypeDAO {
	public void insertType();
	public void updateType(int fid,String typeName);
	public void deleteType(int fid,String typeName);
	public List<Type> getType(int Fid);//传电影号参数来查找一个电影的类型，根据优先级降序存储在一个list里
	public void print(List<Type> types);//打印电影类型
}
