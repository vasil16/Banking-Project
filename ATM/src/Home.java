import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;
import java.sql.SQLException;

public class Home extends JFrame {
	
	int balance=0;
	int amount;
	int pin;
	int customerId;
	
	
	
	public Home(int CustomerId) 
	{
		super("Home");
		this.customerId = CustomerId;
		setSize(572,418);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		build();
		
	}

	public Home()
	{
		
		System.out.println("con call");
	}

	
	public void build() 
	{
		JLabel lbl1 = new JLabel("Choose An Option");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
		lbl1.setBounds(114, 22, 312, 42);
		getContentPane().add(lbl1);
		
		
		//Withdraw Panel
		Panel withdrawPanel = new Panel();
		withdrawPanel.setBackground(Color.LIGHT_GRAY);
		withdrawPanel.setBounds(254, 75, 256, 230);
		
		withdrawPanel.setLayout(null);
		Label withAmt = new Label("Enter Amount");
		withAmt.setAlignment(Label.CENTER);
		withAmt.setFont(new Font("Dialog", Font.BOLD, 12));
		withAmt.setForeground(Color.WHITE);
		withAmt.setBounds(65, 50, 135, 22);
		withdrawPanel.add(withAmt);
		
		TextField getwithAmt = new TextField();
		getwithAmt.setBounds(85, 94, 89, 22);
		withdrawPanel.add(getwithAmt);
		
		Button withcnf = new Button("Confirm");
		withcnf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(getwithAmt.getText().isBlank() || !getwithAmt.getText().matches("\\d"))
				{
					JOptionPane.showMessageDialog(null, "Enter Valid Amount");
					getwithAmt.setText("");
					return;
				}
				String amtstr=getwithAmt.getText();
				amount=Integer.parseInt(amtstr);
				
				if(amount<=0)
				{
					JOptionPane.showMessageDialog(null, "Enter Valid Amount");
					getwithAmt.setText("");
					return;
				}
				
				Withdraw withdraw = new Withdraw();
				if(!withdraw.isBalanceSufficient(customerId, amount))
				{
					JOptionPane.showMessageDialog(null, "Insufficient Balance");
					getwithAmt.setText("");
					getContentPane().remove(withdrawPanel);
				}
				
				else
				{
					balance = withdraw.withdrawAmount(customerId, amount);
					getwithAmt.setText("");
					JOptionPane.showMessageDialog(null, "Withdraw Complete!!Available Balance: "+balance);
					getContentPane().remove(withdrawPanel);
				}
			}
		});
		withcnf.setBounds(85, 138, 89, 22);
		withdrawPanel.add(withcnf);
						
		
		Panel depositPanel = new Panel();
		depositPanel.setBackground(Color.LIGHT_GRAY);
		depositPanel.setForeground(Color.PINK);
		depositPanel.setBounds(254, 75, 256, 230);
		
		depositPanel.setLayout(null);
		
		Label depAmt = new Label("Enter Amount");
		depAmt.setFont(new Font("Dialog", Font.BOLD, 12));
		depAmt.setForeground(Color.WHITE);
		depAmt.setBounds(10, 22, 79, 22);
		depositPanel.add(depAmt);
		
		TextField getdepAmt = new TextField();
		getdepAmt.setForeground(Color.BLACK);
		getdepAmt.setBounds(10, 50, 89, 22);
		depositPanel.add(getdepAmt);
		
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
				getContentPane().remove(depositPanel);
				}
			}
		});
		depcnf_1.setForeground(Color.BLACK);
		depcnf_1.setBounds(10, 101, 70, 22);
		depositPanel.add(depcnf_1);
				
		
		
		
		//Home UI
		JButton blnc = new JButton("Check Balance");
		blnc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FetchBalance();
				JOptionPane.showMessageDialog(null, "Available Balance:"+ balance);
			}
		});
		blnc.setBounds(52, 75,140,50);
		blnc.setAlignmentX(CENTER_ALIGNMENT);
		getContentPane().add(blnc);
		
		JButton withdrawBtn = new JButton("Withdraw");
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(depositPanel.isEnabled())
				{
					getContentPane().remove(depositPanel);
				}
				getContentPane().add(withdrawPanel);
				 
			}

			
		});
		withdrawBtn.setBounds(52, 169, 140, 50);
		getContentPane().add(withdrawBtn);
		
		JButton depositBtn = new JButton("Deposit");
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(withdrawPanel.isEnabled())
				{
					getContentPane().remove(withdrawPanel);
				}
				getContentPane().add(depositPanel);
				
			}
		});
		depositBtn.setBounds(52, 255, 140, 50);
//		getContentPane().add(depositBtn);
		
		JButton transactionBtn = new JButton("Transactions");
		transactionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(withdrawPanel.isEnabled())
				{
					getContentPane().remove(withdrawPanel);
				}
				getContentPane().add(depositPanel);
				
			}
		});
		transactionBtn.setBounds(52, 255, 140, 50);
//		getContentPane().add(transactionBtn);
		
		JButton logout = new JButton("Logout");
		logout.setBackground(new Color(255, 0, 2));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				new Login();
			}
		});
		logout.setBounds(421, 336, 89, 23);
		getContentPane().add(logout);
		
		JButton pinchangeBtn = new JButton("Change Pin");
		pinchangeBtn.setBounds(449, 6, 117, 29);
		pinchangeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new PinChange(customerId, Home.this);
			}
		});
		
		getContentPane().add(pinchangeBtn);
		
		setVisible(true);
		setResizable(false);
		
	}

	private void FetchBalance()
	{
		try
		{
			Connection connection = DriverManager.getConnection(DBSetup.url);
			String query = "SELECT balance FROM Accounts WHERE customer_id = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) 
            {
                preparedStatement.setInt(1, customerId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) 
                {
					if(resultSet.next())
					{
						balance = (int) (resultSet.getDouble("balance"));
					}
                }
            }
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}