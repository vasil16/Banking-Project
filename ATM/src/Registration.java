import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.sql.Date;

public class Registration extends JFrame {
    private JTextField nameInput;
    private JPasswordField pinInput;
    private JButton registerButton;
    private JTextField regTxt;
    private JTextField mobInput;
    private JPasswordField pinConfirmInput;
    private Connection connection;
    private String name;
    private String mobile;
    private String dateCreated;
    private String cuusId;
    private long balance;
    private String hashedPin;
    public String url;
    
    String enteredPin;
    String pinHashed;

    public Registration() {
    	
    	
        url = "jdbc:sqlite:/Users/secondary/Documents/GitHub/Banking-Project/XZUserAccs.db";
        try {
    	connection = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	getContentPane().setFont(new Font("Noto Nastaliq Urdu", Font.BOLD, 21));
        // Set up the JFrame
        setTitle("User Registration");
        setSize(435, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        nameInput = new JTextField(20);
        nameInput.setBounds(102, 60, 225, 20);
        pinInput = new JPasswordField(4);
        pinInput.setBounds(102, 124, 105, 20);
        registerButton = new JButton("Register");
        registerButton.setBounds(316, 216, 95, 36);

        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        // Add components to the content pane
        JLabel labelName = new JLabel("Name:");
        labelName.setBounds(21, 59, 57, 20);
        labelName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        getContentPane().add(labelName);
        getContentPane().add(nameInput);
        JLabel labelPin = new JLabel("Choose Pin:");
        labelPin.setBounds(21, 123, 82, 29);
        getContentPane().add(labelPin);
        getContentPane().add(pinInput);
        getContentPane().add(registerButton);
        
        regTxt = new JTextField();
        regTxt.setHorizontalAlignment(SwingConstants.CENTER);
        regTxt.setText("User Registration");
        regTxt.setBounds(130, 6, 184, 41);
        getContentPane().add(regTxt);
        regTxt.setColumns(10);
        
        JLabel labelMob = new JLabel("Mobile");
        labelMob.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        labelMob.setBounds(21, 91, 57, 29);
        getContentPane().add(labelMob);
        
        mobInput = new JTextField(10);
        mobInput.setBounds(102, 92, 148, 20);
        getContentPane().add(mobInput);
        
        JLabel labelConfimPin = new JLabel("Confirm Pin");
        labelConfimPin.setBounds(21, 155, 82, 29);
        getContentPane().add(labelConfimPin);
        
        pinConfirmInput = new JPasswordField(4);
        pinConfirmInput.setBounds(102, 156, 105, 20);
        getContentPane().add(pinConfirmInput);

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	char[] enteredPinChars = pinInput.getPassword();
                enteredPin = new String(enteredPinChars);
            	VerifyData();
                
            }
        });
        setVisible(true);
    }
    

    private static boolean IsDuplicate(Connection connection, String mobile) {
        // Check if the mobile number already exists in the Accounts table
        String checkSQL = "SELECT COUNT(*) FROM Accounts WHERE phone_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(checkSQL)) {
            statement.setString(1, mobile);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "nuumber already registered");
        return false;
    }
    
    public boolean InvalidPin() {
        String pin = new String(pinInput.getPassword());
        String confirmPin = new String(pinConfirmInput.getPassword());

        if (!pin.matches("\\d{4}")) {
            // Handle the case where the PIN is not numeric or not of length 4
            JOptionPane.showMessageDialog(null, "Invalid PIN format. PIN must be numeric and have a length of 4.");
            return true;
        } else if (!pin.equals(confirmPin)) {
            JOptionPane.showMessageDialog(null, "Pins don't match!!");
            return true;
        }

        return false;
    }

    
    private void VerifyData()
    {
    	if(!InvalidPin() && ! IsDuplicate(connection, mobile)) 
    	{
    		try 
    		{
    			hashedPin = hashPin(enteredPin);
    		} 
    		catch (NoSuchAlgorithmException e) 
    		{
    			e.printStackTrace();
    			return;
    		}
    		RegisterUser();
    	
    	}
    }
    
    private static String hashPin(String pin) throws NoSuchAlgorithmException {
    	System.out.println(pin + "  pin");
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pin.getBytes());
        StringBuilder hashString = new StringBuilder();

        for (byte hashByte : hashBytes) {
            hashString.append(Integer.toHexString((hashByte & 0xFF) | 0x100), 1, 3);
        }

        System.out.println(hashString.toString() + "  h");
        return hashString.toString();
    }
    
    private void RegisterUser() {
        name = nameInput.getText();
        mobile = mobInput.getText();
        int intMob = Integer.parseInt(mobile);

        try {
            Connection connection = DriverManager.getConnection(url);

            String insertQuery = "INSERT INTO Accounts (name, balance, phone_number, hashed_pin, creation_date) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, 500);
                preparedStatement.setInt(3, intMob);
                preparedStatement.setString(4, hashedPin);
                preparedStatement.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                	
                	int cusId = retrieveCustomerId(connection, mobile);
                			JOptionPane.showMessageDialog(this, "Registration successful!\nYour Customer ID is: " + cusId);
                		}
                
                else {
                    JOptionPane.showMessageDialog(this, "Registration failed! No rows affected.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } 
        dispose();
        new Login();
    }
    
    
    private int retrieveCustomerId(Connection connection, String mobile) throws SQLException {
        String query = "SELECT customer_id FROM Accounts WHERE phone_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mobile);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("customer_id");
                } else {
                    throw new SQLException("Unable to retrieve customer ID.");
                }
            }
        }
    }
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Registration registration = new Registration();
            registration.setVisible(true);
        });
    }

}
