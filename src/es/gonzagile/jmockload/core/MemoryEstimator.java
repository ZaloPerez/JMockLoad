package es.gonzagile.jmockload.core;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class MemoryEstimator {

    private static final Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());

    public static long estimate(Object obj) {
        visited.clear();
        return estimateObject(obj);
    }

    public static long estimateObject(Object obj) {
        if (obj == null || visited.contains(obj)) return 0;
        visited.add(obj);

        Class<?> clazz = obj.getClass();

        // Wrapper types
        if (obj instanceof Byte || obj instanceof Boolean) return 1;
        if (obj instanceof Short || obj instanceof Character) return 2;
        if (obj instanceof Integer || obj instanceof Float) return 4;
        if (obj instanceof Long || obj instanceof Double) return 8;

        if (obj instanceof String s) {
            return 40 + s.length() * 2L; // base + contenido UTF-16
        }

        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            long size = 16 + 4L * length; // base + referencias/primitivos
            for (int i = 0; i < length; i++) {
                size += estimateObject(Array.get(obj, i));
            }
            return size;
        }

        if (obj instanceof Collection<?> col) {
            long size = 24 + col.size() * 8L;
            for (Object item : col) {
                size += estimateObject(item);
            }
            return size;
        }

        if (obj instanceof Map<?, ?> map) {
            long size = 32 + map.size() * 16L;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                size += estimateObject(entry.getKey());
                size += estimateObject(entry.getValue());
            }
            return size;
        }

        // Estimar campos de cualquier clase
        long size = 16; // cabecera del objeto
        for (Field field : getAllFields(clazz)) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            field.setAccessible(true);
            try {
                Object fieldValue = field.get(obj);
                size += 8; // referencia
                size += estimateObject(fieldValue);
            } catch (IllegalAccessException ignored) {}
        }

        return size;
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static long estimateString(String s) {
        if (s == null) return 0;
        return 40 + (long) s.length() * 2; // cabecera + contenido UTF-16
    }

    private static long estimateInteger(Integer i) {
        return i == null ? 0 : 16; // objeto Integer típico
    }

    private static long estimateLong(Long l) {
        return l == null ? 0 : 24; // objeto Long típico
    }

    private static long estimateArrayListOverhead(List<?> list) {
        int capacity = 10;
        try {
            // usar reflexión para ver la capacidad interna real
            var f = ArrayList.class.getDeclaredField("elementData");
            f.setAccessible(true);
            Object[] data = (Object[]) f.get(list);
            capacity = data.length;
        } catch (Exception ignored) {}

        return 24 + (long) capacity * 8; // estructura base + referencias
    }
}