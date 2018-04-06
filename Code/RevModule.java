import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.*;
import java.awt.Color;

public class RevModule {

	private JFrame frmWriteAReview;
	private JTextField textField;
	private JTextField textField_1;
	private int REVIEWER;
	private int REVIEWEE;
	private String REVIEWER_NAME;
	private String REVIEWEE_NAME;
	private int rating = 0;
	private Connection CONN;

	private ResultSet rs;

	public RevModule(Connection conn, int user, int revuser) {
		REVIEWER = user;
		REVIEWEE = revuser;
		CONN = conn;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("select username from Accounts where userid = " + REVIEWER);
			rs.next();
			REVIEWER_NAME = rs.getString(1);
			rs = stmt.executeQuery("select username from Accounts where userid = " + REVIEWEE);
			rs.next();
			REVIEWEE_NAME = rs.getString(1);
			rs.close();
			stmt.close();
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end Catch
		initialize();
		this.frmWriteAReview.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWriteAReview = new JFrame();
		frmWriteAReview.getContentPane().setBackground(new Color(0, 204, 204));
		frmWriteAReview.setFont(new Font("Dialog", Font.PLAIN, 16));
		frmWriteAReview.setTitle("Write a review");
		frmWriteAReview.setBounds(100, 100, 571, 369);
		frmWriteAReview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmWriteAReview.getContentPane().setLayout(null);
		
		JLabel lblEnnterTheUsername = new JLabel("Displayed is the username of the reviewer:");
		lblEnnterTheUsername.setBounds(10, 11, 251, 14);
		frmWriteAReview.getContentPane().add(lblEnnterTheUsername);
		
		
		JLabel lblEnterTheUsername = new JLabel("Displayed is the username of the user being reviewed:");
		lblEnterTheUsername.setBounds(10, 43, 318, 14);
		frmWriteAReview.getContentPane().add(lblEnterTheUsername);
		
		JLabel lblEnterTheReview = new JLabel("Enter the review of the user below:");
		lblEnterTheReview.setBounds(10, 75, 251, 14);
		frmWriteAReview.getContentPane().add(lblEnterTheReview);
		
		textField = new JTextField(); //the username of the user giving the review needs to be pulled from the database and displayed here
		textField.setBackground(new Color(0, 204, 204));
		textField.setBounds(361, 8, 158, 20);
		frmWriteAReview.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		textField.setText(REVIEWER_NAME);
		
		textField_1 = new JTextField(); // the username of the user being reviewed needs to be pulled from the database and displayed here
		textField_1.setBackground(new Color(0, 204, 204));
		textField_1.setBounds(361, 40, 158, 20);
		frmWriteAReview.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setEditable(false);
		textField_1.setText(REVIEWEE_NAME);
		
		JEditorPane reviewField = new JEditorPane(); // the review written here needs to be stored to database when "post review" is clicked
		reviewField.setBounds(10, 118, 535, 167);
		frmWriteAReview.getContentPane().add(reviewField);
		
		JLabel lblRateTheUser = new JLabel("Rate the user 1-5:");
		lblRateTheUser.setBounds(10, 93, 114, 14);
		frmWriteAReview.getContentPane().add(lblRateTheUser);
		
		JRadioButton radioButton = new JRadioButton("1");
		radioButton.setBackground(new Color(0, 204, 204));
		radioButton.setBounds(130, 88, 48, 23);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
					rating = 1;
			}
			
		});
		frmWriteAReview.getContentPane().add(radioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("2");
		radioButton_1.setBackground(new Color(0, 204, 204));
		radioButton_1.setBounds(180, 88, 48, 23);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
					rating = 2;
			}
			
		});
		frmWriteAReview.getContentPane().add(radioButton_1);
		group.add(radioButton_1);
		JRadioButton radioButton_2 = new JRadioButton("3");
		radioButton_2.setBackground(new Color(0, 204, 204));
		radioButton_2.setBounds(230, 88, 48, 23);
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
					rating = 3;
			}
			
		});
		frmWriteAReview.getContentPane().add(radioButton_2);
		group.add(radioButton_2);
		JRadioButton radioButton_3 = new JRadioButton("4");
		radioButton_3.setBackground(new Color(0, 204, 204));
		radioButton_3.setBounds(280, 88, 48, 23);
		radioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
					rating = 4;
			}
			
		});
		frmWriteAReview.getContentPane().add(radioButton_3);
		group.add(radioButton_3);
		JRadioButton radioButton_4 = new JRadioButton("5");
		radioButton_4.setBackground(new Color(0, 204, 204));
		radioButton_4.setBounds(330, 88, 48, 23);
		radioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
					rating = 5;
			}
			
		});
		frmWriteAReview.getContentPane().add(radioButton_4);
		group.add(radioButton_4);
		
		JButton btnPostReview = new JButton("Post Review"); // this button needs to send the review to the database
		btnPostReview.setBounds(301, 296, 107, 23);
		btnPostReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (rating != 0) {
					AddReview.addReview(CONN, REVIEWEE, rating);
					frmWriteAReview.dispose();
				}//end if
			}
			
		});
		frmWriteAReview.getContentPane().add(btnPostReview);
		
		JLabel label = new JLabel("");
		label.setBounds(230, 11, 46, 14);
		frmWriteAReview.getContentPane().add(label);
		
		JButton btnCancel = new JButton("Cancel"); // this button needs to cancel and exit the review
		btnCancel.setBounds(418, 296, 107, 23);
		frmWriteAReview.getContentPane().add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frmWriteAReview.dispose();
			}
			
		});
	}
}
