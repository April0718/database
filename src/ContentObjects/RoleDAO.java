package ContentObjects;

import java.util.List;

public interface RoleDAO {
	public void insertRole();
	public void updateRole(int Aid,int Fid);
	public void deleteRole(int Aid,int Fid);
	public Role getRole(int Aid,int Fid);//获得某个演员参演的某部电影的角色名
	public int[] getActorsID(int Fid);//获得一部电影参演的所有演员ID
	public void print(List<Role> roles);
}
