package com.ymonnier.restful.littleapp.controllers.Auth;

import com.ymonnier.restful.littleapp.models.User;
import com.ymonnier.restful.littleapp.models.User_;
import com.ymonnier.restful.littleapp.utilities.HibernateUtil;
import com.ymonnier.restful.littleapp.utilities.TokenUtil;
import com.ymonnier.restful.littleapp.utilities.errors.Error;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.controllers.Auth.
 * File AuthorizationService.java.
 * Created by Ysee on 12/03/2017 - 20:51.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public abstract class AuthorizationService {

    protected boolean authorization;
    protected User authenticate;

    public AuthorizationService(@Context HttpHeaders headers) {
        authorization = false;

        String token = headers.getHeaderString("Authorization");
        if (token != null) {
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();

                CriteriaBuilder builder = session.getCriteriaBuilder();

                CriteriaQuery<User> criteria = builder.createQuery(User.class);
                Root<User> root = criteria.from(User.class);
                criteria.select(root);
                criteria.where(builder.equal(root.get(User_.token), token));

                authenticate = session.createQuery(criteria).getSingleResult();

                if (authenticate != null)
                    authorization = true;
            } catch (Exception e) {
            }
        }
    }

    protected Response getUnauthorization() {
        return Error.unauthorized()
                .getResponse();
    }
}
