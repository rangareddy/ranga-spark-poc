package com.ranga.spark.avro.java.delete;/* rangareddy.avula created on 23/06/20 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.util.hash.Hash;

import java.io.*;
import java.util.*;

public class ReadDoc {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> inputFileMap = objectMapper.readValue(new File("/Users/rangareddy.avula/work/spark_cases/691355/updated_schema.json"), Map.class);
        Map<String, Object> outputFileMap = objectMapper.readValue(new File("/Users/rangareddy.avula/work/spark_cases/691355/schema1.avsc"), Map.class);
        Map<String, String> docMap = getDocMap(inputFileMap);
        setDocMap(outputFileMap, docMap);
        File file = new File("/Users/rangareddy.avula/work/spark_cases/691355/new_updated_schema.avsc");
        if(file.exists()) {
            file.delete();
        }
        objectMapper.writeValue(file, outputFileMap);

    }

    private static void setDocMap(Map<String, Object> resultMap, Map<String, String> docMap) {
        String name = (String) resultMap.get("name");
        Object fields = resultMap.get("fields");
        Object type = resultMap.get("type");
        String docValue = docMap.get(name);
        if(docValue != null) {
            resultMap.put("doc", docValue);
        }

        if(fields instanceof List || type instanceof List) {
            List<Map> list = (List) (fields != null ? fields : type);
            for(Object value : list) {
                if(value instanceof Map) {
                    Map<String, Object> fieldMap = (Map<String, Object>) value;
                    setDocMap(fieldMap, docMap);
                }
            }
        }
    }

    private static Map<String, String> getDocMap(Map<String, Object> resultMap) {
        return getDocMap(resultMap, new HashMap<>());
    }
    private static Map<String, String> getDocMap(Map<String, Object> resultMap, Map<String, String> docMap) {
        String doc = (String) resultMap.get("doc");
        String name = (String) resultMap.get("name");
        if(doc != null) {
            docMap.put(name, doc);
        }
        Object fields = resultMap.get("fields");
        Object type = resultMap.get("type");
        if(fields instanceof List || type instanceof List) {
            List<Map> list = (List) (fields != null ? fields : type);
            for(Object value : list) {
                if(value instanceof Map) {
                    Map<String, Object> fieldMap = (Map<String, Object>) value;
                    docMap.putAll(getDocMap(fieldMap, docMap));
                }
            }
        }
        return docMap;
    }
}
