package testpack;

public class User {
	private int uid;
	private String loginName;
	private String name;
	private String loginPass;
	public User() {
		super();
	}
	public User(int uid, String loginName, String name, String loginPass) {
		super();
		this.uid = uid;
		this.loginName = loginName;
		this.name = name;
		this.loginPass = loginPass;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginPass() {
		return loginPass;
	}
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	
}
