package com.ymonnier.restful.littleapp.controllers;

import com.ymonnier.restful.littleapp.models.Person;
import com.ymonnier.restful.littleapp.utilities.HibernateUtil;
import com.ymonnier.restful.littleapp.utilities.errors.Error;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.controllers.
 * File PersonController.java.
 * Created by Ysee on 24/01/2017 - 23:04.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Path("persons/")
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {
    private final static Logger LOGGER = Logger.getLogger(PersonController.class.getSimpleName());
    public final static String PATH = "persons/";

    @Context
    UriInfo uriInfo;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @GET
    public Response index() {
        LOGGER.info("#GET ");
        Response response = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            List persons = session.createQuery("from Person")
                    .list();

            session.getTransaction().commit();
            session.close();

            response = Response
                    .ok(persons)
                    .build();
        } catch (Exception exception) {
            LOGGER.warning("#GET " + exception.getLocalizedMessage());
            response = Error.internalServer(exception)
                    .getResponse();
            session.getTransaction().rollback();
        }

        return response;
    }

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as JSON Object.
     *
     * @return JSON Object created by the client.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person person) {
        LOGGER.info("#POST " + person.toString());
        Response response = null;
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(person);

        if (constraintViolations.size() > 0) {
            response = Error.badRequest(constraintViolations)
                    .getResponse();
            LOGGER.warning("#POST " + response.toString());
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(person);
                session.getTransaction().commit();

                URI builder = uriInfo.getAbsolutePathBuilder()
                        //.path(String.valueOf(p.getId()))
                        .build();

                response = Response
                        .created(builder)
                        .entity(person)
                        .build();
                LOGGER.info("#POST " + response.toString());
            } catch (Exception exception) {
                LOGGER.warning("#POST " + exception.getLocalizedMessage());
                response = Error.internalServer(exception)
                        .getResponse();
                session.getTransaction().rollback();
            }
        }

        return response;
    }

    @GET
    @Path("{id}/")
    public Response show(@PathParam("id") Long id) {
        LOGGER.info("#GET {" + id + "} ");
        Response response = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.getTransaction().commit();
            session.close();

            if (person == null) {
                response = Error.notFound(String.valueOf(id))
                        .getResponse();
                LOGGER.warning("#GET {" + id + "} " + response.toString());
            } else {
                response = Response
                        .ok(person)
                        .build();
                LOGGER.info("#GET {" + id + "} " + response.toString());
            }

        } catch (Exception exception) {
            response = Error.internalServer(exception)
                    .getResponse();
            LOGGER.warning("#GET {" + id + "} " + exception.getLocalizedMessage());
            session.getTransaction().rollback();
        }
        System.out.println("TEST......");
        return response;
    }

    // Create
    // Index
    // Show
    // Update
    // Destroy
}
