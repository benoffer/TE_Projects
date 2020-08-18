package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface AccountDAO {

	Account getBalance(int accountId);

	boolean updateBalance(int accountId, Double newBalance);
	
}
