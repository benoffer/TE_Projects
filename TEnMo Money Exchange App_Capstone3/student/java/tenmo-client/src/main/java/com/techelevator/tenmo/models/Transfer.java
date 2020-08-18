package com.techelevator.tenmo.models;

import java.text.NumberFormat;

public class Transfer {

	private int transferId;
	private int fromAccountId;
	private String fromAccountName;
	private int toAccountId;
	private String toAccountName;
	private double transferAmount;
	private int statusId;
	private int transferTypeId;
	private String transferTypeString;
	private String transferStatusString;
	
	
	private static final String TRANSFER_HEADER_FORMAT = "%5s%-10s%-10s\n" + "%5s%-10s%-10s %-20s %-20s %12s";
	private static final String TRANSFER_FORMAT = "%-10s%-10s %-20s %-20s %12s";
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();

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

	public double getTransferAmount() {
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

	public String printTransferDetails() {
		String detailsFormat = "--------------------------------------------------\n" + "%s Details\n"
				+ "--------------------------------------------------\n" + "%12s %s\n" + "%12s %s\n" + "%12s %s\n"
				+ "%12s %s\n" + "%12s %s\n" + "%12s %s\n" + "--------------------------------------------------\n";

		return String.format(detailsFormat, this.transferTypeString.equals("Request") ? this.transferTypeString : "Transfer",

				"ID:", this.transferId, "From:", this.fromAccountName, "To:", this.toAccountName, "Type:",
				this.transferTypeString, "Status:", this.transferStatusString, "Amount:",
				formatter.format(this.transferAmount));
	}

	public static String getHeader() {
		return String.format(TRANSFER_HEADER_FORMAT, "", "Status", "Transfer", "", "", "ID", "From", "To", "Amount");
	}

	@Override
	public String toString() {
		return String.format(TRANSFER_FORMAT, this.transferStatusString, this.transferId, this.fromAccountName, this.toAccountName,
				formatter.format(this.transferAmount));
	}

}
