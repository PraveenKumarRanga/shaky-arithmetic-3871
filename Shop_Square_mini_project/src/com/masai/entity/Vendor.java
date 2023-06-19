package com.masai.entity;

import java.io.Serializable;

public class Vendor extends User implements Serializable{

	private String moblieNum;

	public Vendor(String username, String password, String address, String email, String moblieNum) {
		super(username, password, address, email);
		this.moblieNum = moblieNum;
	}

	public String getMoblieNum() {
		return moblieNum;
	}

	public void setMoblieNum(String moblieNum) {
		this.moblieNum = moblieNum;
	}

	@Override
	public String toString() {
		return "Vendor [moblieNum=" + moblieNum + ", getMoblieNum()=" + getMoblieNum() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword() + ", getAddress()=" + getAddress()
				+ ", getEmail()=" + getEmail() + "]";
	}
	
	
	
}
