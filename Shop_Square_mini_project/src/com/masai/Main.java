package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.masai.Service.CustomerServImpls;
import com.masai.Service.CustomerService;
import com.masai.Service.ProductServImpls;
import com.masai.Service.ProductService;
import com.masai.Service.PurchaseServImpls;
import com.masai.Service.PurchaseService;
import com.masai.Service.VendorServImpls;
import com.masai.Service.VendorService;
import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.entity.Purchase;
import com.masai.entity.Transaction;
import com.masai.entity.User;
import com.masai.entity.Vendor;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;
import com.masai.utility.Admin;
import com.masai.utility.FileExists;
import com.masai.utility.IdGeneration;

public class Main {

	// admin functionality
		private static void adminFunctionality(Scanner sc, Map<Integer, Product> products, Map<String, Customer> customers,
				List<Purchase> purchases,Map<String,Vendor> vendors) throws InvalidDetailsException, ProductException, TransactionException {
			// admin login

			adminLogin(sc);

			
			CustomerService cusService = new CustomerServImpls();
			ProductService prodService = new ProductServImpls();
			PurchaseService purchaseService = new PurchaseServImpls();
			VendorService vendorService = new VendorServImpls();
			int choice = 0;
			try {
				do {
					System.out.println("Press 1 add the product");
					System.out.println("Press 2 view all the product");
					System.out.println("Press 3 delete the product");
					System.out.println("Press 4 update the product");
					System.out.println("Press 5 view all customers");
					System.out.println("Press 6 to view all purchases");
//					System.out.println("Press 7 to approve the vendor");
//					System.out.println("Press 8 to reject the vendor");
					System.out.println("Press 7 to log out");
					choice = sc.nextInt();

					switch (choice) {
					case 1:
						String added = adminAddProduct(sc, products, prodService);
						System.out.println(added);
						break;
					case 2:

						adminViewAllProducts(products, prodService);
						break;
					case 3:

						adminDeleteProduct(sc, products, prodService);
						break;
					case 4:

						String upt = adminUpdateProduct(sc, products, prodService);
						System.out.println(upt);
						break;
					case 5:
						adminViewAllCustomers(customers, cusService);

						break;
					case 6:
						adminViewAllPurchases(purchases, purchaseService);
						break;
						
//					case 7:
//						adminApproveVendor(vendors);
//						break;
//						
//					case 8:
//						adminRejectVendor(vendors);
//						break;
					case 7:
						System.out.println("admin has successfully logout");
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + choice);
					}

				} while (choice <= 6);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
		public static void adminLogin(Scanner sc) throws InvalidDetailsException {

			System.out.println("Enter the user name");
			String userName = sc.next();
			System.out.println("Enter the password");
			String password = sc.next();
			if (userName.equals(Admin.username) && password.equals(Admin.password)) {

				System.out.println("successfully login");
			} else {
				throw new InvalidDetailsException("Invalid Admin Credentials");
			}
		}
	
		public static String adminAddProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) {

			String str = null;
			System.out.println("please enter the product details");
			System.out.println("Enter the product name");
			String name = sc.next();
			System.out.println("Enter the product qty");
			int qty = sc.nextInt();
			System.out.println("Enter the product price/piece");
			double price = sc.nextDouble();
			System.out.println("Enter the product category");
			String cate = sc.next();

			Product prod = new Product(IdGeneration.generateId(), name, qty, price, cate);

			str = prodService.addProduct(prod, products);// considering all details are valid

			return str;

		}
	
		public static void adminViewAllProducts(Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			prodService.viewAllProducts(products);
		}
		
		public static void adminDeleteProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService)
				throws ProductException {

			System.out.println("please enter the id of product to be deleted");
			int id = sc.nextInt();
			prodService.deleteProduct(id, products);
		}
		
		public static String adminUpdateProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			String result = null;
			System.out.println("please enter the id of the product which is to be updated");
			int id = sc.nextInt();
			System.out.println("Enter the updated details ");

			System.out.println("Enter the product name");
			String name = sc.next();

			System.out.println("Enter the  product qty");
			int qty = sc.nextInt();

			System.out.println("Enter the product price/piece");
			double price = sc.nextDouble();

			System.out.println("Enter the product category");
			String cate = sc.next();

			Product update = new Product(id, name, qty, price, cate);

			result = prodService.updateProduct(id, update, products);
			return result;
		}

		public static void adminViewAllCustomers(Map<String, Customer> customers, CustomerService cusService)
				throws ProductException {
			List<Customer> list = cusService.viewAllCustomers(customers);

			for (Customer c : list) {
				System.out.println(c);
			}
		}



		public static void adminViewAllPurchases(List<Purchase> purchases, PurchaseService purchaseService) 
				throws TransactionException {
			List<Purchase> allPurchases = purchaseService.viewAllPurchase(purchases);
			
			for(Purchase pr : allPurchases) {
				System.out.println(pr);
				
			}
		}
		
//		public static void adminApproveVendor(Map<String,Vendor> vendors) {
//			
//			System.out.println("Vendor"+ ((User) vendors).getUsername() +"approved");
//		
//		}
//		
//		public static void adminRejectVendor(Map<String,Vendor> vendors) {
//			
//			System.out.println("Vendor"+ ((User) vendors).getUsername() +"rejected");
//		
//		}
//		
		
		
		// customer functionality
		public static void customerFunctionality(Scanner sc, Map<String, Customer> customers,
				Map<Integer, Product> products, List<Purchase> purchases,Map<String,Vendor> vendors)
				throws InvalidDetailsException, TransactionException {

			CustomerService cusService = new CustomerServImpls();
			ProductService prodService = new ProductServImpls();
			PurchaseService purchaseService = new PurchaseServImpls();
			VendorService venService = new VendorServImpls();

			// Customer login
			System.out.println("please enter the following details to login");
			System.out.println("please enter the email");
			String email = sc.next();
			System.out.println("Enter the password");
			String pass = sc.next();
			customerLogin(email,pass, customers, cusService);

			try {
				int choice = 0;
				do {
					System.out.println("Select the option of your choice");
					System.out.println("Press 1 to view all products");
					System.out.println("Press 2 to buy a product");
					System.out.println("Press 3 to add money to a wallet");
					System.out.println("Press 4 view wallet balance");
					System.out.println("Press 5 view my details");
					System.out.println("Press 6 view my purchases");
					System.out.println("Press 7 to update details");
					System.out.println("Press 8 to delete account");
					System.out.println("Press 9 to logout");
					choice = sc.nextInt();

					switch (choice) {
					case 1:
						customerViewAllProducts(products, prodService);
						break;
					case 2:
						String result = customerBuyProduct(sc, email, products, customers, purchases, cusService,vendors);
						System.out.println(result);
						break;
					case 3:
						String moneyAdded = customerAddMoneyToWallet(sc, email, customers, cusService);
						System.out.println(moneyAdded);
						break;
					case 4:
						double walletBalance = customerViewWalletBalance(email, customers, cusService);
						System.out.println("Wallet balance is: " + walletBalance);
						break;
					case 5:
						customerViewMyDetails(email, customers, cusService);
						break;
					case 6:
						customerViewCustomerPurchases(email, purchases, purchaseService);
						break;
					case 7:
						customerUpdateDetails(sc,customers,cusService);
						break;
					case 8:
						customerDeleteAccount(sc,customers,cusService);
						break;
					case 9:
						System.out.println("you have successsfully logout");
						break;
					default:
						System.out.println("invalid choice");
						break;
					}

				} while (choice <= 6);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		private static void customerDeleteAccount(Scanner sc, Map<String, Customer> customers,
				CustomerService cusService) throws InvalidDetailsException {
			// TODO Auto-generated method stub
			System.out.println("please enter your email delete account");
			String email = sc.next();
			cusService.deleteAccount(email, customers);
		}

		private static String customerUpdateDetails(Scanner sc, Map<String, Customer> customers,
				CustomerService cusService) throws InvalidDetailsException {
			// TODO Auto-generated method stub
			String result = null;
			System.out.println("please enter the email of the product which is to be updated");
			String email = sc.next();
			System.out.println("Enter the updated details ");
			

			System.out.println("Enter your name");
			String name = sc.next();

			System.out.println("Enter the  mobile number");
			String address = sc.next();

			System.out.println("Enter new password");
			String pass = sc.next();


			Customer update = new Customer(email, name, address,pass);

			result = cusService.updateDetails(email, update, customers);
			return result;
		}

		public static void customerSignup(Scanner sc, Map<String, Customer> customers) throws DuplicateDataException {
			System.out.println("please enter the following details to Signup");
			System.out.println("please enter the user name");
			String name = sc.next();
			System.out.println("Enter the password");
			String pass = sc.next();
			System.out.println("enter the address");
			String address = sc.next();
			System.out.println("Enter the email id");
			String email = sc.next();
			System.out.println("Enter the balance to be added into the wallet");
			double balance = sc.nextDouble();
			Customer cus = new Customer(balance, name, pass, address, email);

			CustomerService cusService = new CustomerServImpls();
			cusService.signup(cus, customers);
			System.out.println("customer has Succefully sign up");

		}

		public static void customerLogin(String email,String pass, Map<String, Customer> customers, CustomerService cusService)
				throws InvalidDetailsException {
			cusService.login(email, pass,customers);
			System.out.println("Customer has successfully login");

		}

		public static void customerViewAllProducts(Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			prodService.viewAllProducts(products);
		}

		public static String customerBuyProduct(Scanner sc, String email, Map<Integer, Product> products,
				Map<String, Customer> customers, List<Purchase> purchases, CustomerService cusService,
				Map<String,Vendor> vendor)
				throws InvalidDetailsException, ProductException {
			System.out.println("Enter the product id");
			int id = sc.nextInt();
			System.out.println("enter the quantity you want to buy");
			int qty = sc.nextInt();
			cusService.buyProduct(id, qty, email, products, customers, purchases, vendor);

			return "You have successfully bought the product";

		}

		public static String customerAddMoneyToWallet(Scanner sc, String email, Map<String, Customer> customers,
				CustomerService cusService) {
			System.out.println("please enter the amount");
			double money = sc.nextDouble();
			boolean added = cusService.addMoneyToWallet(money, email, customers);

			return "Amount: " + money + " successfully added to your wallet";
		}

		public static double customerViewWalletBalance(String email, Map<String, Customer> customers,
				CustomerService cusService) {
			double walletBalance = cusService.viewWalletBalance(email, customers);
			return walletBalance;
		}

		public static void customerViewMyDetails(String email, Map<String, Customer> customers,
				CustomerService cusService) {
			Customer cus = cusService.viewCustomerDetails(email, customers);
			System.out.println("name : " + cus.getUsername());
			System.out.println("address : " + cus.getAddress());
			System.out.println("email : " + cus.getEmail());
			System.out.println("wallet balance : " + cus.getWalletBalance());
		}

		public static void customerViewCustomerPurchases(String email, List<Purchase> purchases,
				PurchaseService purchaseService) throws TransactionException {
			
			List<Purchase> myPurchases = purchaseService.viewCustomerPurchase(email, purchases);

			for (Purchase tr : myPurchases) {
				System.out.println(tr);
			}
		}
		
// Vendor role
		public static void vendorFunctionality(Scanner sc, Map<String, Customer> customers,
				Map<Integer, Product> products, List<Purchase> purchases,Map<String,Vendor> vendors)
				throws InvalidDetailsException, TransactionException {

			CustomerService cusService = new CustomerServImpls();
			ProductService prodService = new ProductServImpls();
			PurchaseService purchaseService = new PurchaseServImpls();
			VendorService venService = new VendorServImpls();

			// Vendor login
			System.out.println("please enter the following details to login");
			System.out.println("please enter the email");
			String email = sc.next();
			System.out.println("Enter the password");
			String pass = sc.next();
			vendorLogin(email,pass, vendors, venService);

			try {
				int choice = 0;
				do {
					System.out.println("Select the option of your choice");
					System.out.println("Press 1 view my products");
					System.out.println("Press 2 to add product");
					System.out.println("Press 3 purchase history");
					System.out.println("Press 4 to add product");
					System.out.println("Press 5 to update product");
					System.out.println("Press 6 to delete product");
					System.out.println("Press 7 to logout");
					choice = sc.nextInt();

					switch (choice) {
					case 1:
						vendorViewAllProducts(sc,products, prodService);
						break;

					case 2:
						vendorViewMyDetails(email, vendors, venService);
						break;
					case 3:
						vendorViewPurchasesHistory(email, purchases, purchaseService);
						break;
					case 4:
						vendorAddProduct(sc,products,prodService);
						break;
					case 5:
						vendorUpdateProduct(sc,products,prodService);
						break;
					case 6: 
						deleteProduct(sc,products,prodService);
						break;
					case 7:
						System.out.println("You have successsfully logout");
						break;
					default:
						System.out.println("Invalid choice");
						break;
					}

				} while (choice <= 4);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
	private static void deleteProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) throws ProductException {
	// TODO Auto-generated method stub
		System.out.println("Please enter the product-id deleted product");
		int id = sc.nextInt();
		prodService.deleteProduct(id, products);
}

	private static String vendorUpdateProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) throws ProductException {
	// TODO Auto-generated method stub
		String result = null;
		System.out.println("Please enter product-ID to update product details");
		int id = sc.nextInt();
		System.out.println("Enter the updated details ");

		System.out.println("Enter the product name");
		String name = sc.next();

		System.out.println("Enter the  product qty");
		int qty = sc.nextInt();

		System.out.println("Enter the product price/piece");
		double price = sc.nextDouble();

		System.out.println("Enter the product category");
		String cate = sc.next();

		Product update = new Product(id, name, qty, price, cate);

		result = prodService.updateProduct(id, update, products);
		return result;
}

	private static String vendorAddProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) {
	// TODO Auto-generated method stub
		String str = null;
		System.out.println("please enter the product details");
		System.out.println("Enter the product name");
		String name = sc.next();
		System.out.println("Enter the product qty");
		int qty = sc.nextInt();
		System.out.println("Enter the product price/piece");
		double price = sc.nextDouble();
		System.out.println("Enter the product category");
		String cate = sc.next();

		Product prod = new Product(IdGeneration.generateId(), name, qty, price, cate);

		str = prodService.addProduct(prod, products);// considering all details are valid

		return str;
}

	private static void vendorViewPurchasesHistory(String email, List<Purchase> purchases,
		PurchaseService purchaseService) throws TransactionException {
	// TODO Auto-generated method stub
	
		List<Purchase> myPurchases = purchaseService.viewCustomerPurchase(email, purchases);

		for (Purchase tr : myPurchases) {
			System.out.println(tr);
		}
}

	private static void vendorViewMyDetails(String email, Map<String, Vendor> vendors, VendorService venService) {
			// TODO Auto-generated method stub
		Vendor ven = venService.viewVendorDetails(email, vendors);
		System.out.println("name : " + ven.getUsername());
		System.out.println("address : " + ven.getAddress());
		System.out.println("email : " + ven.getEmail());
		System.out.println("mobile No. : " + ven.getMoblieNum());
		}

	private static void vendorViewAllProducts(Scanner sc, Map<Integer, Product> products,
				ProductService prodService) throws ProductException {
			// TODO Auto-generated method stub
		prodService.viewAllProducts(products);
		}

	private static void vendorLogin(String email, String pass, Map<String, Vendor> vendors,
				VendorService venService) throws InvalidDetailsException {
			// TODO Auto-generated method stub
		venService.login(email, pass,vendors);
		System.out.println("Vendor has successfully login");
		}
	
	private static void vendorSignup(Scanner sc, Map<String, Vendor> vendors) throws DuplicateDataException {
		// TODO Auto-generated method stub
		System.out.println("please enter the following details to Signup");
		System.out.println("please enter the user name");
		String name = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		System.out.println("enter the address");
		String address = sc.next();
		System.out.println("Enter the email id");
		String email = sc.next();
		System.out.println("Enter your mobile number");
		String mobNum = sc.next();
		
		Vendor ven = new Vendor(name, pass, address, email,mobNum);

		VendorService venService = new VendorServImpls();
		venService.signup(ven, vendors);
		System.out.println("Vendor has succefully sign-up");

	}

	public static void main(String[] args) {
		
		Map<Integer, Product> products = FileExists.productFile();
		Map<String, Customer> customers = FileExists.customerFile();
		Map<String, Vendor> vendors =FileExists.vendorFile();
		List<Purchase> purchases = FileExists.purchaseFile();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to ShopSquare...");
		
		try {
			
			int preference = 0;
			
			do {
				System.out.println("Please enter your preference...");
				System.out.println("Press 1 for Admin Login...");
				System.out.println("Press 2 for Customer Login...");
				System.out.println("Press 3 for Customer Sign-Up...");
				System.out.println("Press 4 for Vendor Login...");
				System.out.println("Press 5 for Vendor Sign-Up...");
				System.out.println("Press 0 for exit");
				
				preference = sc.nextInt();
				switch (preference) {
				case 1:
					adminFunctionality(sc, products, customers, purchases,vendors);
					break;
				case 2:
					customerFunctionality(sc, customers, products, purchases,vendors);
					break;

				case 3:
					customerSignup(sc, customers);
					break;
				
				case 4:
					vendorFunctionality(sc,customers,products,purchases,vendors);
					break;
				
				case 5:
					vendorSignup(sc,vendors);
					break;

				case 0:
					System.out.println("successfully existed from the system");

					break;

				default:
					throw new IllegalArgumentException("Invalid Selection");
				}

			}
			while(preference != 0);	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			// serialization (finally always executed)
			try {
				ObjectOutputStream poos = new ObjectOutputStream(new FileOutputStream("Product.ser"));
				poos.writeObject(products);
				
				ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
				coos.writeObject(customers);

				ObjectOutputStream toos = new ObjectOutputStream(new FileOutputStream("purchases.ser"));
				toos.writeObject(purchases);
				
				ObjectOutputStream voos = new ObjectOutputStream(new FileOutputStream("Vendor.ser"));
				voos.writeObject(vendors);
				
			//	System.out.println("serialized..........");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
		
	}

	

	
}
