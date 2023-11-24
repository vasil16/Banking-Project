import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Font;
import java.awt.Point;

public class Login extends JFrame {
	
	String cusid="1121";
	String cuspin="0000";
	
	
	JFrame f=new JFrame("XZ Mini Bank");
	JPanel log=new JPanel();
	TextField getid=new TextField();
	JPasswordField getpin =new JPasswordField();
	JLabel id=new JLabel("Enter ID");
	JLabel pin=new JLabel("Enter PIN");
	JLabel l3=new JLabel("Welcome");
	JButton login=new JButton("Login");
	JButton reset=new JButton("Reset");
	JButton exit=new JButton("Exit");
	public Login() {
		
		
		f.setLocation(new Point(300, 120));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setSize(435, 301);
		f.setVisible(true);
		f.getContentPane().add(log);
		log.setSize(getPreferredSize());
		log.setLayout(null);
		l3.setFont(new Font("Bahnschrift", Font.BOLD, 24));
		l3.setBounds(151, 36, 123, 44);
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

	public static void main(String[] args) {
		new Login();
		
	}
}
