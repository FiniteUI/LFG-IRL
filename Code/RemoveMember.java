
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//removes a member from a group
public class RemoveMember {
	
	//remove a member from the Members relation
	public static void removeMember(Connection conn, int Member, int Group){
		
		String removeStatement = "Delete from Members where groupid = ? and userid = ?";
		//connect();
		try {
			
			//insert it
			PreparedStatement ps = conn.prepareStatement(removeStatement);
			ps.setInt(1, Group);
			ps.setInt(2, Member);
			ps.execute();
			
			//if no problems, commit it
			conn.commit();
		}//end try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		finally {
			//closeDB();
		}//end finally
		
	}//end removeMember
}//end class