package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class InjectMockUtils {
    private static final Logger logger = LoggerFactory.getLogger(InjectMockUtils.class);
    public static void inject(Object objectTarget, String fieldName, Object objectToInject) {
        boolean isPrivate = false;

        try {
            Field f = objectTarget.getClass().getDeclaredField(fieldName);
            if (!f.isAccessible()) {
                f.setAccessible(true);
                isPrivate = true;
            }
            f.set(objectTarget, objectToInject);

            if (isPrivate) {
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }
}
