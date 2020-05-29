package csci310;
import java.util.ArrayList;
import java.sql.*;

/**
 * Servlet implementation class Login
 */
public class Fav {
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/weather?"
			+ "cloudSqlInstance=weatherapp-272900:us-west2:maindb&socketFactory=com.google.cloud.sql.mysql.SocketFactory"
			+ "&useSSL=false&requireSSL=false&user=grouph&password=password";
	static Connection connection = null;
	Statement st = null;
	ResultSet rs = null;
	
	/* SQL TABLES
	 * FavoriteCityInfo;
		+----------+-------------+------+-----+---------+-------+
		| Field    | Type        | Null | Key | Default | Extra |
		+----------+-------------+------+-----+---------+-------+
		| citiName | varchar(50) | YES  |     | NULL    |       |
		| count    | int(11)     | YES  |     | NULL    |       |
		| country  | varchar(50) | YES  |     | NULL    |       |
		| lat      | varchar(50) | YES  |     | NULL    |       |
		| lng      | varchar(50) | YES  |     | NULL    |       |
		+----------+-------------+------+-----+---------+-------+
	 * FavoriteCities;
		+----------+-------------+------+-----+---------+-------+
		| Field    | Type        | Null | Key | Default | Extra |
		+----------+-------------+------+-----+---------+-------+
		| username | varchar(50) | NO   |     | NULL    |       |
		| cityName | varchar(50) | NO   |     | NULL    |       |
		+----------+-------------+------+-----+---------+-------+
	 */
	
	public Fav(){
		System.out.println("in FavServlet");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("pass Class.forName");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			
		}catch(Exception e) {
			String msg = e.getMessage();
			System.out.println(msg);
			System.out.println("Error in connection JDBC");
		}
	}

	public void addFav(String username, String cityname, String country, String lat, String lng) throws SQLException {
		//add an entry to FavoriteCities list: 
		st = connection.createStatement();
		st.executeUpdate("INSERT INTO FavoriteCities (username, cityName)VALUES ('"+username+"','"+cityname+"')");

		//update FavCityCount list
		int count = -1;
		//check if city is in FavCity List
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM FavoriteCityInfo WHERE citiName ='" + cityname + "'");
		if(!rs.next()) {
			//city not in the list, add an entry 
			st = connection.createStatement();
			st.executeUpdate("INSERT INTO FavoriteCityInfo (citiName, count, country, lat, lng"
					+ ")VALUES ('"+cityname+"','"+1+"','"+country+"','"+lat+"','"+lng+"')");
		}
		else {
			//city in the list, get the current count, insert count+1
			count = rs.getInt("count");
			count++;
			st = connection.createStatement();
			st.executeUpdate("UPDATE FavoriteCityInfo "
					+ "SET count = '"+count+"'"
							+ "WHERE citiName = '"+cityname+"'");
		}
		
	}
	
	// returns the number of likes a certain city has. 
	public int getLikeCount(String cityname, String username) throws SQLException{
		//update FavCityCount list
		
		//check if city is in FavCity List
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM Likes WHERE city ='" + cityname + "' AND username = '" + username + "';");
		if(!rs.next()) {
			//city not in the list, add an entry 
			st = connection.createStatement();
			st.executeUpdate("INSERT INTO Likes (username, city, count) VALUES ('" +username+"','" + cityname + "', 0);");
			return 0;
		}
		else {
			//city in the list, get the current count, insert count+1
			int count = -1;
			count = rs.getInt("count");
			System.out.println("The count for city: " + cityname + " was: " + count);
			return count;
		}
	}
	
	// returns the number of likes that city has after the increment
	public int incrementLikes(String cityname, String username, int count) throws SQLException {
		
		//update FavCityCount list
		//int count = -1;
		//check if city is in FavCity List
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM FavoriteCityInfo WHERE citiName ='" + cityname + "'");
		if(!rs.next()) {
			//city not in the list, add an entry 
			st = connection.createStatement();
			st.executeUpdate("INSERT INTO Likes (username, city, count) VALUES ('" +username+"','" + cityname + "', 1);");
			return 1;
			
		}
		else {
			//city in the list, get the current count, insert count+1
			st = connection.createStatement();
			st.executeUpdate("UPDATE Likes "
					+ "SET count = '"+count+"'"
							+ "WHERE city = '"+cityname+"' AND username = '" + username + "';");
			return count;
		}
	}
	
	public void removeFav(String username, String cityname) throws SQLException {
		//add an entry to FavouriteCities list: 
		st = connection.createStatement();
		st.executeUpdate("DELETE FROM FavoriteCities WHERE username = '"+username+"'AND cityName='"+cityname+"'");

		//update FavCityCount list
		int count = -1;
		//check if city is in FavCity List
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM FavoriteCityInfo WHERE citiName ='" + cityname + "'");
		if(!rs.next()) {
			//city not in the list, add an entry 
			System.out.println("removeFav but city does not exist in cityCount");
		}
		else {
			//city in the list, get the current count, insert count+1
			count = rs.getInt("count");
			count--;
			if(count != 0) {
				//minus one from cityCount
				st = connection.createStatement();
				st.executeUpdate("UPDATE FavoriteCityInfo "
						+ "SET count = '"+count+"'"
								+ "WHERE citiName = '"+cityname+"'");
			}
			else {
				//delete the entry if count is down to 0
				st = connection.createStatement();
				st.executeUpdate("DELETE FROM FavoriteCityInfo WHERE citiName = '"+cityname+"'");
			}
		}
	}

	public boolean isFav(String username, String cityname) throws SQLException{
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM FavoriteCities WHERE username = '"+username+"'AND cityName='"+cityname+"'");
		if(rs.next()) return true;
		else return false;
	}
	public boolean isFavEmpty(String username) throws SQLException{
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM FavoriteCities WHERE username = '"+username+"'");
		if(rs.next()) return true;
		else return false;
	}
	public ArrayList<CityObject> getFavCities(String username) throws SQLException{
		ArrayList<String> cityNames = new ArrayList<String>();
		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM FavoriteCities WHERE username = '"+username+"'");
		while(rs.next()) {
			cityNames.add(rs.getString("cityName"));
			System.out.println(rs.getString("cityName"));
		}
		ArrayList<CityObject> result = new ArrayList<CityObject>();
		for(int i=0; i<cityNames.size(); i++) {
			st = connection.createStatement();
			rs = st.executeQuery("SELECT * FROM FavoriteCityInfo WHERE citiName = '"+cityNames.get(i)+"'");
			if(rs.next()) {
				String cityName = cityNames.get(i);
				String country = rs.getString("country");
				String lat = rs.getString("lat");
				String lng = rs.getString("lng");
				CityObject co = new CityObject(cityName, country, lat, lng);
				System.out.println(co);
				result.add(co);
			}
		}
		return result;
	}
}
