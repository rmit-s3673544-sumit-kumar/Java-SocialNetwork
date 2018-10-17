/**
 * 
 */
package application.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import application.exception.NoSuchAgeException;
import application.exception.RelationshipException;
import application.network.ConnectionType;
import application.network.Driver;
import application.network.User;

public class TextDataFetcher implements DataFetcher {

	/*
	 * (non-Javadoc)
	 * 
	 * @see rmit.social.network.DateFetcher#fetchData()
	 */

	@Override
	public Set<User> fetchAllUsersData() throws NumberFormatException,
			NoSuchAgeException {
		Set<User> users = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				"people.txt")))) {
			String st;
			while ((st = br.readLine()) != null) {
				String[] data = st.split(",");
				User user = Driver.createUser(data[0].trim(),
						Integer.parseInt(data[4].trim()));
				user.setStatus(data[2].trim());
				user.setUserImage(data[1].trim());
				user.setAddress(data[5].trim());
				user.setGender(data[3].trim());
				users.add(user);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// users.add(Driver.createUser("Sumit", 25));
		return users;
	}

	@Override
	public User fetchUserData(String name) {
		return null;

	}

	@Override
	public User fetchUserData(String name, String age) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initRelationship() {
		// TODO Auto-generated method stub
		// setting relationship read as NAME2 is a CONNECTION_TYPE of NAME1
		// Sachin[name2] is a friend of Sumit[name1]
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				"relation.txt")))) {
			String st;
			while ((st = br.readLine()) != null) {
				String[] data = st.split(",");
				ConnectionType connection = ConnectionType.valueOf(data[2].trim().toUpperCase());
				Driver.connectPeople(data[1].trim(), data[0].trim(), connection);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
