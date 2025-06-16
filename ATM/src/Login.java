import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    String cusid = "1122";
    String cuspin = "1111";
    
    String enteredPin;
    String pinHashed;

    JTextField getid = new JTextField();
    JPasswordField getpin = new JPasswordField();
    JLabel id = new JLabel("ID");
    JLabel pin = new JLabel("PIN");
    JLabel l3 = new JLabel("Welcome");
    JButton login = new JButton("Login");
    JButton register = new JButton("Register");
    JButton reset = new JButton("Reset");
    JButton exit = new JButton("Exit");

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(435, 301);
        setLocationRelativeTo(null);

        JPanel log = new JPanel();
        log.setLayout(null);

        l3.setFont(new Font("Bahnschrift", Font.BOLD, 24));
        l3.setBounds(153, 11, 123, 55);
        log.add(l3);

        id.setBounds(68, 97, 52, 23);
        log.add(id);

        pin.setBounds(68, 131, 52, 19);
        log.add(pin);

        getid.setBounds(160, 97, 80, 22);
        log.add(getid);

        getpin.setBounds(160, 128, 80, 22);
        log.add(getpin);

        login.setBounds(160, 174, 80, 22);
        log.add(login);
        
        register.setBounds(160, 200, 80, 22);
        log.add(register);

        reset.setBounds(242, 174, 80, 22);
        log.add(reset);

        exit.setBounds(329, 229, 80, 22);
        log.add(exit);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {

            	char[] enteredPinChars = getpin.getPassword();
                enteredPin = new String(enteredPinChars);

                verifyLogin();

            }
        });
        
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                    dispose(); // Close the current frame (Baker)
                    new Registration();
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getid.setText(null);
                getpin.setText(null);
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        getContentPane().add(log);
        setVisible(true);
        setResizable(false);
    }
    
    private void verifyLogin() {
        String customerIdStr = getid.getText();
        
        try 
		{
			 pinHashed = hashPin(enteredPin);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
			return;
		}

        if (!customerIdStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid Customer ID");
            getid.setText("");

            return;
        }

        int customerId = Integer.parseInt(customerIdStr);

        // Check if the customer ID and PIN match
        if (isValidLogin(customerId, pinHashed)) 
        {
        	
            dispose();
            new Home(customerId);
            // Open the main application window or perform other actions after successful login
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Invalid Customer ID or PIN");
            getid.setText("");
            getpin.setText("");
        }
    }
    
    private boolean isValidLogin(int customerId, String pin) 
    {
        try (Connection connection = DriverManager.getConnection(DBSetup.url)) 
        {
            String query = "SELECT * FROM Accounts WHERE customer_id = ? AND hashed_pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) 
            {
                preparedStatement.setInt(1, customerId);
                preparedStatement.setString(2, pinHashed);

                try (ResultSet resultSet = preparedStatement.executeQuery()) 
                {
                    return resultSet.next(); // If there is a matching record, login is valid
                }
            }
        } 
        catch (SQLException  e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    private static String hashPin(String pin) throws NoSuchAlgorithmException {
    	System.out.println(pin + "  pin");
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pin.getBytes());
        StringBuilder hashString = new StringBuilder();

        for (byte hashByte : hashBytes) 
        {
            hashString.append(Integer.toHexString((hashByte & 0xFF) | 0x100), 1, 3);
        }

        System.out.println(hashString.toString() + "  hashed");
        return hashString.toString();
    }
    

    public static void main(String[] args) {
        new Login();
    }
}
