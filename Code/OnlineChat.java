

import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

public class OnlineChat {
	private static final String FTP_SERVER = "ftp.rseddon.heliohost.org";
	private static final String FTP_USER =  "chat@rseddon.heliohost.org";
	private static final String FTP_USER_THREAD = "chatrefresh@rseddon.heliohost.org";
	private static final String FTP_PASSWORD_THREAD = "chatrefresh";
	private static final int FTP_PORT = 21;
	private final static String FTP_PASSWORD = "";
	private static FTPClient client;
	private static FTPClient threadClient;

	private static void connect() {
		client = new FTPClient();
		try {
			client.connect(FTP_SERVER, FTP_PORT);
			client.login(FTP_USER, FTP_PASSWORD);
			client.enterLocalPassiveMode();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end connect

	public static void connectThread() {
		threadClient = new FTPClient();
		try {
			threadClient.connect(FTP_SERVER, FTP_PORT);
			threadClient.login(FTP_USER, FTP_PASSWORD);
			threadClient.enterLocalPassiveMode();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end connectThread

	private static void disconnectThread() {
		try {
			threadClient.logout();
			threadClient.disconnect();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end disconnect

	private static void disconnect() {
		try {
			client.logout();
			client.disconnect();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end disconnect

	private static void download(int GID) {
		try {
			File file = new File("currentChat.txt");
			file.createNewFile();
			OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
			client.retrieveFile("chat" + GID + ".txt", os);
			os.flush();
			os.close();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end download

	private static void upload(int GID) {
		File file = new File("currentChat.txt");
		try {
			InputStream is = new FileInputStream(file);
			client.storeFile("chat" + GID + ".txt", is);
			is.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//end upload

	public static void edit(int GID, String line) {
		connect();
		download(GID);
		File file = new File ("currentChat.txt");
		try{
			PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
			pw.append("\n" + line);
			pw.flush();
			pw.close();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
		upload(GID);
		file.delete();
		disconnect();
	}//end edit

	public static void initialDownload(int GID){
		connectThread();
		try {
			File file = new File("currentChatThread.txt");
			file.createNewFile();
			OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
			threadClient.retrieveFile("chat" + GID + ".txt", os);
			os.flush();
			os.close();
		}//end try
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end initialDownload


	public static void idClose(){
		disconnect();
		File file = new File("currentChat.txt");
		file.delete();
	}//end idClose()


	public static void threadIDClose() {
		disconnectThread();
		File file = new File("currentChatThread.txt");
		file.delete();
	}

	//creates a new empty chat file
	public static void createChat(int GID) {
		connect();
		File file = new File("currentChat.txt");
		try {
			file.createNewFile();
		}//end try
		catch (Exception e) {
			e.printStackTrace();
		}//end catch
		upload(GID);
		file.delete();
		disconnect();
	}//end createChat

}//end onlinechat
