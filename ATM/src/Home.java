import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;

public class Home extends JFrame {
	
	int balance=0;
	int amount;
	int pin;
	
	
	
	public Home() {
		super("Home");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(572,418);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		build();
		
		
		
		
	}
	public void build() {
		
		
	
		
		
		JLabel lbl1 = new JLabel("Choose An Option");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
		lbl1.setBounds(114, 22, 312, 42);
		getContentPane().add(lbl1);
		Panel panelwd = new Panel();
		panelwd.setBackground(Color.LIGHT_GRAY);
		panelwd.setBounds(254, 75, 125, 150);
		
		panelwd.setLayout(null);
		Label withAmt = new Label("Enter Amount");
		withAmt.setFont(new Font("Dialog", Font.BOLD, 12));
		withAmt.setForeground(Color.WHITE);
		withAmt.setBounds(10, 22, 79, 22);
		panelwd.add(withAmt);
		
		TextField getwithAmt = new TextField();
		getwithAmt.setBounds(10, 54, 89, 22);
		panelwd.add(getwithAmt);
		
		Button withcnf = new Button("Confirm");
		withcnf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String amtstr=getwithAmt.getText();
				amount=Integer.parseInt(amtstr);
				if(amount<=0) {
					JOptionPane.showMessageDialog(null, "Invalid Amount");
				
				}
				else if(amount>balance) {
					JOptionPane.showMessageDialog(null, "Insufficient Balance");
					getContentPane().remove(panelwd);
				}
				else {
				balance=balance-amount;
				JOptionPane.showMessageDialog(null, "Withdraw Complete!!Available Balance: "+balance);
				getContentPane().remove(panelwd);
				}
			}
		});
		withcnf.setBounds(10, 101, 70, 22);
		panelwd.add(withcnf);
		
		Panel paneldep = new Panel();
		paneldep.setBackground(Color.LIGHT_GRAY);
		paneldep.setForeground(Color.PINK);
		paneldep.setBounds(385, 75, 125, 150);
		
		paneldep.setLayout(null);
		
		Label depAmt = new Label("Enter Amount");
		depAmt.setFont(new Font("Dialog", Font.BOLD, 12));
		depAmt.setForeground(Color.WHITE);
		depAmt.setBounds(10, 22, 79, 22);
		paneldep.add(depAmt);
		
		TextField getdepAmt = new TextField();
		getdepAmt.setForeground(Color.BLACK);
		getdepAmt.setBounds(10, 50, 89, 22);
		paneldep.add(getdepAmt);
		
		Button depcnf_1 = new Button("Confirm");
		depcnf_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String amtstr=getdepAmt.getText();
				amount=Integer.parseInt(amtstr);
				if(amount<=0) {
					JOptionPane.showMessageDialog(null, "Enter a Valid Amount");
				}
				else
				{
				balance=balance+amount;
				JOptionPane.showMessageDialog(null, "Deposit Complete!!Available Balance: "+balance);
				getContentPane().remove(paneldep);
				}
			}
		});
		depcnf_1.setForeground(Color.BLACK);
		depcnf_1.setBounds(10, 101, 70, 22);
		paneldep.add(depcnf_1);
		setVisible(true);
		
		
		JButton blnc = new JButton("Check Balance");
		blnc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Available Balance:"+ balance);
			}
		});
		blnc.setBounds(52, 75,140,50);
		blnc.setAlignmentX(CENTER_ALIGNMENT);
		getContentPane().add(blnc);
		
		JButton wdr = new JButton("Withdraw");
		wdr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				getContentPane().add(panelwd);
				 
			}

			
		});
		wdr.setBounds(52, 169, 140, 50);
		getContentPane().add(wdr);
		
		JButton dps = new JButton("Deposit");
		dps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				getContentPane().add(paneldep);
				
			}
		});
		dps.setBounds(52, 255, 140, 50);
		getContentPane().add(dps);
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				new Login();
			}
		});
		logout.setBounds(423, 311, 89, 23);
		getContentPane().add(logout);
		
		
	}
	
	
	public static void main(String[]args) {
		
		new Login();
		
	}
}
