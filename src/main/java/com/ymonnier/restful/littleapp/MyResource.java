package com.ymonnier.restful.littleapp;

import com.ymonnier.restful.littleapp.models.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    public Person getIt() {

        Person p = new Person.Builder()
                .setName("Paul")
                .setAddress("15 Street view")
                .build();
        return p;
        //return "Got it!";
    }
}
