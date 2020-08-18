package com.techelevator.tenmo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferStatusSqlDAO implements TransferStatusDAO {

	private Map<Integer, String> transferStatuses = new HashMap<>();

	private JdbcTemplate jdbcTemplate;
	private TransferStatusDAO dao;

	public TransferStatusSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Transfer applyStatusStringsToTransfer(Transfer transfer) {
		dao = new TransferStatusSqlDAO(jdbcTemplate);
		
		String sqlSelect = "SELECT * FROM transfer_statuses";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect);
		
		while(results.next()) {
			transferStatuses.put(results.getInt("transfer_status_id"), results.getString("transfer_status_desc"));
		}
		
		transfer.setTransferStatusString(transferStatuses.get(transfer.getStatusId()));

		return transfer;
			
	}
	
	@Override
	public Transfer[] applyStatusStringsToTransfers(Transfer[] transfers) {
		dao = new TransferStatusSqlDAO(jdbcTemplate);
		
		String sqlSelect = "SELECT * FROM transfer_statuses";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect);
		
		while(results.next()) {
			transferStatuses.put(results.getInt("transfer_status_id"), results.getString("transfer_status_desc"));
		}
		
		for (Transfer transfer : transfers) {
			transfer.setTransferStatusString(transferStatuses.get(transfer.getStatusId()));
		}

		return transfers;
	}

	
}
