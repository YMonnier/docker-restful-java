package com.ymonnier.websocket.littleapp.utilities.json;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.utilities.json.
 * File From.java.
 * Created by Ysee on 13/03/2017 - 16:01.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class From {
    public enum Type {
        SERVER, CLIENT
    }
    String from;
    String content;

    public From(String from, String content) {
        this.from = from;
        this.content = content;
    }

    public String toJson() {
        return GsonSingleton
                .getInstance()
                .toJson(this);
    }

    @Override
    public String toString() {
        return "From{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
