package com.masai.Service;

import java.util.List;

import com.masai.entity.Purchase;
import com.masai.exceptions.TransactionException;

public interface PurchaseService {

    public List<Purchase> viewCustomerPurchase(String email, List<Purchase> purchases)throws TransactionException;
	
	public List<Purchase> viewAllPurchase(List<Purchase> purchases) throws TransactionException;
}
