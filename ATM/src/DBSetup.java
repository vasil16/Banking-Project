
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {
	
	public static final String url = "jdbc:sqlite:/Users/secondary/Documents/GitHub/Banking-Project/XZUserAccs.db";

    public static void main(String[] args) {
        // Load the SQLite JDBC driver explicitly
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Define the URL for the SQLite database. Replace with the actual path to your desired database file.
        

        try (Connection connection = DriverManager.getConnection(url)) {
            // Establish a connection to the SQLite database
            System.out.println("Connected to SQLite database.");

            // Create the necessary tables
            createAccountsTable(connection);
//          createTransactionsTable(connection);

            // Print a message indicating that the database setup is complete
            System.out.println("Database setup complete.");

        } catch (SQLException e) {
            // Handle any SQL-related exceptions
            e.printStackTrace();
        }
    }


    private static void createAccountsTable(Connection connection) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Accounts (" +
                "customer_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "name TEXT NOT NULL, " +
                "balance REAL NOT NULL," +
                "phone_number INTEGER UNIQUE," +
                "hashed_pin VARCHAR(64) NOT NULL," + // Fixed length of 64 characters for SHA-256
                "creation_date DATE NOT NULL)";

        executeUpdate(connection, createTableSQL, "Table 'Accounts' created successfully.");
    }



    private static void createTransactionsTable(Connection connection) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account_id INTEGER REFERENCES Accounts(customer_id)," +
                "transaction_type TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "transaction_date DATE NOT NULL)";

        executeUpdate(connection, createTableSQL, "Table 'Transactions' created successfully.");
    }

    private static void executeUpdate(Connection connection, String sql, String successMessage) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println(successMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
