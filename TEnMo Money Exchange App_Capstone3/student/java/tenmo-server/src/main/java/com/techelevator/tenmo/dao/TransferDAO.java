package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	Transfer transfer(Transfer transfer);
	Transfer[] getFinishedTransfersByUserId(int id);
	Transfer request(Transfer transfer);
	Transfer getTransferDetails(int id);
	Transfer[] getPendingTransfersByUserId(int id);
	Transfer denyTransfer(Transfer transfer);
	
}
