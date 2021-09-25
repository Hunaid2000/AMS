import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction{
	private String Type;
	private double Amount, remBalance;
	private LocalDateTime DateTime;	
	
	public Transaction(String typ, double am, double rembal) {
		Type = typ;
		Amount = am;
		remBalance = rembal;
		DateTime = LocalDateTime.now();	
	}
	
	public LocalDateTime getDateTime() {
		return DateTime;
	}
	
	public void printTransaction() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(Type + ": Rs." + Amount + " on " + DateTime.format(formatter) + " RemBalance." + remBalance);
	}
	
}