package application.network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import application.exception.NoSuchAgeException;
import application.exception.RelationshipException;

public abstract class User {
	protected String name;
	protected int age;
	protected String status;
	protected String userImage;
	protected Map<ConnectionType, Set<User>> connections;
	protected String address;
	protected String gender;

	protected User(String name) {
		this.name = name.trim();
		this.connections = new HashMap<>();
		this.status = "";
		this.userImage = "";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public abstract void setAge(int age) throws NoSuchAgeException;

	/**
	 * Add user connection to another user
	 * 
	 * @param connectionType
	 *            of other user
	 * @param anotherUser
	 *            user to connect
	 */
	public abstract void addConnection(ConnectionType connectionType,
			User anotherUser) throws RelationshipException;

	/**
	 * remove user connection to another user
	 * 
	 * @param connectionType
	 *            of other user
	 * @param anotherUser
	 *            user to remove connection
	 */
	public abstract void removeConnection(ConnectionType connectionType,
			User anotherUser);

	/**
	 * update user connection to another user
	 * 
	 * @param connectionType
	 *            of other user to update
	 * @param anotherUser
	 *            user to connect
	 */
	public void updateConnection(ConnectionType connectionType, User anotherUser)
			throws RelationshipException {
		this.removeConnection(connectionType, anotherUser);
		this.addConnection(connectionType, anotherUser);
	}

	/**
	 * @param user
	 * @return Connection to the user
	 */
	public ConnectionType getConnection(User user) {
		Map<ConnectionType, Set<User>> allConnections = getAllConnections();
		for (ConnectionType type : allConnections.keySet()) {
			if (allConnections.get(type).contains(user))
				return type;
		}
		return ConnectionType.NONE;
	}

	/**
	 * get users for this type of connection
	 * 
	 * @param connectionType
	 *            of other user to update
	 * @return set of users
	 */
	public Set<User> getAllConnections(ConnectionType connectionType) {
		Set<User> set = connections.get(connectionType);
		return set == null ? new HashSet<>() : set;
	}

	/**
	 * @return the connections
	 */
	public Map<ConnectionType, Set<User>> getAllConnections() {
		return connections;
	}

	/**
	 * @return user status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * user status to set
	 * 
	 * @param status
	 *            *
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the user image
	 */
	public String getUserImage() {
		return userImage;
	}

	/**
	 * sets user image
	 * 
	 * @param userImage
	 */
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s(age=%s, status=%s)", name, age, status);
	}
}
