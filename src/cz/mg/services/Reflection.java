package cz.mg.services;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

import java.lang.reflect.Field;

import static cz.mg.services.NameProvider.getName;

@Utility class Reflection {
    @SuppressWarnings("unchecked")
    static <T> @Mandatory T createInstance(@Mandatory Class<?> clazz) {
        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ServiceException("Could not create instance of " + getName(clazz) + ".", e);
        }
    }

    static @Mandatory Field getField(@Mandatory Class<?> clazz, @Mandatory String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException("Could not get field " + getName(clazz) + "." + name + ".", e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> @Optional T getStaticFieldValue(@Mandatory Field field) {
        try {
            try {
                return (T) field.get(null);
            } catch (ReflectiveOperationException e) {
                field.setAccessible(true);
                return (T) field.get(null);
            }
        } catch (ReflectiveOperationException e) {
            throw new ServiceException("Could not get field " + getName(field) + " value.", e);
        }
    }

    static void setStaticFieldValue(@Mandatory Field field, @Optional Object value) {
        try {
            try {
                field.set(null, value);
            } catch (ReflectiveOperationException e) {
                field.setAccessible(true);
                field.set(null, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new ServiceException("Could not set field " + getName(field) + " value.", e);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> @Optional T getFieldValue(@Mandatory Field field, @Mandatory Object instance) {
        try {
            try {
                return (T) field.get(instance);
            } catch (ReflectiveOperationException e) {
                field.setAccessible(true);
                return (T) field.get(instance);
            }
        } catch (ReflectiveOperationException e) {
            throw new ServiceException("Could not get field " + getName(field) + " value.", e);
        }
    }

    static void setFieldValue(@Mandatory Field field, @Mandatory Object instance, @Optional Object value) {
        try {
            try {
                field.set(instance, value);
            } catch (ReflectiveOperationException e) {
                field.setAccessible(true);
                field.set(instance, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new ServiceException("Could not set field " + getName(field) + " value.", e);
        }
    }
}
