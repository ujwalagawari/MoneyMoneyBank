package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.account.util.SortAccounts;
import com.moneymoney.exception.AccountNotFoundException;

public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	private static SavingsAccountService savingsAccountService=new SavingsAccountServiceImpl();
	
	public static void start() {
		
		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");
			
			int choice = scanner.nextInt();
			
			performOperation(choice);
			
		} while(true);
	}

	private static void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			updateAccount();
			break;
		case 3:
			closeAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			checkCurrentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			selectOrderOfSort();
			break;
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
		
	}

	private static void updateAccount() {
		do {
			System.out.println("Update Account : ");
			System.out.println("1. Update Account Holder Name");
			System.out.println("2. Update Salary Type");
			System.out.println("3. Update Both Account Holder Name and Salary Type");
			System.out.println("4. Exit Update Account");
			System.out.println();
			System.out.println("Make your choice: ");
			
			int choice = scanner.nextInt();
			performUpdateOperation(choice);
		} while(true);
	}

	private static void performUpdateOperation(int choice) {
		int accountNumber = 0;
		if(choice > 0 && choice < 4){
			System.out.println("Enter account number : ");
			accountNumber = scanner.nextInt();
		}
		switch (choice) {
		case 1:
			System.out.println("Change Account Holder Name : ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
				savingsAccount.getBankAccount().setAccountHolderName(accountHolderName);
				savingsAccount = savingsAccountService.updateAccount(savingsAccount, accountNumber);
				System.out.println(savingsAccount);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Change Salary Type: ");
			boolean salary = scanner.nextBoolean();
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
				savingsAccount.setSalary(salary);
				savingsAccount = savingsAccountService.updateAccount(savingsAccount, accountNumber);
				System.out.println(savingsAccount);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Change Account Holder Name : ");
			accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Change Salary Type: ");
			salary = scanner.nextBoolean();
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
				savingsAccount.getBankAccount().setAccountHolderName(accountHolderName);
				savingsAccount.setSalary(salary);
				savingsAccount = savingsAccountService.updateAccount(savingsAccount, accountNumber);
				System.out.println(savingsAccount);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			start();
			break;
		default:
			System.out.println("Invalid Choice!");
			break;
		}
		
	}

	private static void searchAccount() {
		do {
			System.out.println("Search Account by : ");
			System.out.println("1. Search By Account Number");
			System.out.println("2. Search By Account Holder Name");
			System.out.println("3. Search By Account Balance");
			System.out.println("4. Exit Search");
			System.out.println();
			System.out.println("Make your choice: ");
			
			int choice = scanner.nextInt();
			performSearchOperation(choice);
		} while(true);
	}

	private static void performSearchOperation(int choice) {
		switch (choice) {
		case 1:
			searchByAccountNumber();
			break;
		case 2:
			searchByAccountHolderName();
			break;
		case 3:
			searchByAccountBalance();
			break;
		case 4:
			start();
			break;
		default:
			System.out.println("Incorrect Choice!");
			break;
		}
		
	}

	private static void searchByAccountBalance() {
		List<SavingsAccount> savingsAccounts;
		System.out.println("Enter Minimum salary : ");
		Double minBalance = scanner.nextDouble();
		System.out.println("Enter Maximum salary : ");
		Double maxBalance = scanner.nextDouble();
		try {
			savingsAccounts = savingsAccountService.getAccountsBetweenMinMaxAccountBal(minBalance, maxBalance);
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void searchByAccountHolderName() {
		List<SavingsAccount> savingsAccounts=null;
		System.out.println("Enter Account Holder Name : ");
		String holderName = scanner.nextLine();
		holderName = scanner.nextLine();
		try {
			savingsAccounts = savingsAccountService.getAccountsByHolderName(holderName);
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			//Or by using contains
			/*List<SavingsAccount> accounts = new ArrayList<SavingsAccount>();
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				if((savingsAccount.getBankAccount().getAccountHolderName()).contains(holderName)){
					accounts.add(savingsAccount);
				}
			}
			for (SavingsAccount savingsAccount : accounts) {
				System.out.println(savingsAccount);
			}*/
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void searchByAccountNumber() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		try {
			SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
			System.out.println(savingsAccount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void checkCurrentBalance() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		try {
			double currentBalance = savingsAccountService.getCurrentBalance(accountNumber);
			System.out.println("Your current account balance is : "+currentBalance);
		} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void closeAccount() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		try {
			savingsAccountService.deleteAccount(accountNumber);
		} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void selectOrderOfSort() {
		do {
			System.out.println("****** Select Sort Order ********");
			System.out.println("Sort in Ascending Order Write : Ascending");
			System.out.println("Sort in Descending Order Write : Descending");
			System.out.println();
			System.out.println("Make your choice: ");
			String choice = scanner.nextLine();
			choice = scanner.nextLine();
			sortMenu(choice);
		} while(true);
	}
	
	private static void sortMenu(String sortWay) {
		if(sortWay.equalsIgnoreCase("Ascending") || sortWay.equalsIgnoreCase("Descending")){
			do {
				System.out.println("+++++Ways of Sorting+++++++");
				System.out.println("1. Account Number");
				System.out.println("2. Account Holder Name");
				System.out.println("3. Account Balance");
				System.out.println("4. Exit from Sorting");
				
				int choice = scanner.nextInt();
				System.out.println();
				performSort(choice, sortWay);
				
			}while(true);
		}
		System.out.println("You have entered wrong order.");
	}

	private static void performSort(int choice, String sortWay) {
		List<SavingsAccount> savingsAccounts =null;
		switch (choice) {
		case 1:
			try {
				savingsAccounts = savingsAccountService.getAllSavingsAccount();
				SortAccounts.sortByAccountNumber(sortWay, savingsAccounts);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				savingsAccounts = savingsAccountService.getAllSavingsAccount();
				SortAccounts.sortByAccountHolderName(sortWay, savingsAccounts);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				savingsAccounts = savingsAccountService.getAllSavingsAccount();
				SortAccounts.sortByAccountBalance(sortWay, savingsAccounts);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			start();
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
	}

	
	private static void showAllAccounts() {
		List<SavingsAccount> savingsAccounts = null;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void acceptInput(String type) {
		if(type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance=0.0;
			if(!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n")?false:true;
			SavingsAccount savingsAccount=createSavingsAccount(accountHolderName,accountBalance, salary);
			System.out.println(savingsAccount);
		}
	}

	private static SavingsAccount createSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount =savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return savingsAccount;
	}
	
	
	
}



