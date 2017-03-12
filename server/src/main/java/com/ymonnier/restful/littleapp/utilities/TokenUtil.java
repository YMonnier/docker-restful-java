package com.ymonnier.restful.littleapp.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ymonnier.restful.littleapp.models.User;

import java.io.UnsupportedEncodingException;

/**
 * Project restful.littleapp.
 * Package com.ymonnier.restful.littleapp.utilities.
 * File TokenUtil.java.
 * Created by Ysee on 12/03/2017 - 21:03.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class TokenUtil {
    public static String generate(User user) throws UnsupportedEncodingException {
        return JWT.create()
                .withIssuer(user.getId() + user.getNickname())
                .sign(Algorithm.HMAC256(user.getPasswordHash() + user.getId()));
    }
}
