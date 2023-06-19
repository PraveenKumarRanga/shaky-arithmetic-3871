package com.masai.Service;

import java.util.List;
import java.util.Map;

import com.masai.entity.Vendor;
import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.entity.Purchase;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;

public interface CustomerService {

	public boolean login(String email, String password, Map<String, Customer> customers) throws InvalidDetailsException;
	
	public void signup(Customer cus, Map<String, Customer> customers) throws DuplicateDataException;
	
	public boolean buyProduct(int id, int qty, String email, Map<Integer, Product> products, Map<String, Customer> customers,
			List<Purchase> purchases,Map<String,Vendor> vendors) throws InvalidDetailsException, ProductException;
	
	public boolean addMoneyToWallet(double amount, String email, Map<String,Customer> customers);
	
	public double viewWalletBalance(String email, Map<String, Customer> customers);
	
	public Customer viewCustomerDetails(String email, Map<String, Customer> customers);

	public List<Customer> viewAllCustomers(Map<String, Customer> customers) throws ProductException;
	
	public String updateDetails(String email, Customer cus, Map<String, Customer> customers) throws InvalidDetailsException;
	
	public void deleteAccount(String email, Map<String, Customer> customers) throws InvalidDetailsException;
}
