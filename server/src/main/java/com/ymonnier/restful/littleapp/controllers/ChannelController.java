package com.ymonnier.restful.littleapp.controllers;

import com.google.gson.JsonObject;
import com.ymonnier.restful.littleapp.controllers.Auth.AuthorizationService;
import com.ymonnier.restful.littleapp.models.Channel;
import com.ymonnier.restful.littleapp.utilities.HibernateUtil;
import com.ymonnier.restful.littleapp.utilities.errors.Error;
import org.hibernate.Session;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.controllers.
 * File ChannelController.java.
 * Created by Ysee on 12/03/2017 - 22:03.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Path("channels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChannelController extends AuthorizationService {

    private final static Logger LOGGER = Logger.getLogger(ChannelController.class.getSimpleName());

    @Context
    UriInfo uriInfo;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ChannelController(@Context HttpHeaders headers) {
        super(headers);
    }

    /**
     * Get all channels.
     * @return list of channel.
     */
    @GET
    public Response index() {
        LOGGER.info("#GET ");
        Response response = null;
        if (this.authorization) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                List channels = session.createQuery("from Channel")
                        .list();
                session.close();

                response = Response
                        .ok(channels)
                        .build();
                LOGGER.info("#GET " + response.toString());
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
     * Create a new channel.
     *
     * @param channel channel name
     * @return Channel saved into the database.
     */
    @POST
    public Response create(Channel channel) {
        LOGGER.info("#POST " + channel.toString());
        Response response = null;
        if (this.authorization) {

            Set<ConstraintViolation<Object>> constraintViolations =
                    validator.validate(channel);

            if (constraintViolations.size() > 0) {
                response = Error.badRequest(constraintViolations)
                        .getResponse();
                LOGGER.warning("#POST " + response.toString());
            } else {

                Session session = HibernateUtil.getSessionFactory().openSession();
                try {
                    session.beginTransaction();
                    channel.setUser(this.authenticate);
                    session.save(channel);
                    session.getTransaction().commit();

                    URI builder = uriInfo.getAbsolutePathBuilder()
                            .build();

                    response = Response
                            .created(builder)
                            .entity(channel)
                            .build();
                    LOGGER.info("#POST " + response.toString());
                } catch (Exception exception) {
                    LOGGER.warning("#POST " + exception.getLocalizedMessage());
                    response = Error.internalServer(exception)
                            .getResponse();
                    session.getTransaction().rollback();
                }
            }
        } else {
            response = this.getUnauthorization();
        }
        return response;
    }

    /**
     * Delete a Channel
     * @param id channel ID.
     * @return A response.
     */
    @DELETE
    @Path("{id}/")
    public Response destroy(@PathParam("id") Long id) {
        LOGGER.info("#DELETE " + id);
        Response response = null;
        if (this.authorization) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Channel channel = session.get(Channel.class, id);

                if (channel == null) {
                    response = Error.notFound(String.valueOf(id))
                            .getResponse();
                    LOGGER.warning("#DELETE {" + id + "} " + response.toString());
                } else {
                    session.delete(channel);
                    response = Response
                            .ok(channel)
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

    /**
     * Get all messages from a specific channel.
     * @return messages.
     */
    @GET
    @Path("{id}/messages")
    public Response messages(@PathParam("id") Long id) {
        LOGGER.info("#GET MESSAGES ");
        Response response = null;
        if (this.authorization) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Channel channel = session.get(Channel.class, id);

                if (channel == null) {
                    response = Error.notFound(String.valueOf(id))
                            .getResponse();
                    LOGGER.warning("#GET MESSAGES  {" + id + "} " + response.toString());
                } else {

                    response = Response
                            .ok(channel.getMessages())
                            .build();

                    //channel.getMessages().forEach(message -> LOGGER.info(message.toString()));
                    LOGGER.info("#GET MESSAGES  size:" + channel.getMessages().size() + " " + response.toString());
                }
                session.close();
            } catch (Exception exception) {
                response = Error.internalServer(exception)
                        .getResponse();
                LOGGER.warning("#GET MESSAGES  {" + id + "} " + exception.getLocalizedMessage());
                session.getTransaction().rollback();
            }
        } else {
            response = this.getUnauthorization();
        }
        return response;
    }
}
