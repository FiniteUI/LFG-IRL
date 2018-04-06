
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//gets the top categories for display
public class GetCategories {
	
	public static String[] topCategoriesStrings(Connection conn) {
		
		ResultSet rs = null;
		String[] categories = new String[6];
		String selectStatement = "Select category, count(category) from Groups group by category order by count(category) desc";
		//connect();
		try {
			Statement select = conn.createStatement();
			rs = select.executeQuery(selectStatement);
			for(int i = 0; i < 6; i++){
				if (!rs.next())
					break;
				categories[i] = rs.getString(1) + ", " + rs.getString(2);
			}//end for
		}//end try
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}//end catch
		finally {
			//closeDB();
		}//end finally
		return categories;
	}//end topCategories
}//end class
