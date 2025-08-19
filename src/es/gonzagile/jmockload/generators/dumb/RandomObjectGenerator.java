package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.core.GeneratorRegistry;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class RandomObjectGenerator <T> implements ValueGenerator<T> {

    private final Class<T> targetClass;

    public RandomObjectGenerator(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T generate() {
        return this.generate(0, List.of());
    }

    @Override
    public T generate(int index) {
        return this.generate(index, List.of());
    }

    @Override
    public T generate(int index, List<FieldConstraint> constraints) {
        try {
            T instance = this.createInstance(targetClass);
            for (Field field : targetClass.getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                ValueGenerator<?> generator = GeneratorRegistry.get(fieldType);
                if (generator == null) {
                    generator = new RandomObjectGenerator<>(fieldType);
                }
                Object value = generator.generate();
                field.set(instance, value);
            }
            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Error generating object for " + targetClass, e);
        }
    }

    private <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}
