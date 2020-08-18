package com.techelevator.tenmo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.DAOIntegrationTest;
import com.techelevator.tenmo.model.Transfer;

public class TransferStatusSqlDAOTest extends DAOIntegrationTest {
	
	private JdbcTemplate jdbcTemplate;
	private TransferStatusSqlDAO dao;

	@Test
	public void testApplyStatusString() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferStatusSqlDAO(jdbcTemplate);
		Transfer transfer = new Transfer();
		transfer.setStatusId(1);
		dao.applyStatusStringsToTransfer(transfer);
		
		assertEquals("Pending",transfer.getTransferStatusString());
		
	}
	
	@Test
	public void testApplyStatusStrings() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferStatusSqlDAO(jdbcTemplate);
		Transfer transfer1 = new Transfer();
		Transfer transfer2 = new Transfer();
		transfer1.setStatusId(1);
		transfer2.setStatusId(2);
		Transfer[] transfers = new Transfer[] {transfer1,transfer2};
		transfers = dao.applyStatusStringsToTransfers(transfers);
		
		assertEquals("Pending",transfers[0].getTransferStatusString());
		assertEquals("Approved",transfers[1].getTransferStatusString());
		
	}

	

}
