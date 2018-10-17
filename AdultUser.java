package application.network;

import java.util.HashSet;

import application.exception.NoAvailableException;
import application.exception.NoSuchAgeException;
import application.exception.NotToBeClassmatesException;
import application.exception.NotToBeColleaguesException;
import application.exception.NotToBeCoupledException;
import application.exception.NotToBeFriendsException;
import application.exception.RelationshipException;

public class AdultUser extends User {

	public AdultUser(String name) {
		super(name);
	}

	@Override
	public void setAge(int age) throws NoSuchAgeException {
		if (age > 16)
			this.age = age;
		else
			throw new NoSuchAgeException("Age can not be less than 16");
	}

	@Override
	public void addConnection(ConnectionType connectionType, User anotherUser)
			throws RelationshipException {
		if (connectionType == ConnectionType.NONE) {
			for (ConnectionType type : connections.keySet()) {
				if (type == ConnectionType.CHILD)
					throw new RelationshipException(
							"Child "
									+ anotherUser
									+ " exists for "
									+ this
									+ ".\nRemove child or modify child relationship first");
				if (connections.get(type).contains(anotherUser))
					this.removeConnection(type, anotherUser);
			}
			return;
		}

		if (connectionType.equals(ConnectionType.PARENT))
			throw new RelationshipException(this + " can't have parents");

		if (connectionType.equals(ConnectionType.CHILD)) {
			if (anotherUser instanceof AdultUser)
				throw new RelationshipException("User " + this
						+ " is not child");

			if (getAllConnections(ConnectionType.CHILD) != null
					&& !getAllConnections(ConnectionType.CHILD).isEmpty())
				throw new RelationshipException("User " + this
						+ " already has a child");
			else {
				connections.putIfAbsent(connectionType, new HashSet<>());
				connections.get(connectionType).add(anotherUser);
				connectionType = ConnectionType.PARENT;
			}
		}

		if (connectionType.equals(ConnectionType.COLLEGUE)) {
			if (!(anotherUser instanceof AdultUser))
				throw new NotToBeColleaguesException("Child/Young Child user "
						+ anotherUser + " can't be collegue");
			connections.putIfAbsent(connectionType, new HashSet<>());
			connections.get(connectionType).add(anotherUser);
		}

		if (!(connectionType.equals(ConnectionType.CLASSMATE) && anotherUser instanceof ChildUser)) {
			if (!(anotherUser instanceof AdultUser))
				throw new NotToBeClassmatesException("Young Child/child user "
						+ anotherUser + " can't be classmate with "
						+ anotherUser);
			connections.putIfAbsent(connectionType, new HashSet<>());
			connections.get(connectionType).add(anotherUser);
		}

		if (connectionType.equals(ConnectionType.COUPLE)) {
			if (!(anotherUser instanceof AdultUser))
				throw new NotToBeCoupledException(
						"Connection can't be couple because user "
								+ anotherUser + " is also Child/Young child.");
			if (getAllConnections(ConnectionType.COUPLE) != null
					&& getAllConnections(ConnectionType.COUPLE).size() <= 1) {
				connections.putIfAbsent(connectionType, new HashSet<>());
				connections.get(connectionType).add(anotherUser);
			} else {
				throw new NoAvailableException("Already have couple");
			}

		}

		if (ConnectionType.FRIEND.equals(connectionType)) {
			if (anotherUser instanceof ChildUser
					|| anotherUser instanceof YoungChild)
				throw new NotToBeFriendsException("user " + anotherUser
						+ " can't be friend with Adult User " + this + ".");

			connections.putIfAbsent(connectionType, new HashSet<>());
			connections.get(connectionType).add(anotherUser);
		}

		anotherUser.getAllConnections().putIfAbsent(connectionType,
				new HashSet<>());
		anotherUser.getAllConnections().get(connectionType).add(this);
	}

	@Override
	public void removeConnection(ConnectionType connectionType, User anotherUser) {
		if (!this.getAllConnections().containsKey(connectionType))
			return;

		connections.get(connectionType).remove(anotherUser);
		if (connections.get(connectionType).isEmpty())
			connections.remove(connectionType);
		if (connectionType.equals(ConnectionType.CHILD))
			connectionType = ConnectionType.PARENT;

		if (anotherUser.getAllConnections().get(connectionType) != null) {
			anotherUser.getAllConnections().get(connectionType).remove(this);
			if (anotherUser.getAllConnections().get(connectionType).isEmpty())
				anotherUser.getAllConnections().remove(connectionType);
		}
	}
}
