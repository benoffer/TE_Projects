package com.techelevator.tenmo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferTypeSqlDAO implements TransferTypeDAO {

	private Map<Integer, String> transferTypes = new HashMap<>();

	private JdbcTemplate jdbcTemplate;
	private TransferTypeDAO dao;

	public TransferTypeSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Transfer applyTypeStringsToTransfer(Transfer transfer) {
		dao = new TransferTypeSqlDAO(jdbcTemplate);
		
		String sqlSelect = "SELECT * FROM transfer_types";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect);
		
		while(results.next()) {
			transferTypes.put(results.getInt("transfer_type_id"), results.getString("transfer_type_desc"));
		}
		
		transfer.setTransferTypeString(transferTypes.get(transfer.getTransferTypeId()));

		return transfer;
			
	}
	
	@Override
	public Transfer[] applyTypeStringsToTransfers(Transfer[] transfers) {
		dao = new TransferTypeSqlDAO(jdbcTemplate);
		
		String sqlSelect = "SELECT * FROM transfer_types";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect);
		
		while(results.next()) {
			transferTypes.put(results.getInt("transfer_type_id"), results.getString("transfer_type_desc"));
		}
		
		for (Transfer transfer : transfers) {
			transfer.setTransferTypeString(transferTypes.get(transfer.getTransferTypeId()));
		}

		return transfers;
	}

	
}
