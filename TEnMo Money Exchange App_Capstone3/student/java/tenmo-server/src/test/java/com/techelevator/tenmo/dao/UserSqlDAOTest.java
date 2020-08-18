package com.techelevator.tenmo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.DAOIntegrationTest;
import com.techelevator.tenmo.model.User;

public class UserSqlDAOTest extends DAOIntegrationTest{

	private JdbcTemplate jdbcTemplate;
	private UserDAO dao;
	private AccountSqlDAO aDao;
	
	
	@Test
	public void testFindIdByUsername() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
 		dao = new UserSqlDAO(jdbcTemplate);
 		
 		dao.create("testertester", "testerpass");
		int actualId = dao.findIdByUsername("testertester");
 		int expectedId = jdbcTemplate.queryForObject("select user_id from users where username = 'testertester'", int.class);
				
 		assertEquals(expectedId, actualId);
	}

	@Test
	public void testFindAll() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
 		dao = new UserSqlDAO(jdbcTemplate);
 		
 		dao.create("userA", "password");
 		dao.create("userB", "password");
 		
 		User[] actualTotalUsers = dao.findAll();
 		int expectedCount = 0;
 		SqlRowSet result = jdbcTemplate.queryForRowSet("SELECT COUNT(*) FROM users");
 		result.next();
 		expectedCount = result.getInt(1);
 		
 		assertEquals(actualTotalUsers.length, expectedCount);		
	}

	@Test
	public void testFindByUsername() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
 		dao = new UserSqlDAO(jdbcTemplate);
 		
 		dao.create("userA", "password");
 		
 		User expectedUser = dao.findByUsername("userA");
 		 
 		assertEquals("userA", expectedUser.getUsername());
	}

	@Test
	public void testCreate() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
 		dao = new UserSqlDAO(jdbcTemplate);
 		
 		dao.create("userA", "password");
 		
 		User expectedUser = dao.findByUsername("userA");
 		
 		assertEquals("userA", expectedUser.getUsername());

	}

}
