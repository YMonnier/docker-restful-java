package com.ymonnier.restful.littleapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.models.
 * File User.java.
 * Created by Ysee on 24/01/2017 - 22:52.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min = 4)
    private String nickname;

    @Column(name = "password_hash")
    @SerializedName("password")
    private String passwordHash = null;

    @NotNull
    private String address;

    @Null
    private String token;

    @NotNull
    private int role;

    public User() {
    }

    private User(Builder builder) {
        this.nickname = builder.nickname;
        this.address = builder.address;
    }

    public void update(User person) {
        this.nickname = person.getNickname();
        this.address = person.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class Builder {
        private String nickname;
        private String address;

        public User build() {
            return new User(this);
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }
    }
}
