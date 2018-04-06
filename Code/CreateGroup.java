
import java.sql.Connection;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//creates a new group entry in the database, and updates other tables accordingly
public class CreateGroup {
	
	//create the group entry in the group table, add the owner to owner and member tables
	public static void createGroup(Connection conn, int ownerID, String name, String category, String description, String date_time, String location, int max_members, int zipcode) {
		
		int GID = 0;//the group id
		String insertStatement = "Insert into Groups(name, category, description, date, city, maxmembers, groupid, zipcode) values(?,?,?,?,?,?,?,?)";//the statement to be executed
		//connect();
		
		try{
			
			//fetch the highest ID to generate the next ID
			String gidStatement = "Select MAX(groupid) from Groups";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(gidStatement);
			if (rs.next()){
				GID = rs.getInt(1);
				GID++;
			}//end if
			
			//insert the new group
			PreparedStatement ps = conn.prepareStatement(insertStatement);
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, description);
			ps.setString(4, date_time);
			ps.setString(5, location);//potentially expand location
			ps.setInt(6, max_members);
			ps.setInt(7, GID);
			ps.setInt(8, zipcode);
			ps.execute();
			
			//add the owner and group to the Owner relation
			insertStatement = "Insert into Owner(groupid, userid) values(?,?)";
			ps = conn.prepareStatement(insertStatement);
			ps.setInt(1, GID);
			ps.setInt(2, ownerID);
			ps.execute();
			
			//add the owner and group to the member relation
			insertStatement = "Insert into Members(groupid, userid) values(?,?)";
			ps = conn.prepareStatement(insertStatement);
			ps.setInt(1, GID);
			ps.setInt(2, ownerID);
			ps.execute();
			
			//if we've made it this far, commit
			conn.commit();
			
			//create new chat file
			OnlineChat.createChat(GID);
			
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		
		//create chat file
		//later input this to database, for now just make file
		try {
			PrintWriter w = new PrintWriter("Chat" + GID + ".txt");
			w.print("");
			w.close();
		}//end try
		catch(Exception e){
			System.out.println(e.getMessage());
		}//end catch
	}//end createGroup	
	
	//creates a group and returns the group id
	public static int createGroupRetGID(Connection conn, int ownerID, String name, String category, String description, String date_time, String location, int max_members, int zipcode) {
		
		int GID = 0;//the group id
		String insertStatement = "Insert into Groups(name, category, description, date, city, maxmembers, groupid, zipcode) values(?,?,?,?,?,?,?,?)";//the statement to be executed
		//connect();
		
		try{
			
			//fetch the highest ID to generate the next ID
			String gidStatement = "Select MAX(groupid) from Groups";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(gidStatement);
			if (rs.next()){
				GID = rs.getInt(1);
				GID++;
			}//end if
			
			//insert the new group
			PreparedStatement ps = conn.prepareStatement(insertStatement);
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, description);
			ps.setString(4, date_time);
			ps.setString(5, location);//potentially expand location
			ps.setInt(6, max_members);
			ps.setInt(7, GID);
			ps.setInt(8, zipcode);
			ps.execute();
			
			//add the owner and group to the Owner relation
			insertStatement = "Insert into Owner(groupid, userid) values(?,?)";
			ps = conn.prepareStatement(insertStatement);
			ps.setInt(1, GID);
			ps.setInt(2, ownerID);
			ps.execute();
			
			//add the owner and group to the member relation
			insertStatement = "Insert into Members(groupid, userid) values(?,?)";
			ps = conn.prepareStatement(insertStatement);
			ps.setInt(1, GID);
			ps.setInt(2, ownerID);
			ps.execute();
			
			//if we've made it this far, commit
			conn.commit();
			
			//create new chat file
			OnlineChat.createChat(GID);
			
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		return GID;
	}//end createGroup	
}//end class