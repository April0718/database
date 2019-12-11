package ContentObjects;

public class Role {
	private int Aid;//演员编号
	private int Fid;//参演的电影编号
	private String roleName;//角色名
	private int status;//主角 or 配角
	public int getAid() {
		return Aid;
	}
	public void setAid(int aid) {
		Aid = aid;
	}
	public int getFid() {
		return Fid;
	}
	public void setFid(int fid) {
		Fid = fid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
