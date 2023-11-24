import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class Classf extends JFrame {
	
	String cusid="1121";
	String cuspin="0000";
	
	
	public Classf() {
	JFrame log=new JFrame("Welcome to XZ Bank");
	log.setVisible(true);
	
	
	log.setSize(500,400);
	getContentPane().setLayout(new BorderLayout());
	log.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
	
	JLabel logger=new JLabel("Enter Credentials");
	logger.setBounds(131, 72, 186, 32);
	logger.setAlignmentX(CENTER_ALIGNMENT);
	logger.setAlignmentY(TOP_ALIGNMENT);
	logger.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 24));
	
	JLabel id = new JLabel("Enter ID");
	id.setBounds(90, 130, 46, 16);
	id.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
	JLabel pin=new JLabel("Enter PIN");
	pin.setBounds(90, 172, 53, 16);
	pin.setFont(new Font("Tahoma", Font.PLAIN, 13));
	
	JTextField getid=new JTextField();
	getid.setBounds(198, 129, 126, 20);
	
	JPasswordField getpin=new JPasswordField();
	getpin.setBounds(198, 171, 126, 20);
	
	JButton login = new JButton("Login");
	login.setBounds(84, 228, 115, 32);
	login.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 
			String idguess = getid.getText();
			String pinguess = getpin.getText();
			if(idguess.equals(cusid)&&pinguess.equals(cuspin)) {
				
				dispose();
				new Home();
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid credentials");
			}
				
			
			
		}
	});
	
	JButton reset = new JButton("Reset");
	reset.setBounds(226, 228, 126, 32);
	reset.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			getid.setText(null);
			getpin.setText(null);
			
		}
	});
	
	JButton exit = new JButton("Exit");
	exit.setBounds(415, 321, 59, 29);
	exit.setFont(new Font("Times New Roman", Font.BOLD, 16));
	
	log.getContentPane().add(logger);
	log.getContentPane().add(id);
	log.getContentPane().add(getid);
	log.getContentPane().add(pin);
	log.getContentPane().add(getpin);
	log.getContentPane().add(login);
	log.getContentPane().add(reset);
	log.getContentPane().add(exit);
	exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}	
	});
	
	
	log.getContentPane().setLayout(null);
	
	
	}

	public static void main(String[] args) {
		new Login();
	}
}
