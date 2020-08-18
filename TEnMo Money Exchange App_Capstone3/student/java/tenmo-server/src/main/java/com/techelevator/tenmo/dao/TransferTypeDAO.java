package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferTypeDAO {

	Transfer applyTypeStringsToTransfer(Transfer transfer);

	Transfer[] applyTypeStringsToTransfers(Transfer[] transfers);

}
