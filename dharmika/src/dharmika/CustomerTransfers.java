package dharmika;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;

public class CustomerTransfers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerTransfers frame = new CustomerTransfers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerTransfers() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("From Customer");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(54, 41, 213, 21);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(257, 42, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("To Customer");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(54, 84, 160, 21);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(257, 83, 96, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Transfer Amount");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(54, 136, 213, 45);
		contentPane.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(257, 152, 96, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(148, 214, 144, 23);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dharmika", "root", "Dharmika944@");
		            Statement stmt = con.createStatement();
		            String fromCustName = textField.getText();
		            String toCustName = textField_1.getText();

		            // Retrieve names from customers table
		            int fromCusId = 0;
		            int toCustId = 0;
		            int fromCusBalance = 0 ;
		            int toCusBalance = 0 ;
		    		int transferAmount = Integer.valueOf(textField_2.getText());


		            ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE Name = '" + fromCustName + "'");
		            if (rs.next()) {
		            	System.out.println(rs);
		            	fromCusId = rs.getInt("id");
		            	fromCusBalance = rs.getInt("current_balance");
		            }else {
                        throw new Exception("from customer not found");

		            }

		            rs = stmt.executeQuery("SELECT * FROM customers WHERE  Name = '" + toCustName + "'");
		            if (rs.next()) {
		                toCustId = rs.getInt("id");
		            	toCusBalance = rs.getInt("current_balance");

		            }else {
                        throw new Exception("to customer not found");

		            }
					if (fromCusBalance >= (transferAmount)) {
						 int remainingBalanceForFromCus = fromCusBalance -transferAmount;
						 int remainingBalanceForToCus = toCusBalance + transferAmount;
		            	try {
		            			stmt.executeUpdate("UPDATE customers SET  current_balance = " + remainingBalanceForFromCus + " WHERE id = " + fromCusId);
		            			stmt.executeUpdate("UPDATE customers SET   current_balance = " + remainingBalanceForToCus + " WHERE id = " + toCustId);
		            			stmt.executeUpdate("INSERT INTO transfers (Amount, fromCusId, toCustId) VALUES (" + transferAmount + ", " + fromCusId + ", " + toCustId + ")");
		            			System.out.println("Transfer Successful!");
		                        System.out.println("Amount transferred from " + fromCustName + " to " + toCustName + ": " + transferAmount);
		            	}
		            	catch(Exception ex1) {
		            		throw new Exception("Transaction error");
		            	}
		                    } 
					else {
		                        // Insufficient balance
		                        throw new Exception("Insufficient balance");
		                    }
		            	
		            
		            // Print names
		            System.out.println("From Customer: " + fromCusId +" wtih balance "+ fromCusBalance);
		            System.out.println("To Customer: " + toCustId+ " with balance "+toCusBalance);

		            System.out.println(transferAmount);

		            // Close resources
		            rs.close();
		            con.close();
                    ct2 frame = new ct2("Transaction successful");
                    frame.setVisible(true);
                    dispose();
		        } catch (Exception ex) {
		            System.out.print(ex);
		            ct2 frame = new ct2(ex.getMessage());
                    frame.setVisible(true);
		            dispose();
		        }
		    }
		});
	}}
