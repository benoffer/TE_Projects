package com.techelevator.tenmo;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private TenmoService tenmo;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL),
				new TenmoService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, TenmoService tenmo) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.tenmo = tenmo;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS, "", "", "", false);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		console.printBalance(tenmo.getBalance(currentUser.getUser().getId()));
		console.getUserInput("Press enter to continue.");
	}

	private void viewTransferHistory() {
		Transfer transfer = new Transfer();
		Object choice = console.getChoiceFromOptions(tenmo.getFinishedTransfersByUserId(currentUser.getUser().getId()),
				Transfer.getHeader(), "Select a Transfer for More Details or 0 to Exit", "", false);

		if (choice.equals("Back")) {
			return;
		}
		transfer = (Transfer) choice;
		System.out.print(transfer.printTransferDetails());
		console.getUserInput("Press enter to continue.");
	}

	private void viewPendingRequests() {
		Transfer transfer = new Transfer();
		Object choice = console.getChoiceFromOptions(tenmo.getPendingTransfersByUserId(currentUser.getUser().getId()),
				Transfer.getHeader(), "Select a Transfer for More Details or 0 to Exit", "", false);

		if (choice.equals("Back")) {
			return;
		}
		transfer = (Transfer) choice;

		if (transfer.getFromAccountId() == currentUser.getUser().getId()) {
			System.out.print(transfer.printTransferDetails());
			
			if (console.getYTrueOrNFalseFromUser("Would you like to approve this request?")) {
				Transfer transferResult = tenmo.transfer(transfer);

				if (transferResult == null) {
					System.out.println("Transfer Invalid, Check for Sufficient Funds");
					console.getUserInput("Press enter to continue.");
					return;
				}
				System.out.print(transferResult.printTransferDetails());
				console.getUserInput("Your transfer is complete. Press enter to continue.");
			} else {
				System.out.print(tenmo.reject(transfer).printTransferDetails());//Deny Request
				console.getUserInput("You rejected the transfer. Press enter to continue.");

			}
			
		} else {
			System.out.print(transfer.printTransferDetails());
			console.getUserInput("Press enter to continue.");
		}

	}

	private void sendBucks() {
		Transfer transfer = new Transfer();
		transfer.setFromAccountId(currentUser.getUser().getId());
		transfer.setFromAccountName(currentUser.getUser().getUsername());
		User user = (User) console.getChoiceFromOptions(tenmo.getAccountUsers(), User.userHeader(),
				"Select a user to Transfer Money to (or enter 0 to go back): ", "", false);
		transfer.setToAccountId(user.getId());
		transfer.setToAccountName(user.getUsername());
		transfer.setTransferAmount(console.getUserInputDouble("Enter the amount you'd like to transfer"));

		Transfer transferResult = tenmo.transfer(transfer);

		if (transferResult == null) {
			System.out.println("Transfer Invalid, Check for Sufficient Funds");
			console.getUserInput("Press enter to continue.");
			return;
		}
		System.out.print(transferResult.printTransferDetails());
		console.getUserInput("Your transfer is complete. Press enter to continue.");

	}

	private void requestBucks() {
		Transfer transfer = new Transfer();
		transfer.setToAccountId(currentUser.getUser().getId());
		transfer.setToAccountName(currentUser.getUser().getUsername());
		User user = (User) console.getChoiceFromOptions(tenmo.getAccountUsers(), User.userHeader(),
				"Select a user to Request Money from (or enter 0 to go back): ", "", false);
		transfer.setFromAccountId(user.getId());
		transfer.setFromAccountName(user.getUsername());
		transfer.setTransferAmount(console.getUserInputDouble("Enter the amount you'd like to request"));

		Transfer transferResult = tenmo.request(transfer);

		if (transferResult == null) {
			System.out.println("Request Invalid.");
			console.getUserInput("Press enter to continue.");
			return;
		}
		System.out.print(transferResult.printTransferDetails());
		console.getUserInput("Your Request has been made and is pending approval. Press enter to continue.");

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS, "", "", "", false);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
				tenmo.setAUTH_TOKEN(currentUser.getToken());
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
