package com.example.devicesservice.utils;

import java.util.Map;

public class MapUtils {

    public static void printMap(Map<?, ?> map) {
        map.forEach((key, value) -> System.out.println(key + ": " + value));
    }

}
