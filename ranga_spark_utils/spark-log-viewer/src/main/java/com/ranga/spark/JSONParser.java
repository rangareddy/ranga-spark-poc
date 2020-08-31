package com.ranga.spark;/* rangareddy.avula created on 08/07/20 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class JSONParser {
    public static void main(String[] args) throws Exception {
        File jsonFile = new File("/Users/rangareddy.avula/work/spark_cases/691873/nbo_tests_test.json");
        Map<String, Object> map = new ObjectMapper().readValue(jsonFile, Map.class);
        System.out.println(map.keySet());

        System.out.println(map.get("paragraphs"));
    }
}
