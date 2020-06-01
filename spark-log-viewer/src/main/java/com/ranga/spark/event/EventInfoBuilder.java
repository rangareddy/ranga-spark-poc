package com.ranga.spark.event;/* rangareddy.avula created on 31/05/20 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class EventInfoBuilder {
    private String filePath;

    public EventInfoBuilder(String filePath) {
        this.filePath = filePath;
    }

    private static Map<String, String> getMapData(Map<String, Object> valueMap, String key) {
        Map<String, String> map = (Map<String, String>) valueMap.get(key);
        return new TreeMap(map);
    }

    public static void do_SparkListenerLogStart(EventInfo eventInfo, Map<String, Object> valueMap) {
        eventInfo.setSparkVersion((String) valueMap.get("Spark Version"));
    }

    public static void do_SparkListenerEnvironmentUpdate(EventInfo eventInfo, Map<String, Object> valueMap) {
        List<String> includeProps = Arrays.asList("Scala Version", "Java Version", "Java Home", "hdp.version", "java.class.version", "java.home", "os.name",
                "os.version", "user.country", "user.home", "user.timezone", "user.name", "user.dir");

        Map<String, String> jvmInformation = getMapData(valueMap, "JVM Information");
        Map<String, String> sparkProperties = getMapData(valueMap, "Spark Properties");
        Map<String, String> classPathProperties = getMapData(valueMap, "Classpath Entries");
        Map<String, String> systemProperties = getMapData(valueMap, "System Properties");

        for (String key : systemProperties.keySet()) {
            if (includeProps.contains(key)) {
                jvmInformation.put(key, systemProperties.get(key));
            }
        }
        eventInfo.setClasspathEntries(classPathProperties);
        eventInfo.setJvmInformation(jvmInformation);
        eventInfo.setSparkProperties(sparkProperties);

        /*self.parsed_data["java_version"] = data["JVM Information"]["Java Version"]
        self.parsed_data["app_name"] = data["Spark Properties"]["spark.app.name"]
        self.parsed_data["app_id"] = data["Spark Properties"]["spark.app.id"]
            #self.parsed_data["driver_memory"] = data["Spark Properties"]["spark.driver.memory"]
        self.parsed_data["executor_memory"] = data["Spark Properties"]["spark.executor.memory"]
        self.parsed_data["commandline"] = data["System Properties"]["sun.java.command"] */

    }

    public EventInfo buildEventInfo() throws Exception {
        List<String> eventLogMessages = Files.readAllLines(new File(filePath).toPath());

        EventInfo eventInfo = new EventInfo();
        Set<String> hosts = new HashSet<>();
        for (String eventLogMessage : eventLogMessages) {
            Map<String, Object> valueMap = new ObjectMapper().readValue(eventLogMessage, Map.class);
            String eventType = (String) valueMap.remove("Event");

            // 13 event types
            if (eventType.equals("SparkListenerLogStart")) {
                do_SparkListenerLogStart(eventInfo, valueMap);
            } else if (eventType.equals("SparkListenerBlockManagerAdded")) {
                //do_SparkListenerBlockManagerAdded(json_data)
            } else if (eventType.equals("SparkListenerEnvironmentUpdate")) {
                do_SparkListenerEnvironmentUpdate(eventInfo, valueMap);
            } else if (eventType.equals("SparkListenerApplicationStart")) {
                valueMap.put("StartTime", valueMap.remove("Timestamp"));
                valueMap.remove("Driver Logs");
                eventInfo.getAppProperties().putAll(valueMap);
            } else if (eventType.equals("SparkListenerApplicationEnd")) {
                valueMap.put("EndTime", valueMap.remove("Timestamp"));
                eventInfo.getAppProperties().putAll(valueMap);
            } else if (eventType.equals("SparkListenerJobStart")) {
                // self.do_SparkListenerJobStart(json_data)
            } else if (eventType.equals("SparkListenerStageSubmitted")) {
                // self.do_SparkListenerStageSubmitted(json_data)
            } else if (eventType.equals("SparkListenerExecutorAdded")) {
                // self.do_SparkListenerExecutorAdded(json_data)
            } else if (eventType.equals("SparkListenerTaskStart")) {
                //self.do_SparkListenerTaskStart(json_data)
            } else if (eventType.equals("SparkListenerTaskEnd")) {
                // self.do_SparkListenerTaskEnd(json_data)
            } else if (eventType.equals("SparkListenerExecutorRemoved")) {
                //self.do_SparkListenerExecutorRemoved(json_data)
            } else if (eventType.equals("SparkListenerBlockManagerRemoved")) {
                //self.do_SparkListenerBlockManagerRemoved(json_data)
            } else if (eventType.equals("SparkListenerStageCompleted")) {
                //self.do_SparkListenerStageCompleted(json_data)
            } else if (eventType.equals("SparkListenerJobEnd")) {
                //self.do_SparkListenerJobEnd(json_data)
            } else {
                System.out.println("WARNING { unknown event type) " + (eventType) + " }");
            }
        }
        eventInfo.setHosts(hosts);
        return eventInfo;
    }
}
