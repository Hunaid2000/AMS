import java.util.Scanner;
import java.util.Vector;

public class Bank {
	Customer bankOnwer;
	Vector<SavingsAccount> savingsAccountsList;
	Vector<CheckingAccount> checkingAccountsList;
	Vector<Customer> customersList;
	Account loginedAccount;
	double interestRateforSavings;
	
	public Bank(Customer owner) {
		bankOnwer = owner;
		savingsAccountsList = new Vector<SavingsAccount>();
		checkingAccountsList = new Vector<CheckingAccount>();
		customersList = new Vector<Customer>();
		interestRateforSavings = 0;
	}
	
	public void createAccount() {
		Scanner input = new Scanner(System.in);     
		System.out.print("Enter Name: ");
		String name = input.nextLine();
		System.out.print("Enter CNIC: ");
		String cnic = input.nextLine();
		System.out.print("Enter PhoneNumber: ");
		String phno = input.nextLine();
		System.out.print("Enter Address: ");
		String address = input.nextLine();
		System.out.print("Enter Initial Balance: ");
		double bal = input.nextDouble();
		boolean haveSavingsAccount = false;
		boolean haveCheckingAccount = false;
		boolean OldCustomer = false;
		Customer customer = null;
		for (int i = 0; i < customersList.size(); i++) {
			if(customersList.elementAt(i).CNIC == cnic) {
				OldCustomer = true;
				customer = customersList.elementAt(i); 
			}
		}
		if(!OldCustomer) {
			customer = new Customer(cnic, name, address, phno);
			customersList.add(customer);
		}
		System.out.println("Choose Account Type:");
		System.out.println("Enter S/s for Savings Account");
		System.out.println("Enter C/c for Checking Account");
		char type = input.next().charAt(0);
		if(type == 's' || type == 'S') {
			for (int i = 0; i < savingsAccountsList.size(); i++) {
				if(cnic.equals(savingsAccountsList.elementAt(i).getAccountHolder().CNIC)) {
					System.out.println("You already have 1 Savings account.");
					haveSavingsAccount = true;
				}				
			}
			if(!haveSavingsAccount) {
				SavingsAccount account = new SavingsAccount(bal, interestRateforSavings, customer);
				savingsAccountsList.add(account);
				System.out.println("Savings Account Created.");
			}			
		}
		
		else if(type == 'c' || type == 'C') {
			for (int i = 0; i < checkingAccountsList.size(); i++) {
				if(cnic.equals(checkingAccountsList.elementAt(i).getAccountHolder().CNIC)) {
					System.out.println("You already have 1 Checking account.");
					haveCheckingAccount = true;
				}				
			}
			if(!haveCheckingAccount) {
				CheckingAccount account = new CheckingAccount(bal, 10, 2, 0.15, customer);
				checkingAccountsList.add(account);
				System.out.println("Checking Account Created.");
			}						
		}
	}
	
	public void closeAccount(int accno) {
		if(savingsAccountsList.contains(getAccount(accno))) {
			savingsAccountsList.remove(getAccount(accno));
			System.out.println("Savings Account Closed Successfully.");
		}			
		else if (checkingAccountsList.contains(getAccount(accno))) {
			checkingAccountsList.remove(getAccount(accno));
			System.out.println("Checking Account Closed Successfully.");
		}
		else
			System.out.println("Account not Found.");
	}
	
	public Account getAccount(int accno) {
		for (int i = 0; i < savingsAccountsList.size(); i++) {
			if(accno == savingsAccountsList.elementAt(i).getAccNo()) {
				return savingsAccountsList.elementAt(i);
			}				
		}
		
		for (int i = 0; i < checkingAccountsList.size(); i++) {
			if(accno == checkingAccountsList.elementAt(i).getAccNo()) {
				return checkingAccountsList.elementAt(i);
			}				
		}
		return null;
	}
	
	public boolean isSavings(Account account) {
		for (int i = 0; i < savingsAccountsList.size(); i++) {
			if(account.getAccNo() == savingsAccountsList.elementAt(i).getAccNo()) {
				return true;
			}				
		}
		return false;
	}
	
	public boolean isChecking(Account account) {
		for (int i = 0; i < checkingAccountsList.size(); i++) {
			if(account.getAccNo() == checkingAccountsList.elementAt(i).getAccNo()) {
				return true;
			}				
		}
		return false;
	}
	
	public void setInterestRate(double ir) {
		interestRateforSavings = ir;	// For new accounts
		for (int i = 0; i < savingsAccountsList.size(); i++) {			
				savingsAccountsList.elementAt(i).setInterestRate(ir); 	// For old accounts			
		}
	}
	
	public boolean Login(int accno) {
		loginedAccount = getAccount(accno);
		if(loginedAccount != null) {
			System.out.println("Login Successful");
			return true;
		}
		else {
			System.out.println("Login Unsuccessful");
			return false;
		}			
	}
	
	public void Logout() {
		if(loginedAccount != null) {
			loginedAccount = null;
			System.out.println("You are now Logout.");
		}			
		else
			System.out.println("Already Logout.");
	}
	
	public void transferAmount(int accno, double amount) {
		if(loginedAccount != null) {
			if(loginedAccount.Balance >= amount)
				loginedAccount.transferAmount(getAccount(accno), amount);
			else
				System.out.println("Insufficient Balance");
		}
		else
			System.out.println("You are not Logined");
	}
}
