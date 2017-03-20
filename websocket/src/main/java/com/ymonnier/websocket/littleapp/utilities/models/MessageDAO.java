package com.ymonnier.websocket.littleapp.utilities.models;

import com.ymonnier.websocket.littleapp.utilities.database.DAO;
import com.ymonnier.websocket.littleapp.utilities.database.exceptions.InsertionException;
import org.glassfish.grizzly.Grizzly;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.utilities.models.
 * File MessageDAO.java.
 * Created by Ysee on 19/03/2017 - 22:06.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class MessageDAO extends DAO<Message> {
    private static final Logger LOGGER = Grizzly.logger(MessageDAO.class);
    public static final String TABLE_NAME = "messages";
    public static final String TABLE_NAME_CHANNEL = "channels_messages";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_MESSAGES_ID = "messages_id";
    public static final String COLUMN_USER_ID = "user_id";

    private static final String INSERT_MESSAGE = "INSERT INTO " + TABLE_NAME + " ( "
            + COLUMN_CONTENT + ","
            + COLUMN_CHANNEL_ID + ","
            + COLUMN_USER_ID + ","
            + COLUMN_DATE + ") VALUES(?, ?, ?, ?)";

    private static final String INSERT_CHANNEL_MESSAGE = "INSERT INTO " + TABLE_NAME_CHANNEL + " ( "
            + COLUMN_CHANNEL_ID + ","
            + COLUMN_MESSAGES_ID + ") VALUES(?, ?)";

    public MessageDAO(Connection connection) {
        super(connection);
    }


    @Override
    public boolean create(Message obj) throws InsertionException {
        boolean res = false;
        assert obj != null;
        if (obj == null)
            throw new IllegalArgumentException("The object should not be null.");

        try (PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE, new String[]{"id"})) {
            ps.setString(1, obj.getContent());
            ps.setLong(2, obj.getChannelId());
            ps.setLong(3, obj.getUserId());
            ps.setString(4, obj.getDate());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    LOGGER.info("test generated keys... " + generatedKeys.getLong(1));
                    try (PreparedStatement pss = connection.prepareStatement(INSERT_CHANNEL_MESSAGE)) {
                        pss.setLong(1, obj.getChannelId());
                        pss.setLong(2, generatedKeys.getLong(1));
                        pss.execute();
                    }
                } else {
                    //throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            res = true;
        } catch (SQLException e) {
            throw new InsertionException("Error when inserting " + obj + " into the database. " + e.getLocalizedMessage());
        }
        return res;
    }

    @Override
    public Message read(int id) {
        return null;
    }

    @Override
    public boolean update(Message obj) {
        return false;
    }

    @Override
    public boolean delete(Message obj) {
        return false;
    }

}
