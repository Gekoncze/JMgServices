package cz.mg.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static cz.mg.services.NameProvider.getName;

@Utility class Validator {
    static void checkAnnotation(@Mandatory Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Service.class)) {
            throw new IllegalArgumentException(
                "Class " + getName(clazz) + " is missing service annotation."
            );
        }
    }

    static void checkAnnotation(@Mandatory Field field) {
        if (!field.isAnnotationPresent(Service.class)) {
            throw new IllegalArgumentException(
                "Field " + getName(field) + " is missing service annotation."
            );
        }
    }

    static void checkStatic(@Mandatory Field field) {
        if (!Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException(
                "Field " + getName(field) + " must be static."
            );
        }
    }

    static void checkNotStatic(@Mandatory Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException(
                "Field " + getName(field) + " cannot be static."
            );
        }
    }

    static void checkType(@Mandatory Field field, @Mandatory Class<?> clazz) {
        if (!field.getType().equals(clazz)) {
            throw new IllegalArgumentException(
                "Field " + getName(field) + " type must be " + getName(clazz) + "."
            );
        }
    }
}
