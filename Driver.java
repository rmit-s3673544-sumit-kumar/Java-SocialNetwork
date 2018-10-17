package application.network;

import application.db.DataFetcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import application.db.TextDataFetcher;
import application.exception.NoSuchAgeException;
import application.exception.RelationshipException;

public class Driver {
	private static Set<User> network;
	private static DataFetcher fetcher;

	static {
		fetcher = new TextDataFetcher();
		try {
			network = fetcher.fetchAllUsersData();
		} catch (NoSuchAgeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fetcher.initRelationship();
	}

	/**
	 * @return the network
	 */
	public Set<User> getNetwork() {
		return network;
	}

	public static User createUser(String name, int age)
			throws NoSuchAgeException {
		User user = null;
		if (age > 0)
			if (age > 16)
				user = new AdultUser(name);
			else
				user = new ChildUser(name);
		else
			throw new NoSuchAgeException("Age can't be less than zero");

		user.setAge(age);
		return user;
	}

	public void addPerson(User user) {
		if (user.getAge() > 0)
			network.add(user);
	}

	public void addPerson(String name, int age) throws NoSuchAgeException {
		User user = createUser(name, age);
		if (user instanceof ChildUser
				&& user.getAllConnections(ConnectionType.PARENT).size() != 2)
			throw new NoSuchAgeException("Add parents first, Parents: "
					+ user.getAllConnections(ConnectionType.PARENT));
		addPerson(user);
	}

	public void deletePerson(User user) throws OperationNotSupportedException {
		if (user.getAllConnections(ConnectionType.CHILD) != null
				&& !user.getAllConnections(ConnectionType.CHILD).isEmpty())
			throw new OperationNotSupportedException(
					"Remove Child First, Child is: "
							+ user.getAllConnections(ConnectionType.CHILD));

		Map<ConnectionType, Set<User>> connections = new HashMap<>(
				user.connections);
		for (ConnectionType type : connections.keySet()) {
			for (User anotherUser : new HashSet<>(connections.get(type)))
				user.removeConnection(type, anotherUser);
		}
		network.remove(user);
	}

	/**
	 * setting relationship read as NAME2 is a CONNECTION_TYPE of NAME1 user2 is
	 * a connectionType of user1
	 * 
	 * @param user1
	 * @param user2
	 * @param connectionType
	 *            type of connection
	 * @throws RelationshipException
	 */
	public static void connectPeople(User user1, User user2,
			ConnectionType connectionType) throws RelationshipException {
		user1.addConnection(connectionType, user2);
	}

	/**
	 * setting relationship read as NAME2 is a CONNECTION_TYPE of NAME1 user2 is
	 * a connectionType of user1
	 * 
	 * @param name1
	 * @param name2
	 * @param connectionType
	 *            type of connection
	 * @throws RelationshipException 
	 */
	public static void connectPeople(String name1, String name2,
			ConnectionType connectionType) throws RelationshipException {
		User user1 = getUserFromName(name1);
		User user2 = getUserFromName(name2);
		user1.addConnection(connectionType, user2);

	}

	public boolean isDirectConnected(String name1, String name2) {
		User user = getUserFromName(name1);
		User anotherUser = getUserFromName(name2);
		return isDirectConnected(user, anotherUser);
	}

	public boolean isDirectConnected(User user, User anotherUser) {
		for (ConnectionType type : user.connections.keySet()) {
			if (user.connections.get(type).contains(anotherUser))
				return true;
		}
		return false;
	}

	public List<String> getChild(User user) {
		List<String> names = new ArrayList<>();
		for (User child : new ArrayList<>(
				user.getAllConnections(ConnectionType.CHILD))) {
			names.add(child.getName());
		}
		return names;
	}

	public List<String> getChild(String name)
			throws OperationNotSupportedException {
		User user = getUserFromName(name);
		if (user instanceof ChildUser)
			throw new OperationNotSupportedException("Child user " + user
					+ "can't have child");
		return getChild(user);
	}

	public List<String> getParents(String name)
			throws OperationNotSupportedException {
		User user = getUserFromName(name);
		if (user instanceof AdultUser)
			throw new OperationNotSupportedException("Adult user " + user
					+ "can't have parent");
		return getParents(user);
	}

	public List<String> getParents(User user) {
		List<String> names = new ArrayList<>();
		for (User parent : new ArrayList<>(
				user.getAllConnections(ConnectionType.PARENT))) {
			names.add(parent.getName());
		}
		return names;
	}

	public void displayProfile(User user) {
		System.out.println("User Profile is:");
		System.out.println("Name: " + user.getName());
		System.out.println("Age: " + user.getAge());
		System.out.println("Status: " + user.getStatus());
		System.out.println("Profile Pic: " + user.getUserImage());
	}

	public static User getUserFromName(String name) {
		Iterator<User> itr = network.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			if (user.getName().equals(name))
				return user;
		}
		throw new RuntimeException("User with name: " + name
				+ " is not in network. Plase add first");
	}

	public List<String> displayAllUsers() {
		List<String> names = new ArrayList<>();
		Iterator<User> itr = getNetwork().iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			names.add(user.getName());
		}
		return names;
	}

	public List<String> displayAllUsers(ConnectionType type) {
		List<String> names = new ArrayList<>();
		Iterator<User> itr = getNetwork().iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			if (user.connections.containsKey(type))
				names.add(user.getName());
		}
		return names;
	}
}
