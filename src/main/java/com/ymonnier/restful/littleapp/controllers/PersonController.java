package com.ymonnier.restful.littleapp.controllers;

import com.ymonnier.restful.littleapp.models.Person;
import com.ymonnier.restful.littleapp.utilities.errors.Error;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Set;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.controllers.
 * File PersonController.java.
 * Created by Ysee on 24/01/2017 - 23:04.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Path("persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as JSON Object.
     *
     * @return JSON Object created by the client.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person p, @Context UriInfo uriInfo) {

        /*Person p = new Person.Builder()
                .setName("Paul")
                .setAddress("15 Street view")
                .build();
        */
        //URI absoluteURI=info.getBaseUriBuilder().path("/wibble").build();
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(p);

        if (constraintViolations.size() > 0) {
            constraintViolations.forEach(pcv -> System.out.println(pcv.getPropertyPath()));
            Error error = new Error.Builder()
                    .setStatus(Response.Status.BAD_REQUEST.getStatusCode())
                    .setErrors(constraintViolations)
                    .build();

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }


        URI builder = uriInfo.getAbsolutePath();

        return Response
                .created(builder)
                .entity(p)
                .build();
    }

    // Create
    // Index
    // Show
    // Update
    // Destroy
}
