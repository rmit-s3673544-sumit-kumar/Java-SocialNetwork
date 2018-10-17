package application.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
	
private static Connection con;

	 private static void getConnection() throws Exception, SQLException {
		  // sqlite driver
		  Class.forName("org.sqlite.JDBC");
		  // database path, if it's new database, it will be created in the project folder
		  con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest.db");
		  initialise();
	 }
	 private static void initialise() throws Exception {
		 BufferedReader br = new BufferedReader(new FileReader(new File("src/people.txt")));
		 con.prepareStatement("Drop table people;").execute();
			 con.prepareStatement(
						"create table people(name varchar(20) not null, photo blob, status varchar(60) not null, gender varchar(10) not null, age integer not null, state varchar(40) not null);")
						.execute();
		
			 String line = br.readLine();
				while (line != null) {
					String[] data = line.split(",");
					PreparedStatement pstmt = con.prepareStatement("insert into people values(?,?,?,?,?,?)");
					pstmt.setString(1, data[0].trim());
					pstmt.setString(2, data[1].trim());
					pstmt.setString(3, data[2].trim());
					pstmt.setString(4, data[3].trim());
					pstmt.setInt(5, Integer.parseInt(data[4].trim()));
					pstmt.setString(6, data[5].trim());
					pstmt.execute();
					line = br.readLine();
				}
				br.close();
	 
				
				br = new BufferedReader(new FileReader(new File("src/relation.txt")));
				con.prepareStatement("Drop table relation;").execute();
				con.prepareStatement(
						"create table relation(name1 varchar(60) not null, name2 varchar(60) not null, relationship varchar(45) not null);")
						.execute();
				line = br.readLine();
				while (line != null) {
					String[] data = line.split(",");
					PreparedStatement pstmt = con.prepareStatement("insert into relation values(?,?,?)");

					pstmt.setString(1, data[0].trim());
					pstmt.setString(2, data[1].trim());
					pstmt.setString(3, data[2].trim());
					pstmt.execute();
					line = br.readLine();
				}
				br.close();

				
				
		 }
	 }

