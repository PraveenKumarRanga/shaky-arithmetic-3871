package com.masai.Service;

import java.util.ArrayList;
import java.util.List;

import com.masai.entity.Purchase;
import com.masai.exceptions.TransactionException;

public class PurchaseServImpls implements PurchaseService{

	@Override
	public List<Purchase> viewCustomerPurchase(String email, List<Purchase> purchases) throws TransactionException {
		// TODO Auto-generated method stub
		
		
		List<Purchase> myPurchases = new ArrayList<>();

		boolean flag = false;
		for (Purchase pr : purchases) {
			if (pr.getEmail().equals(email)) {

				myPurchases.add(pr);

				flag = true;
			}
		}
		if (!flag) {
			throw new TransactionException("You have not done any purchases yet");
		}

		return myPurchases;
	}

	@Override
	public List<Purchase> viewAllPurchase(List<Purchase> purchases) throws TransactionException {
		// TODO Auto-generated method stub
		if(purchases != null && purchases.size()>0) {
			return purchases;
		}
		else {
			throw new TransactionException("No purchases yet");
		}
	}

}
