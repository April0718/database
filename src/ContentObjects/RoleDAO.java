package ContentObjects;

import java.util.List;

public interface RoleDAO {
	public void insertRole();
	public void updateRole(int Aid,int Fid);
	public void deleteRole(int Aid,int Fid);
	public Role getRole(int Aid,int Fid);//���ĳ����Ա���ݵ�ĳ����Ӱ�Ľ�ɫ��
	public int[] getActorsID(int Fid);//���һ����Ӱ���ݵ�������ԱID
	public void print(List<Role> roles);
}
