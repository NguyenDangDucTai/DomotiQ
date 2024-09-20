package com.example.devicesservice.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Set<String> stopWords = new HashSet<>(Arrays.asList("ở", "trong", "là", "của", "và", "có", "cho", "với"));

    public static double compareStrings(String s1, String s2) {
        List<String> filteredS1 = Arrays.stream(s1.trim().toLowerCase().replaceAll("\\s+", " ").split(" "))
                .filter(item -> !stopWords.contains(item))
                .toList();

        String preProcessedS1 = String.join(" ", filteredS1);
        preProcessedS1 = removeAccent(preProcessedS1);
        String preProcessedS2 = s2.toLowerCase().trim().replaceAll("\\s+", " ");
        preProcessedS2 = removeAccent(preProcessedS2);

        String[] splitS1 = preProcessedS1.split(" ");

        int count = 0;
        for (String item : splitS1) {
            if (item.isBlank()) continue;
            if (preProcessedS2.contains(item)) {
                count += item.length();
            }
        }

        return (1.0 * count) / (preProcessedS1.length() - splitS1.length + 1);
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }

}
