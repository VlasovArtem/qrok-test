package org.avlasov.qrok.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by artemvlasov on 10/07/2017.
 */
public abstract class Updater<T> {

    private static final Logger LOGGER = LogManager.getLogger(Updater.class);

    /**
     * Update target with source data
     *
     * @param target target object
     * @param source source object
     * @return updated {@param target} object
     */
    public T update(T target, T source) {
        if (Objects.nonNull(target) &&
                Objects.nonNull(source) &&
                !target.equals(source)) {
            Stream.of(target.getClass().getDeclaredFields())
                    .filter(field -> !getIgnoredFields().contains(field.getName()))
                    .forEach(updateField(target, source));
        }
        return target;
    }

    /**
     * List of fields, that should be ignored
     *
     * @return List of ignored fields
     */
    List<String> getIgnoredFields() {
        return Collections.singletonList("id");
    }

    /**
     * Update Field from source to target. Field will be updated if target field is null or source field is not empty and values of this fields is not equals.
     *
     * @param target target object
     * @param source source object
     * @return return Consumer
     */
    private Consumer<Field> updateField(T target, T source) {
        return field -> {
            try {
                field.setAccessible(true);
                if (Objects.isNull(field.get(target)) || (Objects.nonNull(field.get(source)) && !field.get(target).equals(field.get(source)))) {
                    field.set(target, field.get(source));
                    LOGGER.info("Field {} of the object {} was update with new value.", field.getName(), target.getClass());
                }
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage());
            }
        };
    }

}
