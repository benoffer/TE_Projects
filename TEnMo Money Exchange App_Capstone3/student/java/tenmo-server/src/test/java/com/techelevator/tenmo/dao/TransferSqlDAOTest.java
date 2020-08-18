package com.techelevator.tenmo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.DAOIntegrationTest;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public class TransferSqlDAOTest extends DAOIntegrationTest {

	private JdbcTemplate jdbcTemplate;
	private TransferSqlDAO dao;
	private UserDAO uDao;
	private AccountDAO aDao; 
	
	@Test
	public void testTransfer() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferSqlDAO(jdbcTemplate);
 		uDao = new UserSqlDAO(jdbcTemplate);
 		aDao = new AccountSqlDAO(jdbcTemplate);
 		Transfer transfer = new Transfer();
 		
 		uDao.create("fromtester", "test");
 		uDao.create("totester", "test");
 		Double transferAmount = 500.00;
 		transfer.setFromAccountId(uDao.findIdByUsername("fromtester")); 
 		transfer.setToAccountId(uDao.findIdByUsername("totester"));
 		transfer.setTransferAmount(transferAmount);

 		Transfer transferResult = dao.transfer(transfer);
 					
		
 		Double fromExpected = (double) 500;
		Account fromActual = aDao.getBalance(uDao.findIdByUsername("fromtester"));
		
		Double toExpected = (double) 1500;
		Account toActual = aDao.getBalance(uDao.findIdByUsername("totester"));
 		
		String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferResult.getTransferId());
		result.next();
		Double actualTransferAmount = result.getDouble("amount");
				
		
 		assertEquals(fromExpected, fromActual.getBalance());
 		assertEquals(toExpected, toActual.getBalance());
 		assertEquals(transferAmount, actualTransferAmount);		
	}
	
	
	@Test
	public void testRequest() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferSqlDAO(jdbcTemplate);
 		uDao = new UserSqlDAO(jdbcTemplate);
 		aDao = new AccountSqlDAO(jdbcTemplate);
 		Transfer transfer = new Transfer();
 		
 		uDao.create("fromtester", "test");
 		uDao.create("totester", "test");
 		Double transferAmount = 500.00;
 		transfer.setFromAccountId(uDao.findIdByUsername("fromtester")); 
 		transfer.setToAccountId(uDao.findIdByUsername("totester"));
 		transfer.setTransferAmount(transferAmount);
 		
 		transfer = dao.request(transfer);
 		
 		assertEquals(1, transfer.getTransferTypeId());
 		assertEquals(1, transfer.getStatusId());

	}
	
	
	@Test
	public void testDenyRequest() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferSqlDAO(jdbcTemplate);
 		uDao = new UserSqlDAO(jdbcTemplate);
 		aDao = new AccountSqlDAO(jdbcTemplate);
 		Transfer transfer = new Transfer();
 		
 		uDao.create("fromtester", "test");
 		uDao.create("totester", "test");
 		Double transferAmount = 500.00;
 		transfer.setFromAccountId(uDao.findIdByUsername("fromtester")); 
 		transfer.setToAccountId(uDao.findIdByUsername("totester"));
 		transfer.setTransferAmount(transferAmount);
 		
 		transfer = dao.request(transfer);
 		
 		transfer = dao.denyTransfer(transfer);
 		transfer = dao.getTransferDetails(transfer.getTransferId());

 		
 		assertEquals(3, transfer.getStatusId());
 		
	}
	
	@Test
	public void testApproveRequest() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferSqlDAO(jdbcTemplate);
 		uDao = new UserSqlDAO(jdbcTemplate);
 		aDao = new AccountSqlDAO(jdbcTemplate);
 		Transfer transfer = new Transfer();
 		
 		uDao.create("fromtester", "test");
 		uDao.create("totester", "test");
 		Double transferAmount = 500.00;
 		transfer.setFromAccountId(uDao.findIdByUsername("fromtester")); 
 		transfer.setToAccountId(uDao.findIdByUsername("totester"));
 		transfer.setTransferAmount(transferAmount);
 		
 		transfer = dao.request(transfer);
 		
 		Transfer transferResult = dao.transfer(transfer);
 					
		
 		Double fromExpected = (double) 500;
		Account fromActual = aDao.getBalance(uDao.findIdByUsername("fromtester"));
		
		Double toExpected = (double) 1500;
		Account toActual = aDao.getBalance(uDao.findIdByUsername("totester"));
 		
		String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferResult.getTransferId());
		result.next();
		Double actualTransferAmount = result.getDouble("amount");
				
		
 		assertEquals(fromExpected, fromActual.getBalance());
 		assertEquals(toExpected, toActual.getBalance());
 		assertEquals(transferAmount, actualTransferAmount);		
 		
	}
	
	
	@Test
	public void testGetCompletedTransfers() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferSqlDAO(jdbcTemplate);
 		uDao = new UserSqlDAO(jdbcTemplate);
 		aDao = new AccountSqlDAO(jdbcTemplate);
 		Transfer transfer = new Transfer();
 		
 		uDao.create("fromtester", "test");
 		uDao.create("totester", "test");
 		Double transferAmount = 500.00;
 		transfer.setFromAccountId(uDao.findIdByUsername("fromtester")); 
 		transfer.setToAccountId(uDao.findIdByUsername("totester"));
 		transfer.setTransferAmount(transferAmount);

 		Transfer transferResult = dao.transfer(transfer);
 					
		Transfer[] results = dao.getFinishedTransfersByUserId(transferResult.getFromAccountId());
 		
 		assertEquals(1, results.length);		
	}

	
	@Test
	public void testGetPendingTransfers() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferSqlDAO(jdbcTemplate);
 		uDao = new UserSqlDAO(jdbcTemplate);
 		aDao = new AccountSqlDAO(jdbcTemplate);
 		Transfer transfer = new Transfer();
 		
 		uDao.create("fromtester", "test");
 		uDao.create("totester", "test");
 		Double transferAmount = 500.00;
 		transfer.setFromAccountId(uDao.findIdByUsername("fromtester")); 
 		transfer.setToAccountId(uDao.findIdByUsername("totester"));
 		transfer.setTransferAmount(transferAmount);

 		Transfer transferResult = dao.request(transfer);
 					
		Transfer[] results = dao.getPendingTransfersByUserId(transferResult.getFromAccountId());
 		
 		assertEquals(1, results.length);		
	}
}
