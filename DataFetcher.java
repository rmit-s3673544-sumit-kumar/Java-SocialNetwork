package application.db;

import java.util.Set;

import application.exception.NoSuchAgeException;
import application.network.User;

public interface DataFetcher  {
	public Set<User> fetchAllUsersData() throws NoSuchAgeException;
	public User fetchUserData(String name);
	public User fetchUserData(String name, String age);
	public void initRelationship();
}
