package com.ymonnier.restful.littleapp.models;

import com.google.gson.annotations.Expose;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.models.
 * File Message.java.
 * Created by Ysee on 19/03/2017 - 22:09.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    Long id;

    @NotNull
    @Expose
    String content;

    @ManyToOne
    //@Expose
    Channel channel;

    @ManyToOne
    @Expose
    User user;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Channel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", channel=" + channel +
                ", user=" + user +
                '}';
    }
}
