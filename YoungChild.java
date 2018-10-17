package application.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.exception.NoParentException;
import application.exception.NoSuchAgeException;
import application.exception.NotToBeClassmatesException;
import application.exception.NotToBeColleaguesException;
import application.exception.NotToBeCoupledException;
import application.exception.RelationshipException;
import application.exception.TooYoungException;

public class YoungChild extends User {

    protected YoungChild(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setAge(int age) throws NoSuchAgeException {
        if (age <= 2 && age > 0) {
            this.age = age;
        } else {
            throw new NoSuchAgeException(
                    "Young child age should be between 0-2");
        }

    }

    @Override
    public void addConnection(ConnectionType connectionType, User anotherUser)
            throws RelationshipException {
        if (connectionType == ConnectionType.NONE) {
            Map<ConnectionType, Set<User>> connection = new HashMap<>(
                    connections);
            for (ConnectionType type : connection.keySet()) {
                if (connection.get(type).contains(anotherUser)) {
                    this.removeConnection(type, anotherUser);
                }
            }
            return;
        }
        if (connectionType.equals(ConnectionType.COLLEGUE)) {
            throw new NotToBeColleaguesException("Young Child user " + this
                    + " can't be collegue");
        }

        if (!(connectionType.equals(ConnectionType.CLASSMATE) && anotherUser instanceof ChildUser)) {
            throw new NotToBeClassmatesException("Young Child user " + this
                    + " can't be classmate with " + anotherUser);
        }

        if (connectionType.equals(ConnectionType.COUPLE)) {
            throw new NotToBeCoupledException(
                    "Connection can't be couple because this user " + this
                    + " is also Young child.");
        }

        if (connectionType.equals(ConnectionType.CHILD)) {
            throw new RelationshipException(
                    "Connection can't be child because this user " + this
                    + " is also Young child.");
        }

        if (connectionType.equals(ConnectionType.PARENT)) {
            if (anotherUser instanceof ChildUser) {
                throw new RelationshipException("User " + this
                        + " is not adult.");
            }

            if (getAllConnections(ConnectionType.PARENT) != null
                    && getAllConnections(ConnectionType.PARENT).size() >= 2) {
                throw new RelationshipException("This User " + this
                        + " already have parents "
                        + connections.get(ConnectionType.PARENT));
            }
//			if (getAllConnections(ConnectionType.PARENT) != null
//					&& getAllConnections(ConnectionType.PARENT).size() <= 2)
//				throw new NoParentException("This User " + this
//						+ "has less then two parent "
//						+ connections.get(ConnectionType.PARENT));
            if (connections.get(ConnectionType.PARENT) != null && connections.get(ConnectionType.PARENT).size() == 2) {
                List<User> parents = new ArrayList<>(
                        connections.get(ConnectionType.PARENT));

                if (!parents.get(0).getConnection(parents.get(1))
                        .equals(ConnectionType.COUPLE)) {
                    throw new NoParentException("This User's " + this
                            + "parent are not couple "
                            + connections.get(ConnectionType.PARENT));
                }
            } else {
                connections.putIfAbsent(connectionType, new HashSet<>());
                connections.get(connectionType).add(anotherUser);
                connectionType = ConnectionType.CHILD;
                anotherUser.getAllConnections().putIfAbsent(connectionType,
                        new HashSet<>());
                anotherUser.getAllConnections().get(connectionType).add(this);
            }
        }

        if (connectionType.equals(ConnectionType.FRIEND)) {
            throw new TooYoungException("Young child can't have friends");
        }

    }

    @Override
    public void removeConnection(ConnectionType connectionType, User anotherUser) {
        if (!this.getAllConnections().containsKey(connectionType)) {
            return;
        }

        connections.get(connectionType).remove(anotherUser);
        if (connections.get(connectionType).isEmpty()) {
            connections.remove(connectionType);
        }

        if (connectionType.equals(ConnectionType.PARENT)) {
            connectionType = ConnectionType.CHILD;
        }

        if (anotherUser.getAllConnections().get(connectionType) != null) {
            anotherUser.getAllConnections().get(connectionType).remove(this);
            if (anotherUser.getAllConnections().get(connectionType).isEmpty()) {
                anotherUser.getAllConnections().remove(connectionType);
            }
        }

    }

}
