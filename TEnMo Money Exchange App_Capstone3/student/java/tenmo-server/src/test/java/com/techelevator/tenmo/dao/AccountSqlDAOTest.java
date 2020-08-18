package com.techelevator.tenmo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.DAOIntegrationTest;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public class AccountSqlDAOTest extends DAOIntegrationTest {

	private JdbcTemplate jdbcTemplate;
	private AccountSqlDAO dao;
	private UserDAO uDao;

	@Test
	public void testGetBalance() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
 		uDao = new UserSqlDAO(jdbcTemplate);
 		dao = new AccountSqlDAO(jdbcTemplate);
		
		//Create user that should have 1k balance
		uDao.create("testergetterXX", "testerpass");
		int id = uDao.findIdByUsername("testergetterXX");
		Account actual = dao.getBalance(id);
		Double expected = (double) 1000;
		
		assertEquals(expected,(Double) actual.getBalance());
	}
	
	@Test
	public void updateBalance() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
 		uDao = new UserSqlDAO(jdbcTemplate);
 		dao = new AccountSqlDAO(jdbcTemplate);
		
		//Create user that should have 1k balance
		uDao.create("testergetter", "testerpass");
		int id = uDao.findIdByUsername("testergetter");
		Account actual = dao.getBalance(id);
		Double expectedOriginalBalance = (double) 1000;

		dao.updateBalance(id, 1500.00);
		Account actualUpdated = dao.getBalance(id);

		
		Double expectedUpdatedBalance = (double) 1500;

		//check origin balance
		assertEquals(expectedOriginalBalance,(Double) actual.getBalance());
		//check updated balance
		assertEquals(expectedUpdatedBalance,(Double) actualUpdated.getBalance());

	}
	

}
