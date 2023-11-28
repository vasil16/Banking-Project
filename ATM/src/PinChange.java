import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;


public class PinChange extends JFrame
{
	
    String enteredPin;
    String pinHashed;
    int cusId;

    Home home;

    String url;
    Connection connection;

    private JPasswordField currPin;
    private JPasswordField retypePin;
    private JPasswordField newPin;

    JLabel currentPinLabel;
    JLabel retypePinLabel;
    JLabel newPinLabel;
    JButton changePinBtn;
    JButton confirmBtn;
    
	
	public PinChange(int customerId, Home home) 
    {
		
		super("PinChange");
		this.home = home;
		setSize(572,418);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

        cusId = customerId;
		
		currentPinLabel = new JLabel("Current Pin");
		currentPinLabel.setBounds(28, 53, 103, 26);
		getContentPane().add(currentPinLabel);
		
		retypePinLabel = new JLabel("Re-Type Pin");
		retypePinLabel.setBounds(28, 92, 103, 26);
		getContentPane().add(retypePinLabel);
		
		newPinLabel = new JLabel("New Pin:");
		newPinLabel.setBounds(134, 106, 87, 26);
//		getContentPane().add(newPinLabel);
		
		currPin = new JPasswordField();
		currPin.setBounds(171, 53, 82, 26);
		getContentPane().add(currPin);
		
		retypePin = new JPasswordField();
		retypePin.setBounds(171, 92, 82, 26);
		getContentPane().add(retypePin);
		
		newPin = new JPasswordField();
		newPin.setBounds(226, 106, 82, 26);
//		getContentPane().add(newPin);
		
		changePinBtn = new JButton("Change Pin");
		changePinBtn.setBounds(226, 144, 117, 29);
//		getContentPane().add(changePinBtn);
		
		confirmBtn = new JButton("Confirm");
		confirmBtn.setBounds(171, 127, 82, 29);

        confirmBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event){
                VerifyEnteredPin();
            };
        });

		getContentPane().add(confirmBtn);
		
		setVisible(true);
		
	}
    
    void PinMatched()
    {
        getContentPane().remove(currentPinLabel);
        getContentPane().remove(retypePinLabel);
        getContentPane().remove(currPin);
        getContentPane().remove(retypePin);
        getContentPane().remove(confirmBtn);
        setVisible(false);
        setVisible(true);

        changePinBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event){
                char[] enteredPinChars = newPin.getPassword();
                enteredPin = new String(enteredPinChars);
                VerifyTools verify = new VerifyTools();
                if(verify.InvalidPin(enteredPin))
                {
                	return;
                }
                try
                {
                    changePin(hashPin(enteredPin));
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                             home.dispose();
                            System.out.println("After calling home.dispose() on the EDT");
                        }
                    });
                    JOptionPane.showMessageDialog(PinChange.this, "pin updated");
                    dispose();
                    new Login();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            };
        });

        getContentPane().add(newPinLabel);
        getContentPane().add(newPin);
        getContentPane().add(changePinBtn);
    }
    
    void VerifyEnteredPin()
    {
        char[] enteredPinChars = currPin.getPassword();
                enteredPin = new String(enteredPinChars);
    	// if(currPin.getPassword() == retypePin.getPassword())
        if(Arrays.equals(currPin.getPassword(), retypePin.getPassword()))
        {
            try
            {
                if(IsCorrectPin(cusId,hashPin(enteredPin)))
                {
                    PinMatched();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Wrong Pin entered");
                    currPin.setText("");
                    retypePin.setText("");
                }
    	    }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Entered Pin's dont match");
            currPin.setText("");
            retypePin.setText("");
        }
    }
    
    
    boolean IsCorrectPin(int customerId, String pinString)
    {
        try (Connection connection = DriverManager.getConnection(DBSetup.url)) 
        {
            String query = "SELECT * FROM Accounts WHERE customer_id = ? AND hashed_pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) 
            {
                preparedStatement.setInt(1, customerId);
                preparedStatement.setString(2, pinString);

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

    private static String hashPin(String pin) throws NoSuchAlgorithmException 
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pin.getBytes());
        StringBuilder hashString = new StringBuilder();

        for (byte hashByte : hashBytes) 
        {
            hashString.append(Integer.toHexString((hashByte & 0xFF) | 0x100), 1, 3);
        }
        return hashString.toString();
    }


    boolean  changePin(String newPin) {
        try (Connection connection = DriverManager.getConnection(DBSetup.url)) {
            String updateQuery = "UPDATE Accounts SET hashed_pin = ? WHERE customer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newPin);
                preparedStatement.setInt(2, cusId);
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    return true;
                } else {
                    
                    return false;
                }
            }
        } catch (SQLException e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
            return false;
        }
    }
}
