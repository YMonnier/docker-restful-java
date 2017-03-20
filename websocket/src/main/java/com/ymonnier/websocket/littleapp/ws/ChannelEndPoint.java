package com.ymonnier.websocket.littleapp.ws;

import com.google.gson.JsonObject;
import com.ymonnier.websocket.littleapp.utilities.database.DatabaseManager;
import com.ymonnier.websocket.littleapp.utilities.database.exceptions.InsertionException;
import com.ymonnier.websocket.littleapp.utilities.json.GsonSingleton;
import com.ymonnier.websocket.littleapp.utilities.models.From;
import com.ymonnier.websocket.littleapp.utilities.models.Message;
import com.ymonnier.websocket.littleapp.utilities.models.MessageDAO;
import org.glassfish.grizzly.Grizzly;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.ws.
 * File ChannelEndPoint.java.
 * Created by Ysee on 13/03/2017 - 14:25.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@ServerEndpoint(value = "/channel/{id}/{nickname}")
public class ChannelEndPoint {
    private static final Logger LOGGER = Grizzly.logger(ChannelEndPoint.class);

    private final static SessionManager manager = new SessionManager();

    private final static String JSON_KEY_CONTENT = "content";
    private final static String JSON_KEY_CHANNEL_ID = "channel_id";
    private final static String JSON_KEY_USER_ID = "user_id";

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("id") Long id, @PathParam("nickname") String nickname) throws IOException {
        LOGGER.info("onOpen " + id + ": " + session.toString());
        manager.add(session, id);

        Message message = new Message.Builder()
                .setContent("Welcom " + nickname + "!")
                .setNickname(nickname)
                .build();

        manager.broadcast(id, message, From.Type.SERVER);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("id") Long id, @PathParam("nickname") String nickname) {
        LOGGER.info("Received message: " + message + " from " + nickname + " and channel n°" + id);
        JsonObject json = GsonSingleton.getInstance().fromJson(message, JsonObject.class);
        if (json != null) {
            String content = json.get(JSON_KEY_CONTENT).getAsString();
            Long channelId = json.get(JSON_KEY_CHANNEL_ID).getAsLong();
            Long userId = json.get(JSON_KEY_USER_ID).getAsLong();

            Message mess = new Message.Builder()
                    .setContent(content)
                    .setChannelId(channelId)
                    .setNickname(nickname)
                    .setUserId(userId)
                    .build();

            try (Connection c = DatabaseManager.getConnection()) {
                MessageDAO messageDAO = new MessageDAO(c);
                if (messageDAO.create(mess))
                    manager.broadcast(id, mess, From.Type.CLIENT);
            } catch (SQLException | InsertionException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason, @PathParam("id") Long id, @PathParam("nickname") String nickname) throws IOException {
        //prepare the endpoint for closing.
        LOGGER.info("onClose: " + session.toString());
        manager.remove(session, id);
        Message json = new Message.Builder()
                .setContent("Bye bye " + nickname + "...")
                .setNickname(nickname)
                .build();

        manager.broadcast(id, json, From.Type.SERVER);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOGGER.warning(t.getMessage());
    }
}
