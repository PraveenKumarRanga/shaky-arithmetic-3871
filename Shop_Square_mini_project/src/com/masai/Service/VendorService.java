package com.masai.Service;

import java.util.List;
import java.util.Map;

import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.entity.Purchase;
import com.masai.entity.Vendor;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;

public interface VendorService {

	
	
	public void signup(Vendor ven, Map<String,Vendor> vendors) throws DuplicateDataException;
	
	public boolean login(String email, String password,Map<String,Vendor> vendors) throws InvalidDetailsException;
	
//	public boolean listingRequest(Product product) throws DuplicateDataException;
	
	public String addProduct(Map<Integer,Product> products, Product prod) throws InvalidDetailsException;
	
	public Vendor viewVendorDetails(String email, Map<String, Vendor> vendors); 
	
	
}
