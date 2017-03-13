package com.ymonnier.restful.littleapp.models;

import org.hibernate.validator.constraints.Length;

import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Long id;

    @NotNull
    @Length(min = 4)
    @Column(unique = true)
    private String name;

    @ManyToOne
    private User user;

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
}
