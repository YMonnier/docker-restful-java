package com.ymonnier.restful.littleapp.controllers;

import com.ymonnier.restful.littleapp.models.User;
import com.ymonnier.restful.littleapp.utilities.HibernateUtil;
import com.ymonnier.restful.littleapp.utilities.errors.Error;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.controllers.
 * File RegisterController.java.
 * Created by Ysee on 09/03/2017 - 19:57.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Path("register/")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterController {

    private final static Logger LOGGER = Logger.getLogger(RegisterController.class.getSimpleName());

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Context
    UriInfo uriInfo;

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as JSON Object.
     *
     * @return JSON Object created by the client.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        LOGGER.info("#POST " + user.toString());
        Response response = null;
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(user);

        if (constraintViolations.size() > 0) {
            response = Error.badRequest(constraintViolations)
                    .getResponse();
            LOGGER.warning("#POST " + response.toString());
        } else {
            // Set hashed password
            String hashed = BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt());
            user.setPasswordHash(hashed);

            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();

                URI builder = uriInfo.getAbsolutePathBuilder()
                        //.path(String.valueOf(p.getId()))
                        .build();

                response = Response
                        .created(builder)
                        .entity(user)
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
}
