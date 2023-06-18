package com.masai.entity;

import java.io.Serializable;

public class Customer extends User implements Serializable{
	
	private double walletBalance;

	
	
	public Customer(double walletBalance, String password, String address, String email, String username) {
		super(username, password, address, email);
		this.walletBalance = walletBalance;
	}
    public Customer(String password, String address, String email, String username) {
		
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	@Override
	public String toString() {
		return "Customer [walletBalance=" + walletBalance + ", getWalletBalance()=" + getWalletBalance()
				+ ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getAddress()="
				+ getAddress() + ", getEmail()=" + getEmail() + "]";
	}
	
	
	
}
