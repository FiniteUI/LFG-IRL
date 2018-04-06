import java.sql.*;

public class AddReview {
	public static void addReview(Connection conn, int reviewee, int newRating) {
		ResultSet rs = null;
		int rating;
		int ratingNumber;
		String sql = "select rating, ratingnumber from Accounts where userid = " + reviewee;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			rating = rs.getInt(1);
			ratingNumber = rs.getInt(2);
			rs.close();
			/*
			if (ratingNumber == 0)
				ratingNumber = 1;
			*/
			if (rating == 0)
				rating = 5;
			newRating = (rating * ratingNumber + newRating) / ++ratingNumber;
			
			sql = "update Accounts set rating = " + newRating + ", ratingnumber = " + ratingNumber + " where userid = " + reviewee;
			stmt.execute(sql);
			stmt.close();
			conn.commit();
		}//end try
		catch (Exception e) {
			e.printStackTrace();
		}//end catch
	}//end addReview
}////end AddReview
