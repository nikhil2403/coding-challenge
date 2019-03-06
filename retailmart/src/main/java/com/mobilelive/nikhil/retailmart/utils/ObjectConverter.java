package com.mobilelive.nikhil.retailmart.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * util class to convert from one object to another based on similar named properties
 */
public class ObjectConverter {
    /**
     * Method that receives an object and converts it to another object with similar fields using case insensitive.
     * @param object
     * @param clazz
     * @return
     */
    public static <T> T convertCaseInsensitive(Object object, Class<T> clazz) {
        return new ObjectMapper()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .convertValue(object, clazz);
    }

    /**
     * Method receives an list object and
     * converts to another similar list object
     * @param object list object to be converted
     * @param typereference list type to be converted
     * @return List<T> list type to be returned
     */
    public static <T> List<T> convertToList(Object object, TypeReference typereference) {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .convertValue(object, typereference);
    }
}
