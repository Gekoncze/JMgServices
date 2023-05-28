package cz.mg.services;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static cz.mg.services.Reflection.*;
import static cz.mg.services.Validator.*;

public @Utility class Services {
    private static final @Mandatory String INSTANCE_FIELD_NAME = "instance";

    public static <T> T get(@Mandatory Class<T> serviceClass) {
        checkAnnotation(serviceClass);

        Field instanceField = getField(serviceClass, INSTANCE_FIELD_NAME);
        T instance = getStaticFieldValue(instanceField);

        if (instance != null) {
            return instance;
        } else {
            synchronized (Services.class) {
                if (getStaticFieldValue(instanceField) == null) {
                    create(serviceClass, instanceField);
                }
                return get(serviceClass);
            }
        }
    }

    private static <T> void create(@Mandatory Class<T> serviceClass, @Mandatory Field instanceField) {
        checkAnnotation(instanceField);
        checkStatic(instanceField);
        checkType(instanceField, serviceClass);

        T instance = createInstance(serviceClass);
        setStaticFieldValue(instanceField, instance);

        try {
            initialize(serviceClass, instance);
        } catch (RuntimeException e) {
            setStaticFieldValue(instanceField, null);
            throw e;
        }
    }

    private static <T> void initialize(@Mandatory Class<T> serviceClass, @Mandatory T instance) {
        for (Field field : serviceClass.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                checkAnnotation(field);
                checkNotStatic(field);

                setFieldValue(field, instance, get(field.getType()));
            }
        }
    }
}
