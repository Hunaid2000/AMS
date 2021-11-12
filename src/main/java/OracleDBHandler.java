import java.sql.*;

public class OracleDBHandler extends PersistenceHandler {
	public OracleDBHandler() {}

	@Override
	public void Save(Account account) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Drivers Loaded");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.27:1521:xe","system","12345");
			System.out.println("Connection Established");
			
			String sql = "INSERT INTO Accounts VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, account.getAccNo());
			statement.setInt(2, (int)account.Balance);
			statement.setString(3, account.getAccountHolder().getName());
			statement.setString(4, account.getAccountHolder().getCNIC());
			statement.setString(5, account.getAccountHolder().getAddress());
			statement.setString(6, account.getAccountHolder().getPhoneNumber());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Data Saved");
			}
			else {
				System.out.println("Data NOT Saved");
			}
			con.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not Loaded");
		}
		catch (SQLException e) {
			System.out.println("Connection not Established");
			e.printStackTrace();
		}
	}

}
