package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

import com.techelevator.tenmo.models.Account;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options, String header, String footer, String numberFormat, boolean hasQuit) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options, header, footer, numberFormat, hasQuit);
			choice = getChoiceFromUserInput(options, hasQuit);
		}
		return choice;
	}
	
	private Object getChoiceFromUserInput(Object[] options, boolean hasQuit) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			if(hasQuit && userInput.equalsIgnoreCase("Q")) {
				choice = "Quit";
			}else {
				if(userInput.equals("0")) {
					choice = "Back";
				} else {
					int selectedOption = Integer.valueOf(userInput);
					if(selectedOption <= options.length) {
						choice = options[selectedOption - 1];
					}
				}
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}
		return choice;
	}

	public void displayMenuOptions(Object[] options, String header, String footer, String numberFormat, boolean hasQuit) {
		out.println();
		
		if (header.length() > 0) {
			printBreakLine();
			out.print(header+"\n");
			printBreakLine();
		}

		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.printf(((numberFormat.length() > 0) ? numberFormat : "%3s)") + " %s\n", optionNum, options[i]);
			//%4s)
		}

		if (hasQuit) {
			out.printf(((numberFormat.length() > 0) ? numberFormat : "%3s)") + " %s\n", "Q", "Quit");
		}
		if(footer.length() > 0) {
			out.print("\n" + footer + " ");
		}
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}
	
	public Double getUserInputDouble(String prompt) {
		Double result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Double.parseDouble(userInput);
				
				BigDecimal bd = BigDecimal.valueOf(result);
				if (bd.scale() < 3 && bd.compareTo(BigDecimal.ZERO) > 0) {
					continue;
				} else {
					out.println("\n*** " + userInput + " has too many decimal places or is negative ***\n");
					result = null;
				}
							
				
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}
	
	
	public void printBalance(Account account) {
		printBreakLine();
		out.print(String.format("Your current account balance is: %s\n", formatter.format(account.getBalance())));
		out.flush();
		printBreakLine();

	}
	
	
	public void printBreakLine() {
		out.print("--------------------------------------------------------------------------------\n");
		out.flush();
	}
	
	public boolean getYTrueOrNFalseFromUser(String requestText) {
		while(true) {
			String userInput = getUserInput(requestText + "(Y or N)");
			if(userInput.equalsIgnoreCase("Y")) {
				return true;
			}
			if(userInput.equalsIgnoreCase("N")) {
				return false;
			}
			out.println("Invalid entry.\n");
			out.flush();
		}
	}
	
}
