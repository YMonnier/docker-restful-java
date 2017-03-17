package com.ymonnier.websocket.littleapp.utilities.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.utilities.json.
 * File GsonSingleton.java.
 * Created by Ysee on 13/03/2017 - 15:37.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class GsonSingleton {
    private static Gson gson;
    public final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * Singleton pattern,
     *
     * @return gson instance.
     */
    public static Gson getInstance() {
        if (gson == null) return new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
        return gson;
    }
}
