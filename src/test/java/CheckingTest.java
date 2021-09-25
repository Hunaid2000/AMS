import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckingTest {
	CheckingAccount checkingAccount;
	private final PrintStream Out = System.out;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	@Before
	public void initializeCheckingAccount(){
		checkingAccount = new CheckingAccount(100, 10, 2, 0.15, new Customer("1", "Hunaid", "Rawalpindi", "NA"));
		System.setOut(new PrintStream(outputStream));
	}
	
	// Test to check simple deposit
	@Test
	public void testMakeDeposit1() {
		checkingAccount.makeDeposit(100);
		assertEquals(200, checkingAccount.Balance, 0);
	}
	
	// Test to check negative deposit
	@Test
	public void testMakeDeposit2() {
		checkingAccount.makeDeposit(-100);
		assertEquals(100, checkingAccount.Balance, 0);
	}
	
	// Test to check simple withdraw
	@Test
	public void testMakeWithDrawal1() {
		checkingAccount.makeWithdrawal(200);
		assertEquals(-100, checkingAccount.Balance, 0);
	}
	
	// Test to check negative withdraw
	@Test
	public void testMakeWithDrawal2() {
		checkingAccount.makeWithdrawal(-100);
		assertEquals(100, checkingAccount.Balance, 0);
	}
	
	// Test to check if withdraw is possible greater than 5000
	@Test
	public void testMakeWithDrawal3() {
		checkingAccount.makeWithdrawal(10000);
		assertEquals(100, checkingAccount.Balance, 0);
	}	
	
	// Test to check balance
	@Test
	public void testcheckBalance() {	
		checkingAccount.checkBalance();
		assertEquals("Account Holder Name: Hunaid\r\nAccount Balance: 100.0", outputStream.toString().trim());
	}
	
	// Test to print account statement
	@Test
	public void testprintStatement() {
		checkingAccount.makeDeposit(20);
		checkingAccount.makeWithdrawal(20);
		checkingAccount.printStatement();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		assertEquals("Transaction Successful.\r\nTransaction Successful.\r\nName: Hunaid\r\nAddress: Rawalpindi\r\nPhone Number: NA\r\nAccount Number: 17\r\n****** Transaction History ******\r\nDeposit: Rs.20.0 on " + LocalDateTime.now().format(formatter) + " RemBalance.120.0\r\nWithDraw: Rs.20.0 on " +  LocalDateTime.now().format(formatter) + " RemBalance.100.0", outputStream.toString().trim());
	}
	
	// Test to print account details
	@Test
	public void testprintAccountDetails() {
		checkingAccount.printAccountDetails();		
		assertEquals("Name: Hunaid\r\nAddress: Rawalpindi\r\nPhone Number: NA\r\nAccount Number: 16\r\nAccount Balance: 100.0\r\nAccount Opened: " + LocalDate.now(), outputStream.toString().trim());
	}
	
	// Test to display all Deductions
	@Test
	public void testdisplayAllDeductions() {
		checkingAccount.setLastYear(2022);
		checkingAccount.makeDeduction();
		checkingAccount.displayAllDeductions();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		assertEquals("****** Deduction History ******\r\nTax Deduction: Rs.15.0 on " + LocalDateTime.now().format(formatter) + " RemBalance.85.0", outputStream.toString().trim());
	}
	
	// Test to calculate Tax for next year (15% taxrate as specified in constructor)
	@Test
	public void testcalculateTax() {
		assertEquals(0, checkingAccount.calculateTax(), 0);
		checkingAccount.setLastYear(2022);
		assertEquals(15, checkingAccount.calculateTax(), 0);
	}
	
	// Test to Transaction Fee Deduction
	@Test
	public void testcutTransactionFee() {
		checkingAccount.makeWithdrawal(20);
		assertEquals(80, checkingAccount.Balance, 0);  // 2 tries for free transactions
		checkingAccount.makeWithdrawal(20);
		assertEquals(60, checkingAccount.Balance, 0);
		checkingAccount.makeWithdrawal(20);
		assertEquals(30, checkingAccount.Balance, 0); // 10 rupees cut here after 2 free transactions		
		checkingAccount.setLastTransactionMonth(LocalDate.of(2021, 11, 25).getMonth()); // now the month is changed
		checkingAccount.makeWithdrawal(20);
		assertEquals(10, checkingAccount.Balance, 0);
	}
	
	@After
	public void destroy() {
		System.setOut(Out);
		checkingAccount = null;
	}


}
