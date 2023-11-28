
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Withdraw 
{
	String url;
	Connection connection;
	private int updatedBalance;
	

	public void Withdraw()
	{
		System.out.println("constructor call");
		url = DBSetup.url;
		try 
		{
	    	connection = DriverManager.getConnection(url);
	    }
	    catch (SQLException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	
    public int withdrawAmount(int customerId, int amount) 
    {
            // Perform the withdrawal
            updateBalance(customerId, amount);
            System.out.println("Withdrawal successful!");
            return updatedBalance;
    }
    
    public boolean isBalanceSufficient(int customerId, int amount) 
    {
        // Check if the available balance is sufficient for the withdrawal
    	url = DBSetup.url;
    	try 
		{
	    	connection = DriverManager.getConnection(url);
	    }
	    catch (SQLException e) 
		{
	        e.printStackTrace();
	    }
    	
        String query = "SELECT balance FROM Accounts WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    int balance = resultSet.getInt("balance");
                    return balance >= amount;
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return false;
    }
    
    private int updateBalance(int customerId, int amount) 
    {
        // Update the balance after successful withdrawal

        String updateQuery = "UPDATE Accounts SET balance = balance - ? WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, customerId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the updated balance
                updatedBalance = getBalance(customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedBalance;
    }
    
    private int getBalance(int customerId) 
    {
        // Retrieve the current balance
        int balance = -1;

        String query = "SELECT balance FROM Accounts WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    balance = resultSet.getInt("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }
    
}
