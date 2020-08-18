package com.techelevator.tenmo.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;

@Service
public class AccountSqlDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate;
	  
	public AccountSqlDAO (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	  
	  
	@Override
	public Account getBalance(int userId) {		
		
		String sqlSelectBalance = "SELECT * "
								+ "FROM accounts  "
								+ "WHERE user_id = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlSelectBalance, userId);
		result.next();
		Account account = mapRowToAccount(result);
		return account;
	}
	
	@Override
	public boolean updateBalance(int accountId, Double newBalance) {
		
		String sqlUpdate = 
			     "UPDATE accounts "
			   + "SET balance = ? "
			   + "WHERE user_id = ? ";

		try {
			jdbcTemplate.update(sqlUpdate, newBalance, accountId);
			return true;
		} catch (DataAccessException e) {
			System.out.println("Account Not Found");
			return false;
		}
	
	}
	
	

	private Account mapRowToAccount(SqlRowSet result) {
		Account account = new Account();
		account.setAccountId(result.getInt("account_id"));
		account.setUserId(result.getInt("user_id"));
		account.setBalance(result.getDouble("balance"));
		return account;
	}
	

	
}
