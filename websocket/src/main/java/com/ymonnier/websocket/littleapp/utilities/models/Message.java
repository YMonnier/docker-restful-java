package com.ymonnier.websocket.littleapp.utilities.models;

import com.google.gson.annotations.SerializedName;
import com.ymonnier.websocket.littleapp.utilities.date.DateFormatter;
import com.ymonnier.websocket.littleapp.utilities.json.GsonSingleton;

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

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("channel_id")
    private Long channelId;

    public Message(Builder builder) {
        this.date = DateFormatter.dateTime();
        this.content = builder.content;
        this.nickname = builder.nickname;
        this.channelId = builder.channelId;
        this.userId = builder.userId;
    }

    public String getDate() {
        return date;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getChannelId() {
        return channelId;
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
        private String content;
        private String nickname;
        private Long userId;
        private Long channelId;

        public Message build() {
            return new Message(this);
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setChannelId(Long channelId) {
            this.channelId = channelId;
            return this;
        }
    }
}
