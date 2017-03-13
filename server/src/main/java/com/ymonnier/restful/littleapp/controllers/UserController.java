package com.ymonnier.restful.littleapp.controllers;

import com.ymonnier.restful.littleapp.controllers.Auth.AuthorizationService;
import com.ymonnier.restful.littleapp.models.User;
import com.ymonnier.restful.littleapp.utilities.HibernateUtil;
import com.ymonnier.restful.littleapp.utilities.errors.Error;
import org.hibernate.Session;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.Authenticator;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.controllers.
 * File UserController.java.
 * Created by Ysee on 24/01/2017 - 23:04.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Path("users/")
@Produces(MediaType.APPLICATION_JSON)
public class UserController extends AuthorizationService {
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

    public UserController(@Context HttpHeaders headers) {
        super(headers);
        LOGGER.info("#UserController... ");
    }

    @Context
    UriInfo uriInfo;

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Allows to get a list of person.
     *
     * @return response which contains the list
     * of person or errors.
     */
    @GET
    public Response index() {
        LOGGER.info("#GET ");
        Response response = null;
        if (this.authorization) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                List persons = session.createQuery("from User")
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
        } else {
            response = this.getUnauthorization();
        }

        return response;
    }


    /**
     * Http requet which get a specific
     * user depending on the id.
     *
     * @param id user id
     * @return a response which contains the user or errors.
     */
    @GET
    @Path("{id}/")
    public Response show(@PathParam("id") Long id) {
        LOGGER.info("#GET {" + id + "} ");
        Response response = null;
        if (this.authorization) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.getTransaction().commit();
                session.close();

                if (user == null) {
                    response = Error.notFound(String.valueOf(id))
                            .getResponse();
                    LOGGER.warning("#GET {" + id + "} " + response.toString());
                } else {
                    response = Response
                            .ok(user)
                            .build();
                    LOGGER.info("#GET {" + id + "} " + response.toString());
                }

            } catch (Exception exception) {
                response = Error.internalServer(exception)
                        .getResponse();
                LOGGER.warning("#GET {" + id + "} " + exception.getLocalizedMessage());
                session.getTransaction().rollback();
            }
        } else {
            response = this.getUnauthorization();
        }
        return response;
    }


    /**
     * Update HTTP Request which allows to update a user.
     *
     * @param id   user id
     * @param user user data
     * @return a response which contains the user updated or errors.
     */
    @PUT
    @Path("{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, User user) {
        LOGGER.info("#PUT {" + id + "} ");
        Response response = null;
        if (this.authorization) {
            Set<ConstraintViolation<Object>> constraintViolations =
                    validator.validate(user);

            if (constraintViolations.size() > 0) {
                response = Error.badRequest(constraintViolations)
                        .getResponse();
                LOGGER.warning("#PUT " + response.toString());
            } else {
                Session session = HibernateUtil.getSessionFactory().openSession();
                try {
                    session.beginTransaction();
                    User userChecked = session.get(User.class, id);

                    if (userChecked == null) {
                        response = Error.notFound(String.valueOf(id))
                                .getResponse();
                        LOGGER.warning("#PUT {" + id + "} " + response.toString());
                    } else {
                        userChecked.update(user);
                        session.update(userChecked);
                        response = Response
                                .ok(user)
                                .build();
                        LOGGER.info("#PUT {" + id + "} " + response.toString());
                    }

                    session.getTransaction().commit();
                    session.close();
                } catch (Exception exception) {
                    response = Error.internalServer(exception)
                            .getResponse();
                    LOGGER.warning("#PUT {" + id + "} " + exception.getLocalizedMessage());
                    session.getTransaction().rollback();
                }
            }
        } else {
            response = this.getUnauthorization();
        }

        return response;
    }

    /**
     * HTTP Request which allows to destroy a specific user.
     *
     * @param id user id
     * @return a response.
     */
    @DELETE
    @Path("{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("id") Long id) {
        Response response = null;

        if (this.authorization) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                User user = session.get(User.class, id);

                if (user == null) {
                    response = Error.notFound(String.valueOf(id))
                            .getResponse();
                    LOGGER.warning("#DELETE {" + id + "} " + response.toString());
                } else {
                    session.delete(user);
                    response = Response
                            .ok(user)
                            .build();
                    LOGGER.info("#DELETE {" + id + "} " + response.toString());
                }
                session.getTransaction().commit();
                session.close();
            } catch (Exception exception) {
                response = Error.internalServer(exception)
                        .getResponse();
                LOGGER.warning("#DELETE {" + id + "} " + exception.getLocalizedMessage());
                session.getTransaction().rollback();
            }
        } else {
            response = this.getUnauthorization();
        }

        return response;
    }

    // Create
    // Index
    // Show
    // Update
    // Destroy
}
