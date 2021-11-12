import java.io.*;

public class FileHandler extends PersistenceHandler {
	public FileHandler() {
	}

	@Override
	public void Save(Account account) {
		try {
			FileWriter fw = new FileWriter("Accounts.txt",true);
			fw.write(account.getAccNo() + " " + account.Balance + " " + account.getAccountHolder().getName() + " " + account.getAccountHolder().getCNIC() + " " + account.getAccountHolder().getAddress()+ " " +account.getAccountHolder().getPhoneNumber() + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
