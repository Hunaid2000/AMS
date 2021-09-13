import java.time.LocalDateTime;

public class Transaction{
	String Type;
	double Amount, remBalance;
	LocalDateTime DateTime;
	
	public Transaction(String typ, double am, double rembal) {
		Type = typ;
		Amount = am;
		remBalance = rembal;
		DateTime = LocalDateTime.now();	
	}
	
	public void printTransaction() {
		System.out.println(Type + ": Rs." + Amount + " on " + DateTime + " RemBalance." + remBalance);
	}
	
}