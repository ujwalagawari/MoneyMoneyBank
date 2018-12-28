package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) throws ClassNotFoundException, SQLException;

	SavingsAccount updateAccount(SavingsAccount account);

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) throws ClassNotFoundException, SQLException;
	
	void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;
	
	void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;

	double getCurrentBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<SavingsAccount> sortAccountsByAccountBalance(String sortWay) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> sortAccountsByAccountNumber(String sortWay) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> sortAccountsByAccountHolderName(String sortWay) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> getAccountsByHolderName(String holderName) throws SQLException, ClassNotFoundException;

	List<SavingsAccount> getAccountsBetweenMinMaxAccountBal(Double minBalance,Double maxBalance) throws SQLException, ClassNotFoundException;

	
	
}











