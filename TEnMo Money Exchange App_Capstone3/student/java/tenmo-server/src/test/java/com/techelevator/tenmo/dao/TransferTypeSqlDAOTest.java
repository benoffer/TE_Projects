package com.techelevator.tenmo.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.DAOIntegrationTest;
import com.techelevator.tenmo.model.Transfer;

public class TransferTypeSqlDAOTest extends DAOIntegrationTest {
	
	private JdbcTemplate jdbcTemplate;
	private TransferTypeSqlDAO dao;

	@Test
	public void testApplyTypeString() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferTypeSqlDAO(jdbcTemplate);
		Transfer transfer = new Transfer();
		transfer.setTransferTypeId(1);
		dao.applyTypeStringsToTransfer(transfer);
		
		assertEquals("Request",transfer.getTransferTypeString());
		
	}

	@Test
	public void testApplyTypeStrings() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		dao = new TransferTypeSqlDAO(jdbcTemplate);
		Transfer transfer1 = new Transfer();
		Transfer transfer2 = new Transfer();
		transfer1.setTransferTypeId(1);
		transfer2.setTransferTypeId(2);
		Transfer[] transfers = new Transfer[] {transfer1,transfer2};
		transfers = dao.applyTypeStringsToTransfers(transfers);
		
		assertEquals("Request",transfers[0].getTransferTypeString());
		assertEquals("Send",transfers[1].getTransferTypeString());
		
	}

}
