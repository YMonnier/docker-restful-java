package com.ymonnier.restful.littleapp.utilities.errors;

import com.ymonnier.restful.littleapp.models.Person;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

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
                for (ConstraintViolation<Object> cv : errors) {
                    this.errors.add(cv.getPropertyPath() + " " + cv.getMessage());
                }
            }
            return this;
        }
    }
}
