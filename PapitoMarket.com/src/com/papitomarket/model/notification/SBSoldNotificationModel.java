package com.papitomarket.model.notification;

import java.util.List;

public class SBSoldNotificationModel {
	private String name;
	private String user;
	private String fbid;
	private String fbpic;
	private String store;
	private String phone;
	private String orderfrom;
	private String store_jabber_addr;
	private String status;
	private String time;
	private String addr;
	private String appartment;
    private List<Item> items;
    private String total;

    public SBSoldNotificationModel(){
    	
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderfrom() {
		return orderfrom;
	}
	public void setOrderfrom(String orderfrom) {
		this.orderfrom = orderfrom;
	}
	public String getStore_jabber_addr() {
		return store_jabber_addr;
	}
	public void setStore_jabber_addr(String store_jabber_addr) {
		this.store_jabber_addr = store_jabber_addr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAppartment() {
		return appartment;
	}
	public void setAppartment(String appartment) {
		this.appartment = appartment;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getFbid() {
		return fbid;
	}

	public void setFbid(String fbid) {
		this.fbid = fbid;
	}

	public String getFbpic() {
		return fbpic;
	}

	public void setFbpic(String fbpic) {
		this.fbpic = fbpic;
	}
}