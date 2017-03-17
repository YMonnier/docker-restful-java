package com.ymonnier.websocket.littleapp.utilities.json;

import com.ymonnier.websocket.littleapp.utilities.date.DateFormatter;

/**
 * Project websocket.
 * Package com.ymonnier.websocket.littleapp.utilities.json.
 * File Message.java.
 * Created by Ysee on 13/03/2017 - 15:35.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class Message {
    private String date;
    private String content;
    private String nickname;

    public Message(Builder builder) {
        this.date = DateFormatter.dateTime();
        this.content = builder.message;
        this.nickname = builder.nickname;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return content;
    }

    public String getNickname() {
        return nickname;
    }

    public String toJson() {
        return GsonSingleton
                .getInstance()
                .toJson(this);
    }

    @Override
    public String toString() {
        return "Message{" +
                "date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", nickname=" + nickname +
                '}';
    }

    public static class Builder {
        private String message;
        private String nickname;

        public Message build() {
            return new Message(this);
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }
    }
}
