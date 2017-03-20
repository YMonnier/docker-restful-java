package com.ymonnier.restful.littleapp.models;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.models.
 * File Channel.java.
 * Created by Ysee on 12/03/2017 - 21:48.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Entity
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue
    @Expose
    private Long id;

    @NotNull
    @Length(min = 4)
    @Column(unique = true)
    @Expose
    private String name;

    @ManyToOne
    @Expose
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@Expose
    private List<Message> messages = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", messages=" + messages +
                '}';
    }
}
