

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextArea;
import java.awt.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import javax.swing.border.LineBorder;


public class chatmain{

	private JFrame frame;
	private static String CURRENT_USER;
	private static int CURRENT_GROUP;
	private String lastline = null;
	private String thisline = null;
	private ResultSet rs;
	private int lastLineCount = 0;
	private TextArea PostedArea;
	private JTable list;

	public chatmain(String user, int group, DefaultTableModel t) {
		CURRENT_USER = user;
		CURRENT_GROUP = group;
		initialize();
		list.setModel(t);
		this.frame.setVisible(true);
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 204, 204));
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Text area records all members chat
		TextArea PostedArea = new TextArea();
		PostedArea.setBounds(405, 147, 510, 302);
		frame.getContentPane().add(PostedArea);
		PostedArea.setEditable(false);

		OnlineChat.initialDownload(CURRENT_GROUP);
		try {
			
		File file = new File("currentChatThread.txt");
		Scanner sc;
		sc = new Scanner(file);
		PostedArea.setText(" ");
		while(sc.hasNextLine()) {
			PostedArea.insertText(sc.nextLine() + "\n" ,0);
		}
		sc.close();
		} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		OnlineChat.threadIDClose();

		//refresh thread
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable periodicTask = new Runnable() {
		public void run() {
		OnlineChat.initialDownload(CURRENT_GROUP);
		try {
			
		File file = new File("currentChatThread.txt");
		Scanner sc;
		sc = new Scanner(file);
		PostedArea.setText(" ");
		while (sc.hasNextLine()) {
			PostedArea.insertText(sc.nextLine() + "\n" ,0);
		}
		sc.close();
		} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		OnlineChat.threadIDClose();
		}
		};
		executor.scheduleAtFixedRate(periodicTask, 0, 2, TimeUnit.SECONDS);
		
		//list of all people in group
		list = new JTable();
		list.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		list.setBounds(1108, 147, 125, 302);
		frame.getContentPane().add(list);
		list.getTableHeader().setBackground(Color.orange);
		list.setBackground(new Color(0,204,204));
		
		JLabel lblMembers = new JLabel("Members");
		lblMembers.setBounds(1152, 127, 64, 14);
		frame.getContentPane().add(lblMembers);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(617, 127, 46, 14);
		frame.getContentPane().add(lblChat);
		
		//gets user input
		JTextArea InputArea = new JTextArea();
		InputArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		InputArea.setBounds(458, 481, 380, 160);
		frame.getContentPane().add(InputArea);
		InputArea.addKeyListener(
					new KeyListener() {
						public void keyPressed(KeyEvent e) {
							if(e.getKeyCode() == KeyEvent.VK_ENTER || InputArea.getText() == "") {
								String post = InputArea.getText();
								post = CURRENT_USER + ": " + post;
								//try {
									OnlineChat.edit(CURRENT_GROUP, post);
									//lastLineCount++;
									//replace(post);
								//} catch (FileNotFoundException b) {
									// TODO Auto-generated catch block
									//b.printStackTrace();
								//}
								PostedArea.insertText(post+"\n", 0);
							}
							
						}

						@Override
						public void keyTyped(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void keyReleased(KeyEvent e) {
							// TODO Auto-generated method stub
							if(e.getKeyCode() == KeyEvent.VK_ENTER)
							{
							InputArea.setText("");
							InputArea.setCaretPosition(0);
							}
						}
					});
		Button Post_B = new Button("Post");
		Post_B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(InputArea.getText() == "") {
					String post = InputArea.getText();
					post = CURRENT_USER + ": " + post;
					//try {
					OnlineChat.edit(CURRENT_GROUP, post);
						//replace(post);
					//} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					//}
					PostedArea.insertText(post+"\n", 0);
					InputArea.setText("");
				
				//Post user input to text page and file
				}
			}
		});
		Post_B.setEnabled(false);
		Post_B.setVisible(false);
		//Enter to post
		Post_B.setBounds(863, 550, 70, 22);
		frame.getContentPane().add(Post_B);
		//Logout Button
		Button Log_Button = new Button("Exit Chat");
		Log_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert Logout operations here
				frame.dispose();
			}
		});
		Log_Button.setActionCommand("Logout");
		Log_Button.setBounds(10, 57, 100, 22);
		frame.getContentPane().add(Log_Button);
	}
	
	public void replace(String post) throws FileNotFoundException
	{	
		 File file = new File("chat" + CURRENT_GROUP + ".txt");
		 Scanner sc = new Scanner(file);
		 ArrayList<String> history = new ArrayList<>();
		 while(sc.hasNextLine())
		 {
			 history.add(sc.nextLine());
		 }
		 FileWriter fw;
		try {
			fw = new FileWriter(file);
			PrintWriter bw = new PrintWriter(fw);
			for(int i =0; i<history.size();i++)
			{
				bw.write(history.get(i)+"\n");
			}
			fw.write(post+"\n");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	}
