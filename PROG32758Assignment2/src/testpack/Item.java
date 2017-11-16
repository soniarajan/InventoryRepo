package testpack;

public class Item {
	private int iid;
	private String itemName;
	private int quantity;
	private int uid;
	public Item() {
		super();
	}
	public Item(int iid, String itemName, int quantity, int uid) {
		super();
		this.iid = iid;
		this.itemName = itemName;
		this.quantity = quantity;
		this.uid = uid;
	}
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
}
