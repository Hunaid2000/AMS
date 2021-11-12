import java.sql.*;

public class MySQLHandler extends PersistenceHandler {
	public MySQLHandler() {}

	@Override
	public void Save(Account account) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Drivers Loaded");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys","root","12345");
			System.out.println("Connection Established");
			
			Statement st1 = con.createStatement();
			st1.execute("use AMS");
			
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
		}
	}

}
