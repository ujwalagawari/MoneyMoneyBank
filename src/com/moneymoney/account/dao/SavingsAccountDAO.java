package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	
	SavingsAccount updateAccount(SavingsAccount account);
	
	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	
	void commit() throws SQLException;
	
	double getCurrentBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<SavingsAccount> sortAccountsByAccountBalance(String sortWay) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> sortAccountsByAccountNumber(String sortWay) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> sortAccountsByAccountHolderName(String sortWay) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> getAccountsByHolderName(String holderName) throws SQLException, ClassNotFoundException;

	List<SavingsAccount> getAccountsBetweenMinMaxAccountBal(Double minBalance, Double maxBalance) throws SQLException, ClassNotFoundException;
	
	
}
