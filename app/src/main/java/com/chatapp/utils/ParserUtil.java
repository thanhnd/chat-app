package com.chatapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

public class ParserUtil {

    public static <T> T fromJson(String json, Class<T> classOfT) {
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return gson.fromJson(json, classOfT);
    }


    public static <T> T fromJson(Reader json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        Gson gson = new Gson();
        return gson.toJson(src, typeOfSrc);
    }

    public static String toJson(Object src) throws Exception {
        Gson gson = new Gson();
        return gson.toJson(src);
    }

    public static String toJsonDisableHtml(Object src) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, typeOfT);
    }
}
