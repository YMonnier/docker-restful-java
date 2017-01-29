package com.ymonnier.restful.littleapp.models;

import com.ymonnier.restful.littleapp.Main;
import com.ymonnier.restful.littleapp.controllers.PersonController;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.net.URI;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.models.
 * File Person.java.
 * Created by Ysee on 24/01/2017 - 22:52.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Entity
@Table
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    public Person() {
    }

    private Person(Builder builder) {
        this.name = builder.name;
        this.address = builder.address;
    }

    public void update(Person person) {
        this.name = person.getName();
        this.address = person.getAddress();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private String address;

        public Person build() {
            return new Person(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }
    }
}
