

import java.sql.*;
public class GetMembers {
	
	//get a list of members for a specific group
	public static ResultSet getMembers(Connection conn, int GID) {
		String getStmt = "select userid from Members where groupid = " + GID;
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(getStmt);
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		return rs;
	}//end getMembers
	
	//get info for a specific member
	public static ResultSet getMemberInfo(Connection conn, int UID) {
		String getStmt = "select * from Accounts where userid = " + UID;
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(getStmt);
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		return rs;
	}//end get Member info
	
	//returns the id of the owner of the given group
	public static int getOwner(Connection conn, int GID) {
		String getStmt = "select userid from Owner where groupid = " + GID;
		ResultSet rs = null;
		int owner = -1;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(getStmt);
			if (rs.next())
				owner =  rs.getInt(1);
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		return owner;
	}//end getOwner
	
	//returns the username of the account with the given id
	public static String getUserName(Connection conn, int UID) {
		ResultSet rs = getMemberInfo(conn, UID);
		String username = null;
		try {
			rs.next();
			username = rs.getString("username");
		}//end try
		catch (Exception e3) {
			e3.printStackTrace();
		}//end catch
		return username;
	}//end getUserName
}//end GetMembers
