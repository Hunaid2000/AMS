import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankTest {
	static Bank bank;
	
	@BeforeClass
	public static void initializeBank(){
		bank = new Bank(new Customer("i192043", "Hunaid Owner", "Rawalpindi", "NA"),1);
		SavingsAccount savingsAccount = new SavingsAccount(100, 2, new Customer("1", "Customer1", "Rawalpindi", "NA"));
		bank.savingsAccountsList.add(savingsAccount);
		CheckingAccount checkingAccount = new CheckingAccount(100, 10, 2, 0.15, new Customer("2", "Customer2", "Rawalpindi", "NA"));
		bank.checkingAccountsList.add(checkingAccount);
	}
	
	// Test to create Account
	@Test
	public void testcreateAccount() {
		SavingsAccount savingsAccount = new SavingsAccount(100, 2, new Customer("5", "Customer5", "Rawalpindi", "NA"));
		bank.savingsAccountsList.add(savingsAccount);
		assertEquals(5, bank.savingsAccountsList.elementAt(1).getAccNo(), 0);
		CheckingAccount checkingAccount = new CheckingAccount(100, 10, 2, 0.15, new Customer("6", "Customer6", "Rawalpindi", "NA"));
		bank.checkingAccountsList.add(checkingAccount);
		assertEquals(6, bank.checkingAccountsList.elementAt(1).getAccNo(), 0);
	}
	
	// Test to close Account
	@Test
	public void testcloseAccount() {
		SavingsAccount savingsAccount = new SavingsAccount(100, 2, new Customer("3", "Customer3", "Rawalpindi", "NA"));
		bank.savingsAccountsList.add(savingsAccount);
		CheckingAccount checkingAccount = new CheckingAccount(100, 10, 2, 0.15, new Customer("4", "Customer4", "Rawalpindi", "NA"));
		bank.checkingAccountsList.add(checkingAccount);
		bank.closeAccount(3);
		bank.closeAccount(4);
		assertEquals(1, bank.savingsAccountsList.size(), 0);
		assertEquals(1, bank.checkingAccountsList.size(), 0);
	}
	
	// Test to check login
	@Test
	public void testLogin() {
		assertFalse(bank.Login(0));
		assertTrue(bank.Login(1));
	}
	
	// Test to check logout
	@Test
	public void testLogout() {
		bank.Login(1);
		assertNotNull(bank.loginedAccount);
		bank.Logout();
		assertNull(bank.loginedAccount);
	}
	
	// Test to set InterestRate
	@Test
	public void testsetInterestRate() {
		bank.setInterestRate(3);
		for (int i = 0; i < bank.savingsAccountsList.size(); i++) {			
			assertEquals(3, bank.savingsAccountsList.elementAt(i).getInterestRate(), 0);					
		}
	}
	
	// Test to check transferAmount
	@Test
	public void testtransferAmount() {
		bank.transferAmount(2, 200);	// Not logined case
		bank.Login(1);
		bank.transferAmount(2, 200);	// transfer amount greater than balance case
		assertEquals(100, bank.savingsAccountsList.elementAt(0).Balance, 0);
		assertEquals(100, bank.checkingAccountsList.elementAt(0).Balance, 0);
		bank.transferAmount(2, 50);		// simple case
		assertEquals(50, bank.savingsAccountsList.elementAt(0).Balance, 0);
		assertEquals(150, bank.checkingAccountsList.elementAt(0).Balance, 0);
	}
	
	@AfterClass
	public static void destroy() {
		bank = null;
	}


}
