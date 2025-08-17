package es.gonzagile.jmockload.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Used to instantiate the objects and their fields.
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
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    /**
     * Sets the defined value to the defined field of the defined object.
     * @param target The object whose field is gonna be set.
     * @param field The field that is gonna be set.
     * @param value The value to be set.
     */
    static void setField(Object target, Field field, Object value) {
        try {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if(type.isPrimitive() && null == value) {
                setPrimitiveDefault(target, field, type);
            } else {
                field.set(target, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set value for field '" +
                    field.getName() + "' in " + target.getClass().getName(), e);
        }
    }

    /**
     * Initializes a primitive field with the primitive default value.
     * @param target The object whose field is gonna be set.
     * @param field The field that is gonna be set.
     * @param type The primitive type used to set it's default value.
     * @throws IllegalAccessException In case something unexpected happens and type doesn't match with any of the primitives
     */
    private static void setPrimitiveDefault(Object target, Field field, Class<?> type) throws IllegalAccessException {
        switch (type.getName()) {
            case "boolean" -> field.setBoolean(target, false);
            case "char" -> field.setChar(target, '\0');
            case "byte" -> field.setByte(target, (byte) 0);
            case "short" -> field.setShort(target, (short) 0);
            case "int" -> field.setInt(target, 0);
            case "long" -> field.setLong(target, 0L);
            case "float" -> field.setFloat(target, 0f);
            case "double" -> field.setDouble(target, 0d);
            default -> throw new IllegalAccessException("Unsupported primitive type: " + type);
        }
    }
}
