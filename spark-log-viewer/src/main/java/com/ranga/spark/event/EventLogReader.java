package com.ranga.spark.event;/* rangareddy.avula created on 11/05/20 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class EventLogReader {

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/rangareddy.avula/work/spark_cases/678370/application_1586761957017_74880_1";
        generateEventLog(filePath);
    }

    private static void generateEventLog(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(new File(filePath).toPath());
        String applicationName = filePath.substring(filePath.lastIndexOf("/")+1);
        Map<String, Integer> hostCount = new HashMap<>();
        List<Map<String, String>> eventMessages = new ArrayList<>();
        Set<String> hosts = new HashSet<>();

        EventInfo eventInfo = new EventInfo();

        String sparkVersion = "";
        Set<String> events = new TreeSet<>();
        for(int i=0; i < lines.size(); i++) {
            String line = lines.get(i);
            Map<String, Object> valueMap = new ObjectMapper().readValue(line, Map.class);
            String event = (String) valueMap.get("Event");
            events.add(event);

            if("SparkListenerLogStart".equals(event)) {
                sparkVersion = (String) valueMap.get("Spark Version");
                eventInfo.setSparkVersion(sparkVersion);
            } else if("SparkListenerEnvironmentUpdate".equals(event)) {
                Map<String,String> jvmInformation = getMapData(valueMap, "JVM Information");
                Map<String,String> sparkProperties = getMapData(valueMap, "Spark Properties");
                Map<String,String> classPathProperties = getMapData(valueMap, "Classpath Entries");
                Map<String,String> systemProperties = getMapData(valueMap, "System Properties");
                jvmInformation.putAll(systemProperties);

                eventInfo.setClasspathEntries(classPathProperties);
                eventInfo.setJvmInformation(jvmInformation);
                eventInfo.setSparkProperties(sparkProperties);
            }

            if(line.contains("Exception")) {
                if(valueMap.containsKey("Task End Reason")) {

                    Map<String, String> taskEndReasonMap = (Map<String, String>) valueMap.get("Task End Reason");

                    Object value = taskEndReasonMap.remove("Shuffle ID");
                    taskEndReasonMap.put("ShuffleID", value.toString());

                    value = taskEndReasonMap.remove("Map ID");
                    taskEndReasonMap.put("MapID", value.toString());

                    value = taskEndReasonMap.remove("Reduce ID");
                    taskEndReasonMap.put("ReduceID", value.toString());

                    value = valueMap.get("Task Type");
                    taskEndReasonMap.put("TaskType", value.toString());

                    value = valueMap.get("Stage ID");
                    taskEndReasonMap.put("StageID", value.toString());
                    taskEndReasonMap.put("Event", valueMap.get("Event").toString());
                    Map<String, String> taskInfoMap = (Map<String, String>) valueMap.get("Task Info");
                    taskEndReasonMap.put("ExecutorID", taskInfoMap.get("Executor ID"));
                    taskEndReasonMap.put("Host", taskInfoMap.get("Host"));

                    taskEndReasonMap.remove("Block Manager Address");
                    String message = taskEndReasonMap.remove("Message");

                    Map<String, String> map = new HashMap<>(2);
                    map.put("Message", message);
                    map.put("ShortMessage", message.split("\\n")[0]);

                    ObjectMapper objectMapper = new ObjectMapper();
                    String eventMsg = objectMapper.writeValueAsString(taskEndReasonMap);
                    eventMsg = eventMsg.replaceAll(",", ", <br>").replace("{", "").replace("}", "");

                    map.put("Event", eventMsg);

                    map.put("Unique", "Exception_Collapse_"+taskEndReasonMap.get("ExecutorID") +"_"+taskEndReasonMap.get("StageID")+i);

                    eventMessages.add(map);

                    String host = taskInfoMap.get("Host");
                    hosts.add(host);

                    Integer count = hostCount.get(host);
                    if(count == null) {
                        count = 0;
                    }
                    count++;
                    hostCount.put(host,count);
                    String executorId = taskInfoMap.get("Executor ID");
                    String locality = taskInfoMap.get("Locality");
                 //   String message = taskEndReasonMap.get("Message");
                   // System.out.println(executorId +"\t"+host +"\t"+locality);
                }
            }
        }
        eventInfo.setHosts(hosts);
        System.out.println(eventInfo);
        System.out.println("\n\nHosts:\n"+events);
        //GenerateEventMessageHTML.generateHTML(filePath, applicationName, eventMessages);
        System.out.println(hostCount);
    }

    private static Map<String, String> getMapData(Map<String, Object> valueMap, String key) {
        return (Map<String, String>) valueMap.get(key);
    }
}
