package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class TenmoService {

	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public TenmoService(String url) {
		this.BASE_URL = url;
	}

	public Account getBalance(int id) {
		Account account = new Account();
		try {
			account = restTemplate
					.exchange(BASE_URL + "accounts/" + id, HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return account;
	}

	public Transfer[] getFinishedTransfersByUserId(int id) {
		Transfer[] transfers = null;
		try {
			transfers = restTemplate.exchange(BASE_URL + "accounts/" + id + "/transfers", HttpMethod.GET,
					makeAuthEntity(), Transfer[].class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return transfers;
	}
	
	public Transfer[] getPendingTransfersByUserId(int id) {
		Transfer[] transfers = null;
		try {
			transfers = restTemplate.exchange(BASE_URL + "accounts/" + id + "/pending", HttpMethod.GET,
					makeAuthEntity(), Transfer[].class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return transfers;
	}
	

	public Transfer transfer(Transfer transfer) {
		try {
			transfer = restTemplate.exchange(BASE_URL + "transfer", HttpMethod.POST,
					makeTransferEntity(transfer), Transfer.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return transfer;
	}
	
	public Transfer reject(Transfer transfer) {
		try {
			transfer = restTemplate.exchange(BASE_URL + "reject", HttpMethod.POST,
					makeTransferEntity(transfer), Transfer.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return transfer;
	}
	
	public Transfer request(Transfer transfer) {
		try {
			transfer = restTemplate.exchange(BASE_URL + "request", HttpMethod.POST,
					makeTransferEntity(transfer), Transfer.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return transfer;
	}
	
	
	public User[] getAccountUsers() {
		User[] users = null;
		try {
			users = restTemplate.exchange(BASE_URL + "accounts", HttpMethod.GET,
					makeAuthEntity(), User[].class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getResponseBodyAsString());
		}
		return users;
	}

	/**
	 * Creates a new {HttpEntity} with the `Authorization: Bearer:` header and a
	 * reservation request body
	 * 
	 * @param reservation
	 * @return
	 */
	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		return entity;
	}

	public static void setAUTH_TOKEN(String aUTH_TOKEN) {
		AUTH_TOKEN = aUTH_TOKEN;
	}

	/**
	 * Returns an {HttpEntity} with the `Authorization: Bearer:` header
	 * 
	 * @return {HttpEntity}
	 */
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

//	DIDN'T END UP NEEDING THIS SINCE WE ALREADY HAVE TRANSFER DETAILS FROM WHEN WE ASK FOR THE LIST.
//	public Transfer getTransferDetails(int id) {
//		Transfer transfer = new Transfer();
//		try {
//			transfer = restTemplate
//					.exchange(BASE_URL + "transfer/" + id, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
//		} catch (RestClientResponseException e) {
//			System.out.println(e.getResponseBodyAsString());
//		}
//		return transfer;
//	}
	
}
