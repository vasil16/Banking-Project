import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;


public class VerifyTools {
	
    public boolean InvalidPin(String input) 
    {
        if (!input.matches("\\d{4}")) 
        {
            // Handle the case where the PIN is not numeric or not of length 4
            JOptionPane.showMessageDialog(null, "Invalid PIN format. PIN must be numeric and have a length of 4.");
            return true;
        }

        return false;
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

}
