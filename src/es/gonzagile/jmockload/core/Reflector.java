package es.gonzagile.jmockload.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Used to instanciate the objects and their fields.
 */
final class Reflector {
    /**
     * Creates an instance of the class passes as a parameter.
     * @param clazz Class of the object to be instantiated.
     * @return The instance of the object.
     * @param <T>
     */
    static <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear instancia de " + clazz.getName(), e);
        }
    }

    /**
     * Sets the defined value to the defined field of the defined object.
     * @param target The object whose field is gonna be set.
     * @param field The field that is gonna be set.
     * @param value The value to be set.
     */
    static void setField(Object target, Field field, Object value) {
        if(field.getType().isPrimitive()) throw new IllegalArgumentException("No hay soporte para primitivos (atributo " + field.getName() + ")");
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo establecer el valor del campo " +
                    field.getName() + " en " + target.getClass().getName(), e);
        }
    }
}
