import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Vector;

public class Account{
	static int count = 1;
	private int AccNo;
	protected double Balance;
	private Customer AccountHolder;
	protected Vector<Transaction> transactions;
	protected Vector<Transaction> deductions;
	protected LocalDate DateCreated;
	protected int LastYear;
	
	public Account(double bal, Customer Accholder) {
		AccNo = count++;
		Balance = bal;
		transactions = new Vector<Transaction>();
		deductions = new Vector<Transaction>();
		AccountHolder = Accholder;
		DateCreated = LocalDate.now();
		LastYear = LocalDate.now().getYear();
	}
	
	public int getAccNo() {
		return AccNo;
	}
	
	public void checkBalance() {
		System.out.println("Account Holder Name: " + AccountHolder.Name);
		System.out.println("Account Balance: " + Balance);
	}
	
	public double calculateInterest() {
		return 0;
	}
	
	public double calculateZakat() {		
		return 0;
	}
	
	public double calculateTax() {		
		return 0;
	}
	
	public void makeDeposit(double amount) {
		Balance += amount;
		System.out.println("Transaction Successful.");
		Transaction transaction = new Transaction("Deposit", amount, Balance);
		transactions.add(transaction);
	}
	
	public void makeWithdrawal(double amount) {
		Balance -= amount;
	}
	
	public void transferAmount(Account account, double amount) {
		Balance -= amount;
		account.Balance += amount;
	}
	
	public Customer getAccountHolder() {
		return AccountHolder;
	}
	
	public void printAccountDetails() {
		AccountHolder.printCustomer();
		System.out.println("Account Number: " + AccNo);
		System.out.println("Account Balance: " + Balance);
		System.out.println("Account Opened: " + DateCreated);
		
	}
	
	public void printStatement() {
		AccountHolder.printCustomer();
		System.out.println("Account Number: " + AccNo);
		System.out.println("****** Transaction History ******");		
		for (int i = 0; i < transactions.size(); i++) {
			transactions.elementAt(i).printTransaction();
		}
	}
	
	public void displayAllDeductions() {
		System.out.println("****** Deduction History ******");
		for (int i = 0; i < deductions.size(); i++) {
			deductions.elementAt(i).printTransaction();
		}
	}
	
}


class SavingsAccount extends Account{
	private double InterestRate;
	
	public SavingsAccount(double bal, double ir, Customer Accholder) {
		super(bal, Accholder);
		InterestRate = ir;
	}
	
	public void makeWithdrawal(double amount) {
		if(amount <= 0)
			System.out.println("Incorrect Amount");
		else if (amount <= Balance) {
			Balance -= amount;
			Transaction transaction = new Transaction("WithDraw", amount, Balance);
			transactions.add(transaction);
			System.out.println("Transaction Successful.");
		}
		else
			System.out.println("Transaction Unsuccessful. Amount greator than Balance");	
	}
	
	public double getInterestRate() {
		return InterestRate;
	}
	
	public void setInterestRate(double ir) {
		InterestRate = ir;
	}
	
	public double calculateInterest() {
		long days = DateCreated.until(LocalDate.now(), ChronoUnit.DAYS);		
		return Balance*InterestRate*(days/365.0);
	}
	
	public double calculateZakat() {
		if (LastYear != LocalDate.now().getYear() && Balance >= 20000) {
			LastYear = LocalDate.now().getYear();
			return (Balance*2.5)/100;
		}		
		return 0;
	}
	
	public void makeDeduction() {
		double amount = calculateZakat();
		if(amount != 0) {
			Transaction deduction = new Transaction("Zakat Deduction", amount, Balance);
			deductions.add(deduction);
		}		
	}
	
}


class CheckingAccount extends Account{
	private double TransactionFee;
	private double TaxRate;
	private int FreeTransactionCount;
	private java.time.Month LastTransactionMonth;	
	
	public CheckingAccount(double bal, double transfee, int freetranscount, double trate, Customer Accholder) {
		super(bal, Accholder);
		TransactionFee = transfee;
		FreeTransactionCount = freetranscount;	
		TaxRate = trate;
		
	}
	
	public void cutTransactionFee(Transaction transaction) {
		if(LastTransactionMonth == null) {
			LastTransactionMonth = transaction.DateTime.getMonth();
			FreeTransactionCount--;
		}
		
		else if(LocalDate.now().getMonth() == LastTransactionMonth) {
			if(FreeTransactionCount != 0) {
				FreeTransactionCount--;
			}
			else {
				Balance -= TransactionFee;
			}
			LastTransactionMonth = transaction.DateTime.getMonth();
		}
		
		else if (LocalDate.now().getMonth() != LastTransactionMonth) {
			FreeTransactionCount = 2;
			FreeTransactionCount--;
			LastTransactionMonth = transaction.DateTime.getMonth();
		}		
	}
	
	public void makeWithdrawal(double amount) {
		if(amount <= 0)
			System.out.println("Incorrect Amount");
		else if (amount <= 5000) {
			Balance -= amount;
			Transaction transaction = new Transaction("WithDraw", amount, Balance);
			transactions.add(transaction);
			cutTransactionFee(transaction);			
			System.out.println("Transaction Successful.");
		}
		else 
			System.out.println("Transaction Unsuccessful. Amount greator than 5000");
	}
	
	public void makeDeposit(double amount) {
		Balance += amount;
		Transaction transaction = new Transaction("Deposit", amount, Balance);
		transactions.add(transaction);
		cutTransactionFee(transaction);
		System.out.println("Transaction Successful.");
	}
	
	public double calculateTax() {
		if (LastYear != LocalDate.now().getYear()) {
			LastYear = LocalDate.now().getYear();
			return Balance*TaxRate;
		}		
		return 0;
	}
	
	public void makeDeduction() {
		double amount = calculateTax();
		if(amount != 0) {
			Transaction deduction = new Transaction("Tax Deduction", amount, Balance);
			deductions.add(deduction);
		}		
	}
	
}