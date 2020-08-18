package com.techelevator.tenmo.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.TransferStatusDAO;
import com.techelevator.tenmo.dao.TransferTypeDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;




//@PreAuthorize("hasRole('ROLE_USER')")
@PreAuthorize("isAuthenticated()")
@RestController
public class TenmoController {
	
    private AccountDAO aDao;
    private TransferDAO tDao;
    private UserDAO uDao;
    private TransferStatusDAO tsDao;
    private TransferTypeDAO ttDao;

	    public TenmoController(AccountDAO aDao, TransferDAO tDao, UserDAO uDao, TransferStatusDAO tsDao, TransferTypeDAO ttDao) {
	       this.aDao = aDao;
	       this.tDao = tDao;
	       this.uDao = uDao;
	       this.tsDao = tsDao;
	       this.ttDao = ttDao;
	    }
	    
	    
	    @RequestMapping(path = "accounts/{id}", method = RequestMethod.GET)
	    public Account getBalance(@PathVariable int id)  {
	        return aDao.getBalance(id);
	    }
	    
	    @RequestMapping(path = "transfer", method = RequestMethod.POST)
	    public Transfer transfer(@Valid @RequestBody Transfer transfer) {
	    	transfer = tDao.transfer(transfer);
	    	transfer = tsDao.applyStatusStringsToTransfer(transfer);
	    	transfer = ttDao.applyTypeStringsToTransfer(transfer);
	    	return transfer;
	    }
	    
	    @RequestMapping(path = "reject", method = RequestMethod.POST)
	    public Transfer reject(@Valid @RequestBody Transfer transfer) {
	    	transfer = tDao.denyTransfer(transfer);
	    	transfer = tsDao.applyStatusStringsToTransfer(transfer);
	    	transfer = ttDao.applyTypeStringsToTransfer(transfer);
	    	return transfer;
	    }
	    
	    @RequestMapping(path = "accounts/{id}/transfers", method = RequestMethod.GET)
	    public Transfer[] getFinishedTransfersByUserId(@PathVariable int id) {
	    	Transfer[] transfers = tDao.getFinishedTransfersByUserId(id);
	    	transfers = tsDao.applyStatusStringsToTransfers(transfers);
	    	transfers = ttDao.applyTypeStringsToTransfers(transfers);
	    	return transfers;
	    }
	    
	    @RequestMapping(path = "accounts/{id}/pending", method = RequestMethod.GET)
	    public Transfer[] getPendingTransfersByUserId(@PathVariable int id) {
	    	Transfer[] transfers = tDao.getPendingTransfersByUserId(id);
	    	transfers = tsDao.applyStatusStringsToTransfers(transfers);
	    	transfers = ttDao.applyTypeStringsToTransfers(transfers);
	    	return transfers;
	    }
	    	    
	    @RequestMapping(path = "accounts", method = RequestMethod.GET)
	    public User[] getAccountUsers()  {
	        return uDao.findAll();
	    }
	    
	    @RequestMapping(path = "request", method = RequestMethod.POST)
	    public Transfer request(@Valid @RequestBody Transfer transfer) {
	    	transfer = tDao.request(transfer);
	    	transfer = tsDao.applyStatusStringsToTransfer(transfer);
	    	transfer = ttDao.applyTypeStringsToTransfer(transfer);
	    	return transfer;
	    }
	 
//	    DIDN'T END UP NEEDING THIS SINCE WE ALREADY HAVE TRANSFER DETAILS FROM WHEN WE ASK FOR THE LIST.
//	    @RequestMapping(path = "transfer/{id}", method = RequestMethod.GET)
//	    public Transfer getTransferDetails(@PathVariable int id) {
//	    	return tDao.getTransferDetails(id);
//	    }
	    
	
}
