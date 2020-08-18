package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

public class Transfer {

	private int transferId;
	@Min(value = 1, message = "The fromAccountId field is required.")
	private int fromAccountId;
	private String fromAccountName;
	@Min(value = 1, message = "The toAccountId field is required.")
	private int toAccountId;
	private String toAccountName;
	@DecimalMin(value = "0.01", message = "Transfer amount must be greater than 0.")
	private Double transferAmount;
	private int statusId;
	private int transferTypeId;
	private String transferTypeString;
	private String transferStatusString;
	
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public int getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(int fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public String getFromAccountName() {
		return fromAccountName;
	}
	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}
	public int getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}
	public String getToAccountName() {
		return toAccountName;
	}
	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}
	public Double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public String getTransferTypeString() {
		return transferTypeString;
	}
	public void setTransferTypeString(String transferTypeString) {
		this.transferTypeString = transferTypeString;
	}
	public String getTransferStatusString() {
		return transferStatusString;
	}
	public void setTransferStatusString(String transferStatusString) {
		this.transferStatusString = transferStatusString;
	}
	

	
	
	
	
	
	
}
