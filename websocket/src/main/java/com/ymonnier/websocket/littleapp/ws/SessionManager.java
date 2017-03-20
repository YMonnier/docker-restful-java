package com.ymonnier.websocket.littleapp.ws;

import com.ymonnier.websocket.littleapp.utilities.models.From;
import com.ymonnier.websocket.littleapp.utilities.models.Message;
import org.glassfish.grizzly.Grizzly;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.ws.
 * File SessionManager.java.
 * Created by Ysee on 13/03/2017 - 14:58.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
class SessionManager {
    private static final Logger LOGGER = Grizzly.logger(SessionManager.class);
    private static Map<Long, List<Session>> sessions;

    SessionManager() {
        sessions = new HashMap<>();
    }

    /**
     * Save the session into the correct channel ID.
     * @param session session object.
     * @param id channel id.
     */
    void add(Session session, Long id) {
        if(!sessions.containsKey(id)) {
            List<Session> tmp = new ArrayList<>();
            tmp.add(session);
            sessions.put(id, tmp);
        } else {
            sessions.get(id).add(session);
        }
    }

    /**
     * Remove a user.
     * @param session session object.
     * @param id channel ID.
     */
    void remove(Session session, Long id) {
        if(sessions.containsKey(id))
            sessions.get(id).remove(session);
    }

    /**
     * Send message to a specific channel.
     * @param id channel ID.
     * @param message message for sharing.
     */
    void broadcast(Long id, Message message, From.Type from) {
        LOGGER.info("Broadcast to channel " + id + ": " + message);
        if(sessions.containsKey(id)) {
            String mes = new From(from.name(), message).toJson();
            sessions.get(id).forEach(session -> {
                try {
                    session.getBasicRemote().sendText(mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
