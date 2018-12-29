package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount().getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		int rowsaffected = preparedStatement.executeUpdate();
		
		if (rowsaffected != 0){
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if(generatedKeys.next()){
					account = new SavingsAccount(generatedKeys.getInt(1), account.getBankAccount().getAccountHolderName(),
							account.getBankAccount().getAccountBalance(), account.isSalary());
				} else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
			}
        }
		preparedStatement.close();
		commit();
		return account;
	}

	@Override
	public List<SavingsAccount> getAccountsByHolderName(String holderName) throws SQLException, ClassNotFoundException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCOUNT WHERE account_hn=?");
		preparedStatement.setString(1, holderName);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		preparedStatement.close();
		commit();
		return savingsAccounts;
	}
	
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		statement.close();
		commit();
		return savingsAccounts;
	}
	
	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET account_balance=? where account_id=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
	}
	
	@Override
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if(resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "+accountNumber+" does not exist.");
	}
	
	public SavingsAccount updateAccount(SavingsAccount account, int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET salary=?, account_hn=? where account_id=?");
		preparedStatement.setBoolean(1, account.isSalary());
		preparedStatement.setString(2, account.getBankAccount().getAccountHolderName());
		preparedStatement.setInt(3, accountNumber);
		int rowsaffected = preparedStatement.executeUpdate();
		preparedStatement.close();
		commit();
		if(rowsaffected != 0){
			return account;
		}
		return null;
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ACCOUNT WHERE ACCOUNT_ID=?");
		preparedStatement.setInt(1, accountNumber);
		preparedStatement.execute();
		preparedStatement.close();
		commit();
		return getAccountById(accountNumber);
	}

	@Override
	public void commit() throws SQLException {
		DBUtil.commit();
	}

	@Override
	public double getCurrentBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT ACCOUNT_BALANCE FROM ACCOUNT WHERE ACCOUNT_ID=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		double currentBalnce = 0;
		if(resultSet.next()){
			currentBalnce = resultSet.getDouble("ACCOUNT_BALANCE");
			preparedStatement.close();
			commit();
			return currentBalnce;
		}
		throw new AccountNotFoundException("Account with account number "+accountNumber+" does not exist.");
	}
	
	@Override
	public List<SavingsAccount> getAccountsBetweenMinMaxAccountBal(Double minBalance, Double maxBalance) throws SQLException, ClassNotFoundException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCOUNT WHERE account_balance between ? and ?");
		preparedStatement.setDouble(1, minBalance);
		preparedStatement.setDouble(2, maxBalance);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		preparedStatement.close();
		commit();
		return savingsAccounts;
	}

	
/*	
  	@Override
	public List<SavingsAccount> sortAccountsByAccountBalance(String sortWay) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = null;
		if(sortWay.equalsIgnoreCase("Ascending")){
			resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY ACCOUNT_BALANCE");
		}else{
			resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY ACCOUNT_BALANCE DESC");
		}
		
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		statement.close();
		commit();
		return savingsAccounts;
	}

	@Override
	public List<SavingsAccount> sortAccountsByAccountNumber(String sortWay) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = null;
		if(sortWay.equalsIgnoreCase("Ascending")){
			resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_id");
		}else{
			resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_id DESC");
		}
		
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}

	@Override
	public List<SavingsAccount> sortAccountsByAccountHolderName(String sortWay) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = null;
		if(sortWay.equalsIgnoreCase("Ascending")){
			resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_hn");
		}else{
			resultSet = statement.executeQuery("SELECT * FROM ACCOUNT ORDER BY account_hn DESC");
		}
		
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance,
					salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}

	*/

}
