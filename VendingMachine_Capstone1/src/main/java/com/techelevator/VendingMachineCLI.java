package com.techelevator;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import com.techelevator.view.Menu;
 
public class VendingMachineCLI {

	//MAIN MENU OPTIONS
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_SHAKE = "Kick and Shake the Machine";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALESREPORT = "Secret Sales Report";
	//MAIN MENU ARRAY
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_SHAKE,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALESREPORT };

	//PURCHASE SUB-MENU OPTIONS
	private static final String PURCHASE_SUBMENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_SUBMENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_SUBMENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	//PURCHASE SUB-MENU ARRAY
	private static final String[] PURCHASE_SUBMENU_OPTIONS = { PURCHASE_SUBMENU_OPTION_FEED_MONEY,
			PURCHASE_SUBMENU_OPTION_SELECT_PRODUCT, PURCHASE_SUBMENU_OPTION_FINISH_TRANSACTION };

	//FEED MONEY SUB-MENU OPTIONS
	private static final String FEED_MONEY_SUBMENU_OPTION_FEED_1 = "Feed $ 1.00";
	private static final String FEED_MONEY_SUBMENU_OPTION_FEED_2 = "Feed $ 2.00";
	private static final String FEED_MONEY_SUBMENU_OPTION_FEED_5 = "Feed $ 5.00";
	private static final String FEED_MONEY_SUBMENU_OPTION_FEED_10 = "Feed $10.00";
	private static final String FEED_MONEY_SUBMENU_OPTION_DONE_FEEDING = "Done Inserting Money";
	//FEED MONEY SUB-MENU ARRAY
	private static final String[] FEED_MONEY_SUBMENU_OPTIONS = { FEED_MONEY_SUBMENU_OPTION_FEED_1,
			FEED_MONEY_SUBMENU_OPTION_FEED_2, FEED_MONEY_SUBMENU_OPTION_FEED_5, FEED_MONEY_SUBMENU_OPTION_FEED_10,
			FEED_MONEY_SUBMENU_OPTION_DONE_FEEDING };

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws FileNotFoundException {
		InventoryLoader loader = new InventoryLoader();
//		SalesLoader sloader = new SalesLoader();
//		sloader.salesTracker();
		VendingMachine machine = new VendingMachine(loader.fileImporter());
		
		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

				if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					machine.displayInventory();

				} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					// PURCHASE SUB-MENU CODE
					boolean purchaseIsLooping = true;
					while (purchaseIsLooping == true) {
						choice = (String) menu.getChoiceFromOptions(PURCHASE_SUBMENU_OPTIONS);
						if (choice.equals(PURCHASE_SUBMENU_OPTION_FEED_MONEY)) {
							// FEED_MONEY SUB-MENU CODE
							boolean feedIsLooping = true;
							while (feedIsLooping == true) {
								choice = (String) menu.getChoiceFromOptions(FEED_MONEY_SUBMENU_OPTIONS);
								if (choice.equals(FEED_MONEY_SUBMENU_OPTION_FEED_1)) {
									machine.feedMoney(new BigDecimal(1.00));
								} else if (choice.equals(FEED_MONEY_SUBMENU_OPTION_FEED_2)) {
									machine.feedMoney(new BigDecimal(2.00));
								} else if (choice.equals(FEED_MONEY_SUBMENU_OPTION_FEED_5)) {
									machine.feedMoney(new BigDecimal(5.00));
								} else if (choice.equals(FEED_MONEY_SUBMENU_OPTION_FEED_10)) {
									machine.feedMoney(new BigDecimal(10.00));
								} else if (choice.equals(FEED_MONEY_SUBMENU_OPTION_DONE_FEEDING)) {
									System.out.printf("%n************************************%nYou have inserted a total of $%s.%n",
											machine.getBalance().toString());
									feedIsLooping = false;
									// exit
								}
							}
						} else if (choice.equals(PURCHASE_SUBMENU_OPTION_SELECT_PRODUCT)) {

							machine.dispenseItem(machine.getUserInputforItemSelection());;


						} else if (choice.equals(PURCHASE_SUBMENU_OPTION_FINISH_TRANSACTION)) {
							System.out.printf(machine.printChange(machine.dispenseChange()));
							try {
							
									} catch (Exception ex) {
										System.out.println("General Exception at:" + ex.getMessage());
									}

							purchaseIsLooping = false;
						}
					}
				} else if (choice.equals(MAIN_MENU_OPTION_SHAKE)) {
					// KICK AND SHAKE MACHINE

					try {
						machine.shakeMachine();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
					// exit
					System.out.println();
					System.out.println();
					System.out.println("Thank you, have a nice day.");
					break;
				} else if (choice.equals(MAIN_MENU_OPTION_SALESREPORT)) {
					// TO BE IMPLEMENTED
					System.out.println();
					System.out.println();
					System.out.println(">>>>>Your Sales Report has been updated.");
					
				}
			}
	
	}

	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
