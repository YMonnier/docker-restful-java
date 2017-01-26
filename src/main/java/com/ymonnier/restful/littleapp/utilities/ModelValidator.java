package com.ymonnier.restful.littleapp.utilities;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Project Project.
 * Package com.ymonnier.restful.littleapp.utilities.
 * File ModelValidator.java.
 * Created by Ysee on 25/01/2017 - 12:24.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public interface ModelValidator<T> {
    /*public static void validate(T o) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Class<?>>> constraintViolations = factory.getValidator().v;
    }*/
}
