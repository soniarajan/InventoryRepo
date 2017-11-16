package testpack;

import java.sql.*;
import java.util.ArrayList;

public class DB_Access implements DB_Params {
	private Connection con;
	private Statement st;
	public DB_Access() {
		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url, userName, userPass);
			st = con.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public int validateLogin(String uname, String upass) {
		int uid = -1; // assumption, -1 is returned when credentials are wrong
		
		String sql = "select uid from tuser02 where LoginName=? and LoginPass=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, uname);
			pst.setString(2, upass);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				// we have the row, credentials are correct
				uid = rs.getInt(1);
			}
			System.out.println("test");
		}catch(Exception e) {e.printStackTrace();}
		
		return uid;
	}
	public User getUserInfo(int uid) {
		User u = new User();
		String sql = "select LoginName, Name,LoginPass from tuser02 where uid = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				u.setLoginName(rs.getString(1));
				u.setName(rs.getString("Name"));
				u.setLoginPass(rs.getString("LoginPass"));
			}
		}catch(Exception e) {e.printStackTrace();}
		return u;
	}
	
	public boolean validateLogin(String lName) {
		User u = new User();
		String sql = "select LoginName from tuser02 where LoginName = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, lName);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {e.printStackTrace();}
		return false;
	}
	
	public ArrayList<Item> getUserItems(int uid) {
		ArrayList<Item> items = new ArrayList<Item>();
		String sql = "select iid, ItemName, Qty from titems02 where uid = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Item i = new Item(rs.getInt(1), 
						rs.getString(2),
						rs.getInt(3),
						uid);
				items.add(i);
			}
		}catch(Exception e) { e.printStackTrace();}
		return items;
	}
	public String getUserName(int uid) {
		String name = "";
		
		String sql = "select name from tuser02 where uid = "+uid;
		
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) name = rs.getString(1);
		}catch(Exception e) { e.printStackTrace(); }
		
		return name;
	}
	public boolean insertItem(int uid, String name, int qty) {
		boolean success = true;
		
		String sql = "insert into titems02(ItemName,Qty,uid) values(?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			pst.setInt(2, qty);
			pst.setInt(3, uid);
			pst.executeUpdate();
		}catch(Exception e) {success = false; e.printStackTrace();}
		
		return success;
	}
	public boolean deleteItem(int iid, int uid) {
		boolean success = true;
		
		String sql = "delete from titems02 where iid = ? and uid = ?";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, iid);
			pst.setInt(2, uid);
			int res = pst.executeUpdate();
			System.out.println(res);
			if(res == 0) success = false;
		}catch(Exception e) { success = false; e.printStackTrace(); }
		
		return success;
	}

	public boolean modifyAccount(Integer uid, String lName, String name,
			String lPass) {
		boolean success = true;
		String sql = "update tuser02 set LoginName =? , Name= ? , LoginPass=? where uid = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, lName);
			pst.setString(2, name);
			pst.setString(3, lPass);
			pst.setInt(4, uid);
			int res = pst.executeUpdate();
			System.out.println(res);
			if(res == 0) success = false;
		}catch(Exception e) { success = false; e.printStackTrace(); }
		return success;
	}

	public int insertAccount(String lName, String name,
			String lPass) {
		String sql = "insert into tuser02(LoginName,Name,LoginPass) values(?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, lName);
			pst.setString(2, name);
			pst.setString(3, lPass);
			pst.executeUpdate();
		}catch(Exception e) {e.printStackTrace(); return -1;}
		return(validateLogin(lName,lPass));
	}

	public Item getItemById(Integer id, Integer uid) {
		// TODO Auto-generated method stub
		ArrayList<Item> items = new ArrayList<Item>();
		String sql = "select iid, ItemName, Qty from titems02 where iid = ? and uid = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Item i = new Item(rs.getInt(1), 
						rs.getString(2),
						rs.getInt(3),
						uid);
				items.add(i);
			}
		}catch(Exception e) { e.printStackTrace();}
		return items.size()>0 ? items.get(0) : null;
	}

	public boolean modifyItem(Integer uid, String iName, String iQty,
			Integer id) {
		// TODO Auto-generated method stub
		boolean success = true;
		String sql = "update titems02 set ItemName=?, Qty=? where uid = ? and iid= ? ";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, iName);
			pst.setString(2, iQty);
			pst.setInt(3, uid);
			pst.setInt(4, id);
			int res = pst.executeUpdate();
			System.out.println(res);
			if(res == 0) success = false;
		}catch(Exception e) { success = false; e.printStackTrace(); }
		return success;
	}
}





















