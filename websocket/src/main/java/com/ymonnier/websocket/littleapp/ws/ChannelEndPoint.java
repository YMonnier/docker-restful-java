package com.ymonnier.websocket.littleapp.ws;

import com.ymonnier.websocket.littleapp.utilities.json.From;
import com.ymonnier.websocket.littleapp.utilities.json.Message;
import org.glassfish.grizzly.Grizzly;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("id") Long id, @PathParam("nickname") String nickname) throws IOException {
        LOGGER.info("onOpen " + id + ": " + session.toString());
        manager.add(session, id);

        String json = new Message.Builder()
                .setMessage("Welcom " + nickname + "!")
                .setNickname(nickname)
                .build()
                .toJson();

        manager.broadcast(id, json, From.Type.SERVER);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("id") Long id) {
        LOGGER.info("Received message: " + message);
        manager.broadcast(id, message, From.Type.CLIENT);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason, @PathParam("id") Long id, @PathParam("nickname") String nickname) throws IOException {
        //prepare the endpoint for closing.
        LOGGER.info("onClose: " + session.toString());
        manager.remove(session, id);
        String json = new Message.Builder()
                .setMessage("Bye bye " + nickname + "...")
                .setNickname(nickname)
                .build()
                .toJson();

        manager.broadcast(id, json, From.Type.SERVER);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOGGER.warning(t.getMessage());
    }
}
