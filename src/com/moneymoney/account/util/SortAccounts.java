/**
 * 
 */
package com.moneymoney.account.util;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.ui.AccountCUI;

/**
 * @author ugawari
 *
 */
public class SortAccounts {

	
	public static Boolean sortByAccountHolderName(String sortWay, List<SavingsAccount> savingsAccounts) {
		Boolean result = false;
		if(sortWay.equalsIgnoreCase("Ascending")){
			Collections.sort(savingsAccounts, new Comparator<SavingsAccount>(){
				@Override
				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
					return (accountOne.getBankAccount().getAccountHolderName()).compareToIgnoreCase(accountTwo.getBankAccount().getAccountHolderName());
				}
			});
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			result = true;
		}else if(sortWay.equalsIgnoreCase("Descending")){
			Collections.sort(savingsAccounts, new Comparator<SavingsAccount>(){
				@Override
				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
					return (accountTwo.getBankAccount().getAccountHolderName()).compareToIgnoreCase(accountOne.getBankAccount().getAccountHolderName());
				}
			});
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			result = true;
		}
		return result;
	}
	
	public static Boolean sortByAccountNumber(String sortWay, List<SavingsAccount> savingsAccounts) {
		Boolean result = false;
		if(sortWay.equalsIgnoreCase("Ascending")){
			   Collections.sort(savingsAccounts, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
						return accountOne.getBankAccount().getAccountNumber()-accountTwo.getBankAccount().getAccountNumber();
					}
			   });
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			result = true;
		}else if(sortWay.equalsIgnoreCase("Descending")){
				Collections.sort(savingsAccounts, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
						return accountTwo.getBankAccount().getAccountNumber()-accountOne.getBankAccount().getAccountNumber();
					}
				});
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
				result = true;
		}
		return result;
	}

	public static Boolean sortByAccountBalance(String sortWay, List<SavingsAccount> savingsAccounts) {
		Boolean result = false;
		if(sortWay.equalsIgnoreCase("Ascending")){
			Collections.sort(savingsAccounts, new Comparator<SavingsAccount>(){
				@Override
				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
					int result = 0;
					if(accountOne.getBankAccount().getAccountBalance() == accountTwo.getBankAccount().getAccountBalance()){
						result = 0;
					}else if(accountOne.getBankAccount().getAccountBalance() > accountTwo.getBankAccount().getAccountBalance()){
						result = 1;
					}else if(accountOne.getBankAccount().getAccountBalance() < accountTwo.getBankAccount().getAccountBalance()){
						result = -1;
					}
					return result;
				}
			});
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			result = true;
		}else if(sortWay.equalsIgnoreCase("Descending")){
			Collections.sort(savingsAccounts, new Comparator<SavingsAccount>(){
				@Override
				public int compare(SavingsAccount accountOne, SavingsAccount accountTwo) {
					int result = 0;
					if(accountOne.getBankAccount().getAccountBalance() == accountTwo.getBankAccount().getAccountBalance()){
						result = 0;
					}else if(accountTwo.getBankAccount().getAccountBalance() > accountOne.getBankAccount().getAccountBalance()){
						result = 1;
					}else if(accountTwo.getBankAccount().getAccountBalance() < accountOne.getBankAccount().getAccountBalance()){
						result = -1;
					}
					return result;
				}
			});
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			result = true;
		}
		return result;
	}
}
