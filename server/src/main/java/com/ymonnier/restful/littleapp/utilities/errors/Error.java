package com.ymonnier.restful.littleapp.utilities.errors;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.utilities.errors.
 * File Error.java.
 * Created by Ysee on 25/01/2017 - 12:52.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class Error {
    private int status;
    private Collection<String> errors;

    private Error(Builder builder) {
        this.status = builder.status;
        this.errors = builder.errors;
    }

    public Response getResponse() {
        return Response
                .status(this.status)
                .entity(this)
                .build();
    }

    public static Error badRequest(String... errors) {
        return new Builder()
                .setStatus(Response.Status.BAD_REQUEST.getStatusCode())
                .setErrors(errors)
                .build();
    }

    public static Error badRequest(Set<ConstraintViolation<Object>> errors) {
        return new Builder()
                .setStatus(Response.Status.BAD_REQUEST.getStatusCode())
                .setErrors(errors)
                .build();
    }

    public static Error notFound(String identifier) {
        return new Builder()
                .setErrors("The resource " + identifier + " does not exist.")
                .setStatus(Response.Status.NOT_FOUND.getStatusCode())
                .build();
    }

    public static Error internalServer(Exception exception) {
        return new Builder()
                .setErrors(exception.getLocalizedMessage())
                .setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .build();
    }

    public static class Builder {
        private int status;
        private Collection<String> errors;

        public Error build() {
            return new Error(this);
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setErrors(Set<ConstraintViolation<Object>> errors) {
            if (this.errors == null && errors != null) {
                this.errors = new ArrayList<>();
                this.errors.addAll(errors.stream()
                        .map(cv -> cv.getPropertyPath() + " " + cv.getMessage())
                        .collect(Collectors.toList()));
            }
            return this;
        }

        public Builder setErrors(String... errors) {
            if (this.errors == null && errors != null) {
                this.errors = new ArrayList<>();
                Collections.addAll(this.errors, errors);
            }
            return this;
        }
    }
}
