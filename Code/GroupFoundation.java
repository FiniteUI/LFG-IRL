
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.sql.*;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.SystemColor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

//window for managing groups
public class GroupFoundation {

	private JFrame frame;//main frame
	private JTextField searchField;
	private ResultSet Cat;//search results
	private String[] categories;//top categories
	private JTable CaTable;
	private Vector<String> columnNamesVec = new Vector<String>();//column names
	private Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
	private String searchTerm = "%";//search term
	private int CURRENT_USER;//id of the current logged in user
	private Vector<Integer> currentGroupIDs = new Vector<Integer>();//ids of groups on current display
	private Vector<Integer> currentMemberIDs = new Vector<Integer>();
	private JTable table;
	private ResultSet GInfo;//group info
	private int selectedGroup;//which group is selected
	private static final String URL = "jdbc:mysql://rseddon.heliohost.org/rseddon_lfg";
	private static final String DBUsername = "rseddon_general";
	private static final String DBPassword = "";
	private Connection conn = null;
	private ResultSet memberInfo = null;
	private Vector<String> memberColumnNames = new Vector<String>();
	private boolean nearme = false;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnOpenChat;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	JPanel cardTopCategories = new JPanel();
	JPanel cardSearchResults = new JPanel();
	JPanel cardCreateGroup = new JPanel();
	JPanel cardGroupJoin = new JPanel();
	private String inputName;
	private String inputCat;
	private String inputLoc;
	private String inputDate;
	private String inputDesc;
	private int	inputMaxMem;
	private int inputZip;
	private boolean userInGroup = false;
	private int userIntGroup = 0;
	private int selectedGroupOwner;
	private JButton btnCat0;
	private JButton btnCat1;
	private JButton btnCat2;
	private JButton btnCat3;
	private JButton btnCat4;
	private JButton btnCat5;
	private JScrollPane scrollPane;
	private JButton btnViewGroup;
	private int reviewee;
	private JButton btnReviewUser;
	private JButton btnBlockUser;
	private JLabel label_5;

	//create window
	public GroupFoundation(int u, String n) {
		CURRENT_USER = u;
		initialize();
		label_5.setText("User: " + n);
		this.frame.setVisible(true);
	}//end GroupFoundation

	//do all the stuff
	private void initialize() {

		try {
			conn = DriverManager.getConnection(URL, DBUsername, DBPassword);
			conn.setAutoCommit(false);
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end Catch

		categories = GetCategories.topCategoriesStrings(conn);

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 204, 204));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 1060, 672);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 204, 204));
		panel.setBounds(39, 110, 969, 472);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));

		//create columnNames vector
		columnNamesVec.addElement("Name");
		columnNamesVec.addElement("Category");
		columnNamesVec.addElement("Description");
		columnNamesVec.addElement("Date");
		columnNamesVec.addElement("Location");
		columnNamesVec.addElement("Max Members");

		//create emberColumnNames vector
		memberColumnNames.addElement("User");
		memberColumnNames.addElement("Age");
		cardTopCategories.setBackground(new Color(0, 204, 204));

		//JPanel cardTopCategories = new JPanel();
		panel.add(cardTopCategories, "name_529801214552514");
		cardTopCategories.setLayout(null);
		cardSearchResults.setBackground(new Color(0, 204, 204));

		//JPanel cardSearchResults = new JPanel();
		panel.add(cardSearchResults, "name_535265331229536");
		cardSearchResults.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(0, 204, 204));
		scrollPane.setBounds(0, 0, 969, 472);
		cardSearchResults.add(scrollPane);

		CaTable = new JTable() {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		scrollPane.getViewport().setBackground(new Color(0,204,204));
		CaTable.setSelectionForeground(new Color(255, 153, 102));
		CaTable.setSelectionBackground(new Color(255, 255, 255));
		CaTable.setBackground(new Color(102, 204, 204));
		CaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (CaTable.getSelectedRow() != -1) {
					selectedGroup = CaTable.getSelectedRow();
					selectedGroup = currentGroupIDs.get(selectedGroup);

				}//end if
			}
		});

		CaTable.getTableHeader().setReorderingAllowed(false);
		CaTable.getTableHeader().setResizingAllowed(false);
		CaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableModel CaTableModel = new DefaultTableModel(rows, columnNamesVec);
		CaTable.setModel(CaTableModel);
		//CaTable.setBackground(new Color(0,204,204));
		scrollPane.setViewportView(CaTable);
		scrollPane.setForeground(new Color(0,204,204));
		CaTable.getTableHeader().setBackground(Color.orange);

		JButton btnTC = new JButton("Top Categories");
		btnTC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTC();
				hideCards();
				btnViewGroup.setEnabled(false);
				cardTopCategories.show();
			}
		});
		btnTC.setBounds(61, 32, 120, 23);
		frame.getContentPane().add(btnTC);

		btnCat1 = new JButton(categories[1]);
		btnCat1.setBounds(347, 96, 265, 133);
		btnCat1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableize(1));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
			}//end actionpreformed
		});
		cardTopCategories.add(btnCat1);

		btnCat2 = new JButton(categories[2]);
		btnCat2.setBounds(677, 96, 242, 133);
		btnCat2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableize(2));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
			}//end actionpreformed
		});
		cardTopCategories.add(btnCat2);

		btnCat3 = new JButton(categories[3]);
		btnCat3.setBounds(33, 283, 255, 133);
		btnCat3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableize(3));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
			}//end actionpreformed
		});
		cardTopCategories.add(btnCat3);

		btnCat4 = new JButton(categories[4]);
		btnCat4.setBounds(347, 283, 265, 133);
		btnCat4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableize(4));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
			}//end actionpreformed
		});
		cardTopCategories.add(btnCat4);

		JLabel lblTopCategories = new JLabel("Top Categories");
		lblTopCategories.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTopCategories.setBounds(436, 11, 113, 17);
		cardTopCategories.add(lblTopCategories);

		btnCat5 = new JButton(categories[5]);
		btnCat5.setBounds(677, 283, 242, 133);
		btnCat5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableize(5));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
			}//end actionpreformed
		});
		cardTopCategories.add(btnCat5);

		btnCat0 = new JButton(categories[0]);
		btnCat0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableize(0));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
			}//end actionpreformed
		});
		btnCat0.setBounds(33, 96, 255, 133);
		cardTopCategories.add(btnCat0);

		JSeparator separator = new JSeparator();
		separator.setBounds(33, 39, 886, 2);
		cardTopCategories.add(separator);
		cardCreateGroup.setBackground(new Color(0, 204, 204));
		cardCreateGroup.setBorder(new LineBorder(new Color(0, 0, 0)));

		//JPanel cardCreateGroup = new JPanel();
		panel.add(cardCreateGroup, "name_307932265652719");
		cardCreateGroup.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][][][][][][grow]"));

		JLabel lblNewGroup = new JLabel("New Group");
		lblNewGroup.setFont(new Font("Tahoma", Font.BOLD, 14));
		cardCreateGroup.add(lblNewGroup, "cell 2 0");

		JLabel lblNewLabel = new JLabel("Group Name:");
		cardCreateGroup.add(lblNewLabel, "cell 1 1,alignx trailing");

		textField_1 = new JTextField();
		cardCreateGroup.add(textField_1, "cell 2 1,alignx left");
		textField_1.setColumns(40);

		JLabel lblNewLabel_1 = new JLabel("");
		cardCreateGroup.add(lblNewLabel_1, "cell 2 2");

		JLabel lblCategory_1 = new JLabel("Category:");
		cardCreateGroup.add(lblCategory_1, "cell 1 3,alignx trailing");

		textField_2 = new JTextField();
		cardCreateGroup.add(textField_2, "cell 2 3,alignx left");
		textField_2.setColumns(40);

		JLabel lblLocation_1 = new JLabel("Location:");
		cardCreateGroup.add(lblLocation_1, "cell 1 5,alignx trailing");

		textField_3 = new JTextField();
		cardCreateGroup.add(textField_3, "cell 2 5,alignx left");
		textField_3.setColumns(40);

		JLabel lblZipcode = new JLabel("Zipcode:");
		cardCreateGroup.add(lblZipcode, "cell 1 7,alignx trailing");

		textField_4 = new JTextField();
		cardCreateGroup.add(textField_4, "cell 2 7,alignx left");
		textField_4.setColumns(5);

		JLabel label_1 = new JLabel("");
		cardCreateGroup.add(label_1, "cell 2 8");

		JLabel lblMeetingTimeAnd = new JLabel("Meeting Time and Date:");
		cardCreateGroup.add(lblMeetingTimeAnd, "cell 1 9,alignx trailing");

		textField_5 = new JTextField();
		cardCreateGroup.add(textField_5, "cell 2 9,alignx left");
		textField_5.setColumns(40);

		JLabel lblMaxMembers = new JLabel("Max Members:");
		cardCreateGroup.add(lblMaxMembers, "cell 1 11,alignx trailing");

		textField_6 = new JTextField();
		cardCreateGroup.add(textField_6, "cell 2 11,alignx left");
		textField_6.setColumns(2);

		JLabel label_2 = new JLabel("");
		cardCreateGroup.add(label_2, "cell 2 12");

		JLabel lblDescription = new JLabel("Description:");
		cardCreateGroup.add(lblDescription, "cell 1 13");

		JEditorPane editorPane = new JEditorPane();
		editorPane.setBorder(new LineBorder(Color.DARK_GRAY));
		cardCreateGroup.add(editorPane, "cell 2 13 1 2,grow");
		cardGroupJoin.setBackground(new Color(0, 204, 204));

		//JPanel cardGroupJoin = new JPanel();
		panel.add(cardGroupJoin, "name_529792409738599");
		cardGroupJoin.setLayout(null);

		JLabel lblGroupName = new JLabel("Group Name");
		lblGroupName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGroupName.setBounds(399, 8, 156, 21);
		cardGroupJoin.add(lblGroupName);

		JLabel lblGroupCategory = new JLabel("Group Category");
		lblGroupCategory.setBounds(399, 32, 116, 23);
		cardGroupJoin.add(lblGroupCategory);

		JLabel lblGroupLocation = new JLabel("Group Location");
		lblGroupLocation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGroupLocation.setBounds(74, 78, 400, 23);
		cardGroupJoin.add(lblGroupLocation);

		JLabel lblGroupDate = new JLabel("Group Date");
		lblGroupDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGroupDate.setBounds(74, 100, 415, 28);
		cardGroupJoin.add(lblGroupDate);

		JEditorPane lblGroupDescription = new JEditorPane();
		lblGroupDescription.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblGroupDescription.setBackground(new Color(0, 204, 204));
		lblGroupDescription.setEditable(false);
		lblGroupDescription.setBounds(74, 132, 484, 287);
		cardGroupJoin.add(lblGroupDescription);

		JLabel lblMembers = new JLabel("Members");
		lblMembers.setBounds(778, 69, 82, 14);
		cardGroupJoin.add(lblMembers);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(new Color(0, 204, 204));
		scrollPane_1.setBounds(679, 94, 260, 351);
		cardGroupJoin.add(scrollPane_1);
		scrollPane_1.getViewport().setBackground(new Color(0,204,204));

		table = new JTable(){
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table.setBackground(new Color(0, 204, 204));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(Color.orange);
		scrollPane_1.setViewportView(table);

		JLabel label = new JLabel("");
		label.setBounds(138, 15, 162, 14);
		cardGroupJoin.add(label);

		JLabel label_3 = new JLabel("");
		label_3.setBounds(138, 47, 116, 14);
		cardGroupJoin.add(label_3);

		JButton btnLeaveGroup = new JButton("Leave Group");
		btnLeaveGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedGroupOwner == CURRENT_USER) {
					label_3.setText("You own this group.");
				}//end if
				else {
					RemoveMember.removeMember(conn, CURRENT_USER, selectedGroup);
					userInGroup = false;
					table.setModel(tableizeMembers(selectedGroup));
				}//end else

			}
		});
		btnLeaveGroup.setBounds(18, 43, 110, 23);
		cardGroupJoin.add(btnLeaveGroup);

		btnOpenChat = new JButton("Open Chat");
		btnOpenChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = null;
				/*
				Cat = GetMembers.getMemberInfo(conn, CURRENT_USER);
				try {
					Cat.next();
					username = Cat.getString("username");
				}//end try
				catch (Exception e3) {
					e3.printStackTrace();
				}//end catch
				*/
				username = GetMembers.getUserName(conn, CURRENT_USER);
				new chatmain(username, selectedGroup, tableizeMembers(selectedGroup));
			}
		});
		btnOpenChat.setBounds(431, 449, 101, 23);
		btnOpenChat.setEnabled(false);
		cardGroupJoin.add(btnOpenChat);

		JButton btnJoinGroup = new JButton("Join Group");
		btnJoinGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (userInGroup) {
					label.setText("You're already in this group!");
				}//end if
				else {
					AddMember.addMember(conn, CURRENT_USER, selectedGroup);
					label.setText("Success!");;
					GInfo = GroupSearch.searchID(conn, selectedGroup);
					try {
						if (GInfo.next()){
							lblGroupName.setText(GInfo.getString("name"));
							lblGroupCategory.setText(GInfo.getString("category"));
							lblGroupDescription.setText(GInfo.getString("description"));
							lblGroupLocation.setText(GInfo.getString("city"));
							lblGroupDate.setText(GInfo.getString("date"));
						}//endif
					}//end try
					catch(SQLException sqle) {
						System.out.println(sqle.getMessage());
					}//end catch
					//GroupSearch.closeDB();
					table.setModel(tableizeMembers(selectedGroup));
					CaTable.clearSelection();
					userInGroup = true;
					btnLeaveGroup.setEnabled(true);
					btnOpenChat.setEnabled(true);
					hideCards();
					cardGroupJoin.show();
				}//end else
			}
		});
		btnJoinGroup.setBounds(18, 9, 110, 23);
		cardGroupJoin.add(btnJoinGroup);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(310, 13, 46, 14);
		cardGroupJoin.add(lblName);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setBounds(310, 36, 64, 14);
		cardGroupJoin.add(lblCategory);

		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(18, 82, 64, 14);
		cardGroupJoin.add(lblLocation);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(18, 107, 46, 14);
		cardGroupJoin.add(lblDate);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 0, 969, 2);
		cardGroupJoin.add(separator_1);

		JButton btnDeleteGroup = new JButton("Delete Group");
		btnDeleteGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteGroup.deleteGroup(conn, selectedGroup);
				//btnReviewUser.setEnabled(false);
				btnBlockUser.setEnabled(false);
				btnTC.doClick();
			}
		});
		btnDeleteGroup.setBounds(823, 32, 116, 23);
		cardGroupJoin.add(btnDeleteGroup);
		btnDeleteGroup.setEnabled(false);

		JButton btnReviewUser = new JButton("Review User");
		btnReviewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1 && currentMemberIDs.get(table.getSelectedRow()) != CURRENT_USER) {
					reviewee = currentMemberIDs.get(table.getSelectedRow());
					new RevModule(conn, CURRENT_USER, reviewee);
					//replace 1 with reviewee
				}//end if
			}
		});
		btnReviewUser.setBounds(676, 32, 116, 23);
		cardGroupJoin.add(btnReviewUser);
		btnReviewUser.setEnabled(false);

		btnBlockUser = new JButton("Block User");
		btnBlockUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BlockUser.Block(conn, selectedGroup, currentMemberIDs.get(table.getSelectedRow()));
				table.setModel(tableizeMembers(selectedGroup));
			}
		});
		btnBlockUser.setBounds(764, 449, 110, 23);
		cardGroupJoin.add(btnBlockUser);
		btnBlockUser.setEnabled(true);

		JLabel lblOwner = new JLabel("Owner:");
		lblOwner.setBounds(310, 69, 46, 14);
		cardGroupJoin.add(lblOwner);

		JLabel label_4 = new JLabel("");
		label_4.setBounds(399, 66, 89, 14);
		cardGroupJoin.add(label_4);

		JLabel lblGroups = new JLabel("Groups");
		lblGroups.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGroups.setBounds(487, 11, 72, 32);
		frame.getContentPane().add(lblGroups);

		JButton btnCreateGroup = new JButton("Create Group");
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideCards();
				btnViewGroup.setEnabled(false);
				btnReviewUser.setEnabled(false);
				cardCreateGroup.show();
			}
		});
		btnCreateGroup.setBounds(61, 58, 120, 23);
		frame.getContentPane().add(btnCreateGroup);

		searchField = new JTextField();
		searchField.setBounds(800, 59, 103, 20);
		frame.getContentPane().add(searchField);
		searchField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(919, 58, 89, 23);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTerm = searchField.getText();
				CaTable.setModel(tableizeSearch(searchTerm));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
				btnReviewUser.setEnabled(false);
				btnViewGroup.setEnabled(false);
			}//end actionpreformed
		});
		frame.getContentPane().add(btnSearch);

		JCheckBox chckbxNearMe = new JCheckBox("Near Me");
		chckbxNearMe.setBackground(new Color(0, 204, 204));
		chckbxNearMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nearme = !nearme;
			}
		});
		chckbxNearMe.setBounds(847, 86, 97, 23);
		frame.getContentPane().add(chckbxNearMe);

		//show group page
		btnViewGroup = new JButton("View Group");
		btnViewGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					selectedGroup = CaTable.getSelectedRow();
				if (selectedGroup != -1) {
					selectedGroup = currentGroupIDs.get(selectedGroup);
					GInfo = GroupSearch.searchID(conn, selectedGroup);
					try {
						if (GInfo.next()){
							lblGroupName.setText(GInfo.getString("name"));
							lblGroupCategory.setText(GInfo.getString("category"));
							lblGroupDescription.setText(GInfo.getString("description"));
							lblGroupLocation.setText(GInfo.getString("city"));
							lblGroupDate.setText(GInfo.getString("date"));
						}//endif
					}//end try
					catch(SQLException sqle) {
						System.out.println(sqle.getMessage());
					}//end catch
					table.setModel(tableizeMembers(selectedGroup));
					selectedGroupOwner = GetMembers.getOwner(conn, selectedGroup);
					if (selectedGroupOwner != CURRENT_USER) {
						btnDeleteGroup.setEnabled(false);
						btnLeaveGroup.setEnabled(userInGroup);
					}//end if
					else {
						btnDeleteGroup.setEnabled(true);
						btnLeaveGroup.setEnabled(false);
					}//end else
					CaTable.clearSelection();
					hideCards();
					cardGroupJoin.show();
					btnReviewUser.setEnabled(false);
					btnViewGroup.setEnabled(false);
					btnOpenChat.setEnabled(userInGroup);
					btnJoinGroup.setEnabled(!userInGroup);
					label_3.setText("");
					label.setText("");
					label_4.setText(GetMembers.getUserName(conn, selectedGroupOwner));
					btnBlockUser.setEnabled(false);
				}//end if
			}
		});
		btnViewGroup.setEnabled(false);
		btnViewGroup.setBounds(463, 54, 103, 23);
		frame.getContentPane().add(btnViewGroup);

		CaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (CaTable.getSelectedRow() != -1) {
					selectedGroup = CaTable.getSelectedRow();
					selectedGroup = currentGroupIDs.get(selectedGroup);
					btnViewGroup.setEnabled(true);
				}//end if
			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRow() != -1 && currentMemberIDs.get(table.getSelectedRow()) != CURRENT_USER) {
					btnReviewUser.setEnabled(userInGroup);
					if (CURRENT_USER == selectedGroupOwner) {
						btnBlockUser.setEnabled(userInGroup);
					}//end if
					else {
						btnBlockUser.setEnabled(false);
					}//end else
				}//end if
				else {
					btnReviewUser.setEnabled(false);
					btnBlockUser.setEnabled(false);
				}//end else
			}//end valueChanged
		});

		JButton btnMyGroups = new JButton("My Groups");
		btnMyGroups.setBounds(61, 86, 120, 23);
		frame.getContentPane().add(btnMyGroups);
		btnMyGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CaTable.setModel(tableizeUsersGroups(CURRENT_USER));
				scrollPane.setViewportView(CaTable);
				//GroupSearch.closeDB();
				hideCards();
				cardSearchResults.show();
				btnViewGroup.setEnabled(false);
				btnReviewUser.setEnabled(false);
			}//end actionpreformed
		});

		JButton btnActuallyCreateGroup = new JButton("Create");
		btnActuallyCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean ready = true;
				inputName = textField_1.getText();
				inputName.replaceAll("'", "''");
				inputCat = textField_2.getText();
				inputLoc = textField_3.getText();
				inputDate = textField_5.getText();
				inputDesc = editorPane.getText();
				inputDesc.replaceAll("'", "''");
				String tempMaxMem = textField_6.getText();
				if (tempMaxMem.length() > 3 || tempMaxMem.length() < 1) {
					label_2.setText("Error: Max Members must be a number less that 999");
					ready = false;
				}//end if
				for(int i = 0; i < tempMaxMem.length(); i++ ) {
					if (!Character.isDigit(tempMaxMem.charAt(i))){
						label_2.setText("Error: Max Members must be a number less that 999");
						break;
					}//end if
				}//end for
				String tempZip = textField_4.getText();
				if (tempZip.length() != 5) {
					ready = false;
					label_1.setText("Error: Zipcode must be 5 digit number");
				}//end if
				for(int i = 0; i < tempZip.length(); i++ ) {
					if (!Character.isDigit(tempZip.charAt(i))){
						label_1.setText("Error: Zipcode must be 5 digit number");
						ready = false;
						break;
					}//end if
				}//end for
				if (inputName.isEmpty() || inputCat.isEmpty() || inputLoc.isEmpty() || inputDate.isEmpty() || inputDesc.isEmpty()) {
					lblNewLabel_1.setText("Error: Please enter all required information");
					ready = false;
				}//end if
				if (ready) {
					inputMaxMem = Integer.parseInt(tempMaxMem);
					inputZip = Integer.parseInt(tempZip);
					CreateGroup.createGroup(conn, CURRENT_USER, inputName, inputCat, inputDesc, inputDate, inputLoc, inputMaxMem, inputZip);
					btnMyGroups.doClick();
				}//end if
			}
		});
		cardCreateGroup.add(btnActuallyCreateGroup, "cell 2 15,alignx right");

		label_5 = new JLabel("");
		label_5.setBounds(847, 11, 160, 14);
		frame.getContentPane().add(label_5);
	}//end initialize

	//generate the table for a specific category
	private DefaultTableModel tableize(int catNumber) {
		String categoryPart = categories[catNumber].substring(0, categories[catNumber].indexOf(","));

		try {
			if (nearme) {
				memberInfo = GetMembers.getMemberInfo(conn, CURRENT_USER);
				memberInfo.next();
				Cat = GroupSearch.NearbyCategorySearch(conn, categoryPart, memberInfo.getInt("zipcode"));
			}
			else {
				Cat = GroupSearch.searchCategory(conn, categoryPart);
			}//end else
		}//end try
		catch(SQLException e) {
			e.printStackTrace();
		}//end catch
		rows.clear();
		currentGroupIDs.clear();
		String memberCount;

		try{
			while (Cat.next()) {
				Vector<Object> newRow = new Vector<Object>();
				newRow.addElement(Cat.getObject("name"));
				newRow.addElement(Cat.getObject("category"));
				newRow.addElement(Cat.getObject("description"));
				newRow.addElement(Cat.getObject("date"));
				newRow.addElement(Cat.getObject("city"));
				memberCount = Integer.toString(AddMember.getMemberNumber(conn, (Integer)Cat.getObject("groupid")));
				memberCount = memberCount + "/" + Cat.getObject("maxmembers").toString();
				newRow.addElement(memberCount);

				//only show groups that aren't full
				if (AddMember.getMemberNumber(conn, (Integer)Cat.getObject("groupid")) < (Integer)Cat.getObject("maxmembers")) {
					if (!BlockUser.isBlocked(conn, Cat.getInt("groupid"), CURRENT_USER)) {
						rows.addElement(newRow);
						currentGroupIDs.add((Integer)Cat.getObject("groupid"));
					}//end if
				}//end if
			 }//end while
		}//end try
		catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}//end catch
		DefaultTableModel CaTableModel = new DefaultTableModel(rows, columnNamesVec);
		return CaTableModel;
	}//end tableize

	//generate the table for a search term
	private DefaultTableModel tableizeSearch(String Term) {
		rows.clear();
		String memberCount;
		try{
			if (nearme) {
				memberInfo = GetMembers.getMemberInfo(conn, CURRENT_USER);
				memberInfo.next();
				Cat = GroupSearch.NearbySearch(conn, Term, memberInfo.getInt("zipcode"));
			}
			else {
				Cat = GroupSearch.search(conn, Term);
			}//end else
			while (Cat.next()) {
				Vector<Object> newRow = new Vector<Object>();
				newRow.addElement(Cat.getObject("name"));
				newRow.addElement(Cat.getObject("category"));
				newRow.addElement(Cat.getObject("description"));
				newRow.addElement(Cat.getObject("date"));
				newRow.addElement(Cat.getObject("city"));
				memberCount = Integer.toString(AddMember.getMemberNumber(conn, (Integer)Cat.getObject("groupid")));
				memberCount = memberCount + "/" + Cat.getObject("maxmembers").toString();
				newRow.addElement(memberCount);

				//only show groups that aren't full
				if (AddMember.getMemberNumber(conn, (Integer)Cat.getObject("groupid")) < (Integer)Cat.getObject("maxmembers")) {
					if (!BlockUser.isBlocked(conn, Cat.getInt("groupid"), CURRENT_USER)) {
						rows.addElement(newRow);
						currentGroupIDs.add((Integer)Cat.getObject("groupid"));
					}//end if
				}//end if
			}//end while
		}//end try
		catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}//end catch
		DefaultTableModel CaTableModel = new DefaultTableModel(rows, columnNamesVec);
		return CaTableModel;
	}//end tableizeSearch

	//make a table of members
	private DefaultTableModel tableizeMembers(int GID) {
		Cat = GetMembers.getMembers(conn, GID);
		currentMemberIDs.clear();
		rows.clear();
		userIntGroup = 0;
		try{
			while (Cat.next()) {
				if (Cat.getInt(1) == CURRENT_USER) {
					userIntGroup++;
				}//end if
				Vector<Object> newRow = new Vector<Object>();
				memberInfo = GetMembers.getMemberInfo(conn, Cat.getInt(1));
				if (!memberInfo.next())
					break;
				newRow.addElement(memberInfo.getObject("username"));
				newRow.addElement(memberInfo.getObject("age"));
				rows.addElement(newRow);
				currentMemberIDs.add(memberInfo.getInt("userid"));
			}//end while
		}//end try
		catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}//end catch
		DefaultTableModel CaTableModel = new DefaultTableModel(rows, memberColumnNames);
		if (userIntGroup != 0) {
			userInGroup = true;
		}//end if
		else {
			userInGroup = false;
		}//end else
		return CaTableModel;
	}//end tableizeMembers

	//This should accept a user id as i
	private DefaultTableModel tableizeUsersGroups(int UID) {
		Cat = ViewGroups.viewGroups(UID, conn);
		rows.clear();
		currentGroupIDs.clear();
		String memberCount;
		try{
			while (Cat.next()) {
				Vector<Object> newRow = new Vector<Object>();
				newRow.addElement(Cat.getObject("name"));
				newRow.addElement(Cat.getObject("category"));
				newRow.addElement(Cat.getObject("description"));
				newRow.addElement(Cat.getObject("date"));
				newRow.addElement(Cat.getObject("city"));
				memberCount = Integer.toString(AddMember.getMemberNumber(conn, (Integer)Cat.getObject("groupid")));
				memberCount = memberCount + "/" + Cat.getObject("maxmembers").toString();
				newRow.addElement(memberCount);

				rows.addElement(newRow);
				currentGroupIDs.add((Integer)Cat.getObject("groupid"));
			}//end while
		}//end try
		catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}//end catch
		DefaultTableModel CaTableModel = new DefaultTableModel(rows, columnNamesVec);
		return CaTableModel;
	}//end tableizeUserGroups

	private void hideCards() {
		cardGroupJoin.hide();
		cardCreateGroup.hide();
		cardTopCategories.hide();
		cardSearchResults.hide();
	}//hides all cards

	//refreshes the top categories
	private void refreshTC() {
		categories = GetCategories.topCategoriesStrings(conn);
		btnCat1.setText(categories[1]);
		btnCat2.setText(categories[2]);
		btnCat3.setText(categories[3]);
		btnCat4.setText(categories[4]);
		btnCat5.setText(categories[5]);
	}
}//end GroupFoundation
