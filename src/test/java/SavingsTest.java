import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SavingsTest {
	SavingsAccount savingsAccount;
	private final PrintStream Out = System.out;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	@Before
	public void initializeSavingsAccount(){
		savingsAccount = new SavingsAccount(100, 2, new Customer("1", "Hunaid", "Rawalpindi", "NA"));
		System.setOut(new PrintStream(outputStream));
	}
	
	// Test to check simple deposit
	@Test
	public void testMakeDeposit1() {
		savingsAccount.makeDeposit(100);
		assertEquals(200, savingsAccount.Balance, 0);
	}
	
	// Test to check negative deposit
	@Test
	public void testMakeDeposit2() {
		savingsAccount.makeDeposit(-100);
		assertEquals(100, savingsAccount.Balance, 0);
	}
	
	// Test to check simple withdraw
	@Test
	public void testMakeWithDrawal1() {
		savingsAccount.makeWithdrawal(100);
		assertEquals(0, savingsAccount.Balance, 0);
	}
	
	// Test to check negative withdraw
	@Test
	public void testMakeWithDrawal2() {
		savingsAccount.makeWithdrawal(-100);
		assertEquals(100, savingsAccount.Balance, 0);
	}
	
	// Test to check if withdraw is possible greater than balance
	@Test
	public void testMakeWithDrawal3() {
		savingsAccount.makeWithdrawal(200);
		assertEquals(100, savingsAccount.Balance, 0);
	}
	
	// Test to calculate Zakat of same year
	@Test
	public void testcalculateZakat1() {		
		savingsAccount.Balance = 25000;
		assertEquals(0, savingsAccount.calculateZakat(), 0);
	}
	
	// Test to calculate Zakat of next year
	@Test
	public void testcalculateZakat2() {	
		savingsAccount.setLastYear(2022);
		savingsAccount.Balance = 25000;
		assertEquals(625, savingsAccount.calculateZakat(), 0);
	}
	
	// Test to calculate Interest Rate if Account was Created on 1st Jan, 2021.
	@Test
	public void testcalculateInterest() {
		assertEquals(0, savingsAccount.calculateInterest(), 0);
		savingsAccount.setDateCreated(LocalDate.of(2021, 01, 01));
		assertEquals(133.3, savingsAccount.calculateInterest(), 0.1);
	}
	
	// Test to check balance
	@Test
	public void testcheckBalance() {	
		savingsAccount.checkBalance();
		assertEquals("Account Holder Name: Hunaid\r\nAccount Balance: 100.0", outputStream.toString().trim());
	}
	
	// Test to print account statement
	@Test
	public void testprintStatement() {
		savingsAccount.makeDeposit(20);
		savingsAccount.makeWithdrawal(20);
		savingsAccount.printStatement();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		assertEquals("Transaction Successful.\r\nTransaction Successful.\r\nName: Hunaid\r\nAddress: Rawalpindi\r\nPhone Number: NA\r\nAccount Number: 29\r\n****** Transaction History ******\r\nDeposit: Rs.20.0 on " + LocalDateTime.now().format(formatter) + " RemBalance.120.0\r\nWithDraw: Rs.20.0 on " +  LocalDateTime.now().format(formatter) + " RemBalance.100.0", outputStream.toString().trim());
	}
	
	// Test to print account details
	@Test
	public void testprintAccountDetails() {
		savingsAccount.printAccountDetails();		
		assertEquals("Name: Hunaid\r\nAddress: Rawalpindi\r\nPhone Number: NA\r\nAccount Number: 28\r\nAccount Balance: 100.0\r\nAccount Opened: " + LocalDate.now(), outputStream.toString().trim());
	}
	
	// Test to display all Deductions
	@Test
	public void testdisplayAllDeductions() {
		savingsAccount.setLastYear(2022);
		savingsAccount.Balance = 25000;
		savingsAccount.makeDeduction();
		savingsAccount.displayAllDeductions();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		assertEquals("****** Deduction History ******\r\nZakat Deduction: Rs.625.0 on " + LocalDateTime.now().format(formatter) + " RemBalance.24375.0", outputStream.toString().trim());
	}
	
	@After
	public void destroy() {
		System.setOut(Out);
		savingsAccount = null;
	}


}
