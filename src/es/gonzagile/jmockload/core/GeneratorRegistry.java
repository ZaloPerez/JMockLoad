package es.gonzagile.jmockload.core;

import es.gonzagile.jmockload.generators.ValueGenerator;
import es.gonzagile.jmockload.generators.dumb.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratorRegistry {
    private static final Map<Class<?>, ValueGenerator<?>> generators = new ConcurrentHashMap<>();

    static {
        register(Boolean.class, new RandomBooleanGenerator());
        register(boolean.class, new RandomBooleanGenerator());

        register(Integer.class, new RandomIntGenerator());
        register(int.class, new RandomIntGenerator());

        register(Long.class, new RandomLongGenerator());
        register(long.class, new RandomLongGenerator());

        register(String.class, new RandomStringGenerator(10));
    }

    public static <T> void register(Class<T> type, ValueGenerator<? extends T> generator) {
        generators.put(type, generator);
    }

    public static <T> ValueGenerator<T> get(Class<T> type) {
        if (generators.containsKey(type)) {
            return (ValueGenerator<T>) generators.get(type);
        }
        return new RandomObjectGenerator<>(type);
    }
}
