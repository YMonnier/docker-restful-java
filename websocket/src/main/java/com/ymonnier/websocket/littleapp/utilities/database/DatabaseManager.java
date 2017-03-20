package com.ymonnier.websocket.littleapp.utilities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class DatabaseManager.
 */
public class DatabaseManager {

    /**
     * The Constant freeConnections.
     */
    private static final Queue<Connection> freeConnections = new LinkedList<Connection>();

    /**
     * The Constant numberOfInitialConnections.
     */
    private static final int numberOfInitialConnections = 5;

    /**
     * The Constant url.
     */
    private static final String url = "jdbc:postgresql://database:5432/apirest";

    /**
     * The Constant user.
     */
    private static final String user = "root";

    /**
     * The Constant password.
     */
    private static final String password = "root";

    static {
        for (int i = 0; i < numberOfInitialConnections; i++) {
            try {
                freeConnections.add(DriverManager.getConnection(url, user,
                        password));
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException the SQL exception
     */
    public static synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        if (freeConnections.isEmpty()) {
            connection = DriverManager.getConnection(url, user, password);
        } else {
            connection = freeConnections.remove();
        }
        return connection;

    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    public static synchronized void releaseConnection(Connection connection) {
        if (freeConnections.size() < numberOfInitialConnections) {
            freeConnections.add(connection);
        } else {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}