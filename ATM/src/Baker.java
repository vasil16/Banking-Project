import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Point;

public class Baker extends JFrame {
	
	String cusid = "1122";
	String cuspin = "1111";
	JPanel log=new JPanel();
	TextField getid=new TextField();
	JPasswordField getpin =new JPasswordField();
	JLabel id=new JLabel("ID");
	JLabel pin=new JLabel("PIN");
	JLabel l3=new JLabel("Welcome");
	JButton login=new JButton("Login");
	JButton reset=new JButton("Reset");
	JButton exit=new JButton("Exit");
	
	public Baker() {
		
		setTitle("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(435, 301);
        setLocationRelativeTo(null);
		
		log.setSize(getPreferredSize());
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
		login.setActionCommand("Login");
		login.setBounds(160, 174, 80, 22);
		log.add(login);
		reset.setBounds(242, 174, 80, 22);
		log.add(reset);
		exit.setBounds(329, 229, 80, 22);
		log.add(exit);
		
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				
				String idguess = getid.getText();
				char[] ping = getpin.getPassword();
				String pinguess=new String(ping);
				
				if(idguess.equals(cusid)&&pinguess.equals(cuspin)) {
					dispose();
					new Home();
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Invalid credentials");
				}
					
				
				
			}
		});
		
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getid.setText(null);
				getpin.setText(null);
				
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}	
		});
		
		
		
		
	}	
		
	
	   public static void main(String[]args) {
		   new Baker();
	   }
	
}


