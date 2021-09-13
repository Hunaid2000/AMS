import java.util.Scanner;

public class AMS{
	public static void main(String[] args) {
		Bank bank = new Bank(new Customer("i192043", "Hunaid", "Rawalpindi", "NA"));
		while (true) {
			Scanner in = new Scanner(System.in);     
			System.out.println("Press a/A for Admin.");
			System.out.println("Press c/C for Customer.");
			System.out.println("Press e/E to Exit.");
			char type = in.next().charAt(0);		
			if(type == 'a' || type == 'A') {
				System.out.println("Press 1 to create Account.");
				System.out.println("Press 2 to close Account.");
				System.out.println("Press 3 to set Interest Rate for all Savings Account. (Currently set to: " + bank.interestRateforSavings + ")");
				System.out.println("Press 4 to display all Accounts Details and Bank Owner Details.");
				System.out.println("Press 5 to display all Accounts Details and thier Deductions.");
				int choice = in.nextInt();
				if (choice == 1) {
					bank.createAccount();
				}
				else if (choice == 2) {
					System.out.print("Enter Account Number: ");
					int accno = in.nextInt();
					bank.closeAccount(accno);
				}
				else if (choice == 3) {
					System.out.print("Enter Interest Rate: ");
					double ir = in.nextDouble();
					bank.setInterestRate(ir);
				}
				else if (choice == 4) {
					System.out.println("Bank Owner Details");
					bank.bankOnwer.printCustomer();
					System.out.println();
					for (int i = 0; i < bank.savingsAccountsList.size(); i++) {
						bank.savingsAccountsList.elementAt(i).printAccountDetails();
						System.out.println();
					}
					
					for (int i = 0; i < bank.checkingAccountsList.size(); i++) {
						bank.checkingAccountsList.elementAt(i).printAccountDetails();				
						System.out.println();
					}
				}
				else if (choice == 5) {
					for (int i = 0; i < bank.savingsAccountsList.size(); i++) {
						bank.savingsAccountsList.elementAt(i).printAccountDetails();
						bank.savingsAccountsList.elementAt(i).displayAllDeductions();
						System.out.println();
					}
					
					for (int i = 0; i < bank.checkingAccountsList.size(); i++) {
						bank.checkingAccountsList.elementAt(i).printAccountDetails();				
						bank.checkingAccountsList.elementAt(i).displayAllDeductions();
						System.out.println();
					}
				}
			}		
			else if(type == 'c' || type == 'C') {
				System.out.print("Enter your Account Number: ");
				int loginaccno = in.nextInt();
				if(bank.Login(loginaccno)) {
					System.out.println("Press 1 to Deposit.");
					System.out.println("Press 2 to WithDraw.");
					System.out.println("Press 3 transfer Amount.");
					System.out.println("Press 4 to check Balance.");
					System.out.println("Press 5 to get your Account Statment.");
					System.out.println("Press 6 to display your Deduction History.");
					System.out.println("Press 7 to calculate Zakat on Savings Account.");
					System.out.println("Press 8 to calculate Interest on Savings Account.");
					System.out.println("Press 9 to calculate Tax on Checking Account.");
					int choice = in.nextInt();
					if (choice == 1) {
						System.out.print("Enter Amount to Deposit: ");
						Double amount = in.nextDouble();
						bank.loginedAccount.makeDeposit(amount);
					}
					else if (choice == 2) {
						System.out.print("Enter Amount to WithDraw: ");
						Double amount = in.nextDouble();
						bank.loginedAccount.makeWithdrawal(amount);
					}
					else if (choice == 3) {
						System.out.print("Enter Reciever's Account Number: ");
						int recieveraccno = in.nextInt();
						System.out.print("Enter Amount to Transfer: ");
						Double amount = in.nextDouble();
						bank.transferAmount(recieveraccno, amount);
					}
					else if (choice == 4) {
						bank.loginedAccount.checkBalance();
					}
					else if (choice == 5) {
						bank.loginedAccount.printStatement();
					}
					else if (choice == 6) {
						bank.loginedAccount.displayAllDeductions();
					}
					else if (choice == 7) {
						if (bank.isSavings(bank.loginedAccount)) {
							System.out.println("Calculated Zakat: " + bank.loginedAccount.calculateZakat());
						}
						else	
							System.out.println("This is not Savings Account.");
					}
					else if (choice == 8) {
						if (bank.isSavings(bank.loginedAccount)) {
							System.out.println("Calculated Interest: " + bank.loginedAccount.calculateInterest());
						}
						else	
							System.out.println("This is not Savings Account.");
					}
					else if (choice == 9) {
						if (bank.isChecking(bank.loginedAccount)) {
							System.out.println("Calculated Tax: " + bank.loginedAccount.calculateTax());
						}
						else	
							System.out.println("This is not Checking Account.");
					}
				}
				else {
					System.out.println();
					continue;
				}
				
			}
			else if(type == 'e' || type == 'E') {
				in.close();
				return;
			}
			else {
				System.out.println("Wrong Key!");
				System.out.println();
				continue;
			}
			System.out.println();
		}	
	}
}