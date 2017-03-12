package com.ymonnier.restful.littleapp.controllers.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.JsonObject;
import com.ymonnier.restful.littleapp.models.User;
import com.ymonnier.restful.littleapp.models.User_;
import com.ymonnier.restful.littleapp.utilities.HibernateUtil;
import com.ymonnier.restful.littleapp.utilities.TokenUtil;
import com.ymonnier.restful.littleapp.utilities.errors.Error;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
 * File AuthenticationController.java.
 * Created by Ysee on 09/03/2017 - 19:57.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Path("auth/")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationController {

    private final static Logger LOGGER = Logger.getLogger(AuthenticationController.class.getSimpleName());

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Context
    UriInfo uriInfo;

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as JSON Object.
     *
     * @return JSON Object containing user information.
     */
    @Path("register/")
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

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as JSON Object.
     *
     * @return JSON Object containing user information.
     */
    @Path("login/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(JsonObject json) {
        LOGGER.info("#POST " + json.toString());
        Response response = null;
        User user = null;
        String nickname = json.get("nickname").getAsString();
        String password = json.get("password").getAsString();

        if (nickname == null || password == null) {
            response = Error.badRequest("The nickname or password may not be null.")
                    .getResponse();
            LOGGER.warning("#POST " + response.toString());
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();

            try {
                CriteriaBuilder builder = session.getCriteriaBuilder();

                CriteriaQuery<User> criteria = builder.createQuery(User.class);
                Root<User> root = criteria.from(User.class);
                criteria.select(root);
                criteria.where(builder.equal(root.get(User_.nickname), nickname));

                user = session.createQuery(criteria).getSingleResult();

                if (user != null) {
                    if (BCrypt.checkpw(password, user.getPasswordHash())) {
                        session.beginTransaction();

                        String token = TokenUtil.generate(user);
                        user.setToken(token);

                        session.update(user);
                        session.getTransaction().commit();
                    } else {
                        response = Error.badRequest("Bad password...")
                                .getResponse();
                        LOGGER.warning("#POST " + response.toString());
                    }
                } else {
                    response = Error.badRequest("The nickname does not exist.")
                            .getResponse();
                    LOGGER.warning("#POST " + response.toString());
                }

                //session.beginTransaction();
                //session.save(user);
                //session.getTransaction().commit();

                URI builderUri = uriInfo.getAbsolutePathBuilder()
                        .build();
                response = Response
                        .created(builderUri)
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
