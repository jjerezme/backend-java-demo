package com.demo.backend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.Validate;

public class Validation extends Validate {

    public static void greaterOrEqualThanZero(Number n) {
        greaterOrEqualThan(n, 0);
    }

    public static void greaterOrEqualThan(Number n, Number value) {
        isTrue(n.longValue() >= value.longValue(), "El valor debe ser mayor que o igual a %d", value);
    }

    public static void greaterThanZero(Number n) {
        greaterThan(n, 0);
    }

    public static void greaterThan(Number n, Number value) {
        isTrue(n.longValue() > value.longValue(), "El valor debe ser mayor a %d", value);
    }

    public static void lowerOrEqualThanZero(Number n) {
        lowerOrEqualThan(n, 0);
    }

    public static void lowerOrEqualThan(Number n, Number value) {
        isTrue(n.longValue() <= value.longValue(), "El valor debe ser menor que o igual a %d", value);
    }

    public static void lowerThanZero(Number n) {
        lowerThan(n, 0);
    }

    public static void lowerThan(Number n, Number value) {
        isTrue(n.longValue() < value.longValue(), "El valor debe ser menor a %d", value);
    }

    /**
     * Valida el largo mínimo de una cadena de texto
     * 
     * @param str Cadena de texto
     * @param max Largo máximo
     */
    public static void max(String str, int max) {
        notNull(str, "No debe estar vacío");
        isTrue(str.length() <= max, "El valor debe ser mayor a %d caracteres", max);
    }

    /**
     * Valida el largo mínimo de una cadena de texto
     * 
     * @param str Cadena de texto
     * @param min Largo mínimo
     */
    public static void min(String str, int min) {
        notNull(str, "No debe estar vacío");
        isTrue(str.length() >= min, "El valor debe ser menor a %d caracteres", min);
    }

    /**
     * Valida el largo de una cadena de texto
     * 
     * @param str Cadena de texto
     * @param min Largo mínimo
     * @param max Largo máximo
     */
    public static void size(String str, int min, int max) {
        min(str, min);
        max(str, max);
    }

    /**
     * Convert date
     * 
     * @param str
     * @param pattern
     * @return
     */
    public static Date isDate(String str, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato " + pattern);
        }
    }

}
