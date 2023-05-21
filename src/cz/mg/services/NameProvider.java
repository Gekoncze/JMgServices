package cz.mg.services;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

import java.lang.reflect.Field;

@Utility class NameProvider {
    static @Mandatory String getName(@Mandatory Class<?> clazz) {
        return clazz.getSimpleName();
    }

    static @Mandatory String getName(@Mandatory Field field) {
        return field.getDeclaringClass().getSimpleName() + "." + field.getName();
    }
}
