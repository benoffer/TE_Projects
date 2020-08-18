package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferStatusDAO {

	Transfer applyStatusStringsToTransfer(Transfer transfer);

	Transfer[] applyStatusStringsToTransfers(Transfer[] transfers);

}
