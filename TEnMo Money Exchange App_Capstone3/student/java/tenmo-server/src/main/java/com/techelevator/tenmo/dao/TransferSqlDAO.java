package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDAO implements TransferDAO {
	  
	private final int TRANSFER_TYPE_SEND = 2;
	  private final int TRANSFER_STATUS_APPROVED = 2;
	  private final int TRANSFER_STATUS_DENIED = 3;
	  private final int TRANSFER_TYPE_REQUEST = 1;
	  private final int TRANSFER_STATUS_PENDING = 1; 
	  
	  private JdbcTemplate jdbcTemplate;
	  private TransferDAO dao;
	  private AccountDAO aDao;
	  
	public TransferSqlDAO (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Transfer transfer(Transfer transfer) {
		dao = new TransferSqlDAO(jdbcTemplate);
		aDao = new AccountSqlDAO(jdbcTemplate);
		Transfer transferResult = null;
		Account fromAccount = new Account();
		fromAccount = aDao.getBalance(transfer.getFromAccountId());
		Double fromAccountBalance = fromAccount.getBalance();
		
		
		if (fromAccountBalance - transfer.getTransferAmount() >= 0) {			
			double newFromBalance = fromAccountBalance - transfer.getTransferAmount();
			
			double newToBalance = aDao.getBalance(transfer.getToAccountId()).getBalance() + transfer.getTransferAmount();
			
			if (aDao.updateBalance(transfer.getFromAccountId(), newFromBalance) == true && aDao.updateBalance(transfer.getToAccountId(), newToBalance) == true) {
				if (transfer.getTransferId() > 0) {
					transferResult = approveTransfer(transfer);
				} else {
					transferResult = createTransfer(transfer);
				}
			} else {
				System.out.println("Something went wrong, please try again.");
			}
		}
		return transferResult;
	}
	
	 private Transfer createTransfer(Transfer transfer) {
		String sqlTransfer = 
			     "INSERT INTO transfers ( "
			     + "transfer_type_id, transfer_status_id, account_from, account_to, amount)  "
			     + "VALUES (?, ?, ?, ?, ?) "
			     + "RETURNING transfer_id ";

		transfer.setTransferId(jdbcTemplate.queryForObject(sqlTransfer, Integer.class, TRANSFER_TYPE_SEND, TRANSFER_STATUS_APPROVED, 
				transfer.getFromAccountId(), transfer.getToAccountId(), transfer.getTransferAmount()));

		Transfer transferResult = getTransferDetails(transfer.getTransferId());
		
		return transferResult;
	}
	 
	 @Override
	 public Transfer denyTransfer(Transfer transfer) {
			String sqlTransfer = 
				     "UPDATE transfers "
				     + "SET transfer_status_id = ? "
				     + "WHERE transfer_id = ?";

			jdbcTemplate.update(sqlTransfer, TRANSFER_STATUS_DENIED, 
					transfer.getTransferId());

			Transfer transferResult = getTransferDetails(transfer.getTransferId());
			
			return transferResult;
		}
	
	 
	 private Transfer approveTransfer(Transfer transfer) {
			String sqlTransfer = 
				     "UPDATE transfers "
				     + "SET transfer_status_id = ? "
				     + "WHERE transfer_id = ?";

			jdbcTemplate.update(sqlTransfer, TRANSFER_STATUS_APPROVED, 
					transfer.getTransferId());

			Transfer transferResult = getTransferDetails(transfer.getTransferId());
			
			return transferResult;
		}
	 
	@Override
	public Transfer request(Transfer transfer) {
		String sqlTransfer = 
				"INSERT INTO transfers ( "
						+ "transfer_type_id, transfer_status_id, account_from, account_to, amount)  "
						+ "VALUES (?, ?, ?, ?, ?) "
						+ "RETURNING transfer_id ";
		
		transfer.setTransferId(jdbcTemplate.queryForObject(sqlTransfer, Integer.class, TRANSFER_TYPE_REQUEST, TRANSFER_STATUS_PENDING, 
				transfer.getFromAccountId(), transfer.getToAccountId(), transfer.getTransferAmount()));
		
		Transfer transferResult = getTransferDetails(transfer.getTransferId());
		
		return transferResult;
	}

	
	@Override
	public Transfer[] getFinishedTransfersByUserId(int id) {
		List<Transfer> transfers = new ArrayList<>();
		
		String sqlSelectTransfer = "SELECT t.*, fu.username AS from_user_name,"
				+ "tu.username AS to_user_name "
				+ "FROM transfers t "
				+ "JOIN users fu ON t.account_from = fu.user_id "
				+ "JOIN users tu ON t.account_to = tu.user_id "
				+ "WHERE (t.transfer_status_id = 2 "
				+ "OR t.transfer_status_id = 3) "
				+ "AND "
				+ "(fu.user_id = ? "
				+ "OR tu.user_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectTransfer, id, id);
		
		while(results.next()) {
			transfers.add(mapRowToTransfer(results));
		}
		Transfer[] idTransfers = new Transfer[transfers.size()];
		for (int i = 0; i < transfers.size(); i++) {
			idTransfers[i] = transfers.get(i);
		}
		return idTransfers;
	}
	
	@Override
	public Transfer[] getPendingTransfersByUserId(int id) {
		List<Transfer> transfers = new ArrayList<>();
		
		String sqlSelectTransfer = "SELECT t.*, fu.username AS from_user_name,"
				+ "tu.username AS to_user_name "
				+ "FROM transfers t "
				+ "JOIN users fu ON t.account_from = fu.user_id "
				+ "JOIN users tu ON t.account_to = tu.user_id "
				+ "WHERE t.transfer_status_id = 1 "
				+ "AND "
				+ "(fu.user_id = ? "
				+ "OR tu.user_id = ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectTransfer, id, id);
		
		while(results.next()) {
			transfers.add(mapRowToTransfer(results));
		}
		Transfer[] idTransfers = new Transfer[transfers.size()];
		for (int i = 0; i < transfers.size(); i++) {
			idTransfers[i] = transfers.get(i);
		}
		return idTransfers;
	}
	


	private Transfer mapRowToTransfer (SqlRowSet result) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(result.getInt("transfer_id"));
		transfer.setFromAccountId(result.getInt("account_from"));
		transfer.setFromAccountName(result.getString("from_user_name"));
		transfer.setToAccountId(result.getInt("account_to"));
		transfer.setToAccountName(result.getString("to_user_name"));
		transfer.setStatusId(result.getInt("transfer_status_id"));
		transfer.setTransferAmount(result.getDouble("amount"));
		transfer.setTransferTypeId(result.getInt("transfer_type_id"));
		return transfer;
	}

@Override
public Transfer getTransferDetails (int id) {
	Transfer transfer = new Transfer();
	String sqlSelectTransfer = "SELECT t.*, fu.username AS from_user_name,"
			+ "tu.username AS to_user_name "
			+ "FROM transfers t "
			+ "JOIN users fu ON t.account_from = fu.user_id "
			+ "JOIN users tu ON t.account_to = tu.user_id "
			+ "WHERE t.transfer_id = ? ";
	SqlRowSet result = jdbcTemplate.queryForRowSet(sqlSelectTransfer, id);
	while (result.next()) {
		transfer = mapRowToTransfer(result);			
	}
	return transfer;
}
	
}
