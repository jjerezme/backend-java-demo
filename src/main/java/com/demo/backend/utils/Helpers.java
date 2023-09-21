package com.demo.backend.utils;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

public class Helpers {
    /**
     * 
     * @param fields
     * @param obj
     */
    public static void patchFields(Map<String, Object> fields, Object obj) {
        if(fields == null) {
            return;
        }
        
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(obj.getClass(), key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, obj, value);
            }
        });
    }
}
