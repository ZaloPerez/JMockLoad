package es.gonzagile.jmockload.core;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JMockLoad is the core class of the JMoclLoad library. It is used to set the class of the objects to be generated,
 * the amount of objects to be generated and the generators and constraints to generate the values of the fields
 * of the generated objects.
 * @param <T> The class of the objects to be generated.
 */
public class JMockLoad<T> {

    private final Class<T> clazz;
    private final int size;
    private long delayMs = 0;
    private final Map<String, List<FieldConstraint>> constraints = new HashMap<>();
    private final Map<String, ValueGenerator<?>> customGenerators = new HashMap<>();

    /**
     * Constructor of JMockLoad. Sets the class of the objects to be generated and the size of the generated collection.
     * @param clazz The class of the generated objects.
     * @param size The amount of objects to be generated and added to the final collection.
     */
    public JMockLoad(Class<T> clazz, int size) {
        if(clazz.isRecord() || clazz.isAnnotation() || clazz.isEnum() || clazz.isInterface()) {
            throw new IllegalArgumentException("No se permiten records, anotaciones, enums ni interfaces.");
        }
        this.clazz = clazz;
        this.size = size;
    }

    /**
     * Sets a delay time to emulate database retrieval time delay.
     * @param ms The amount of time in milliseconds to delay the generation of the collection.
     * @return This.
     */
    public JMockLoad<T> withDelay(long ms) {
        this.delayMs = ms;
        return this;
    }

    private JMockLoad() {
        this(null, 0);
    }

    /**
     * Sets a constraint for the field with the name passed by parameter.
     * @param fieldname The name of the field to be attached to the constraint.
     * @param constraint The constraint used to validate the generated value of the attached field.
     * @return This.
     */
    JMockLoad<T> withConstraint(String fieldname, FieldConstraint constraint) {
        List<FieldConstraint> fieldConstraints = constraints.getOrDefault(fieldname, new ArrayList<>());
        fieldConstraints.add(constraint);
        constraints.put(fieldname, fieldConstraints);
        return this;
    }

    /**
     * Sets a generator for the field with the name passed by parameter.
     * @param fieldName The name of the field to be attached to the generator.
     * @param generator The generator to be used to generate the value of the attached field.
     * @return This.
     * @param <V>
     */
    <V> JMockLoad<T> withCustomGenerator(String fieldName, ValueGenerator<V> generator) {
        customGenerators.put(fieldName, generator);
        return this;
    }

    /**
     * Executes the process of generating a List loaded by mock data.
     * @return The generated list.
     */
    public List<T> execute() {
        try {
            if (delayMs > 0) Thread.sleep(delayMs);
        } catch (InterruptedException ignored) {}
        return IntStream.range(0, size)
                .mapToObj(this::generateInstance)
                .collect(Collectors.toList());
    }

    /**
     * Generates the instance of an individual object.
     * @param index Index of the object in the Collection, used by some generators.
     * @return The instance of the object.
     */
    private T generateInstance(int index) {
        T instance = Reflector.createInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            Object value = generateValueForField(field, index);
            Reflector.setField(instance, field, value);
        }
        return instance;
    }

    /**
     * Generates the value for the defined field using the predefined generator and constraints.
     * @param field The field to be filled.
     * @param index The index of the object in the Collection, used by some generators.
     * @return The generated value for the field.
     */
    private Object generateValueForField(Field field, int index) {
        String fieldname = field.getName();

        ValueGenerator<?> generator = customGenerators.get(fieldname);
        List<FieldConstraint> fieldConstraints = constraints.getOrDefault(fieldname, new ArrayList<>());

        if (null != generator) {
            return generator.generate(index, fieldConstraints);
        }
        return null;
    }

    /**
     * Starts the configuration chain in order to set the generator and/or constraints to the field generation.
     * @param fieldName The name of the field to be configured.
     * @return A FieldConfigurator to start the chain.
     */
    public FieldConfigurator<T> configure(String fieldName) {
        return new FieldConfigurator<>(fieldName, this);
    }
}
