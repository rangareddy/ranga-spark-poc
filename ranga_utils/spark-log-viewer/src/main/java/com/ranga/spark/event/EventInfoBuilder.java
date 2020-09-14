package com.ranga.spark.event;/* rangareddy.avula created on 31/05/20 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class EventInfoBuilder {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private EventInfo eventInfo;
    private String filePath;

    public EventInfoBuilder(String filePath) {
        this.filePath = filePath;
    }

    private static Map<String, String> getMapData(Map<String, Object> valueMap, String key) {
        return (Map<String, String>) valueMap.get(key);
    }

    public EventInfo getEventInfo() {
        if (eventInfo == null) {
            buildEventInfo();
        }
        return eventInfo;
    }

    public void setEventInfo(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    private void buildEventInfo() {
        try {
            eventInfo = new EventInfo();
            List<String> eventLogMessages = Files.readAllLines(new File(filePath).toPath());
            Set<String> hosts = new HashSet<>();
            for (String eventLogMessage : eventLogMessages) {
                Map<String, Object> jsonDataMap = OBJECT_MAPPER.readValue(eventLogMessage, Map.class);
                String eventType = (String) jsonDataMap.remove("Event");

                // 13 event types
                if (eventType.equals("SparkListenerLogStart")) {
                    buildSparkListenerLogStart(jsonDataMap);
                } else if (eventType.equals("SparkListenerBlockManagerAdded")) {
                    buildSparkListenerBlockManagerAdded(jsonDataMap);
                } else if (eventType.equals("SparkListenerEnvironmentUpdate")) {
                    buildSparkListenerEnvironmentUpdate(jsonDataMap);
                } else if (eventType.equals("SparkListenerApplicationStart")) {
                    buildSparkListenerApplicationStart(jsonDataMap);
                } else if (eventType.equals("SparkListenerApplicationEnd")) {
                  buildSparkListenerApplicationEnd(jsonDataMap);
                } else if (eventType.equals("SparkListenerJobStart")) {
                    buildSparkListenerJobStart(eventLogMessage, jsonDataMap);
                } else if (eventType.equals("SparkListenerStageSubmitted")) {
                    buildSparkListenerStageSubmitted(jsonDataMap);
                } else if (eventType.equals("SparkListenerExecutorAdded")) {
                    buildSparkListenerExecutorAdded(jsonDataMap);
                } else if (eventType.equals("SparkListenerTaskStart")) {
                    buildSparkListenerTaskStart(jsonDataMap);
                } else if (eventType.equals("SparkListenerTaskEnd")) {
                    buildSparkListenerTaskEnd(jsonDataMap);
                } else if (eventType.equals("SparkListenerExecutorRemoved")) {
                    buildSparkListenerExecutorRemoved(jsonDataMap);
                } else if (eventType.equals("SparkListenerBlockManagerRemoved")) {
                    buildSparkListenerBlockManagerRemoved(jsonDataMap);
                } else if (eventType.equals("SparkListenerStageCompleted")) {
                    buildSparkListenerStageCompleted(jsonDataMap);
                } else if (eventType.equals("SparkListenerJobEnd")) {
                    buildSparkListenerJobEnd(jsonDataMap);
                } else {
                    //  System.out.println("WARNING { unknown event type) " + (eventType) + " }");
                }
            }
            eventInfo.setHosts(hosts);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buildSparkListenerApplicationEnd(Map<String, Object> jsonDataMap) {
        eventInfo.getAppProperties().put("EndTime", jsonDataMap.remove("Timestamp"));
    }

    private void buildSparkListenerApplicationStart(Map<String, Object> jsonDataMap) {
        Map<String, Object> appProperties = eventInfo.getAppProperties();
        appProperties.put("StartTime", jsonDataMap.remove("Timestamp"));
        appProperties.putAll(jsonDataMap);
    }

    private void buildSparkListenerBlockManagerAdded(Map<String, Object> jsonDataMap) {
        //System.out.println(jsonDataMap);
    }

    private void buildSparkListenerTaskStart(Map<String, Object> jsonDataMap) {

    }



    private void buildSparkListenerStageSubmitted(Map<String, Object> jsonDataMap) {

    }

    private void buildSparkListenerTaskEnd(Map<String, Object> jsonDataMap) {

    }

    private void buildSparkListenerExecutorAdded(Map<String, Object> jsonDataMap) {
        //System.out.println("buildSparkListenerExecutorAdded "+jsonDataMap);
    }

    private void buildSparkListenerExecutorRemoved(Map<String, Object> jsonDataMap) {
       // System.out.println("buildSparkListenerExecutorRemoved "+jsonDataMap);
    }

    private void buildSparkListenerBlockManagerRemoved(Map<String, Object> jsonDataMap) {

    }

    private void buildSparkListenerStageCompleted(Map<String, Object> jsonDataMap) {

    }

    private void buildSparkListenerJobStart(String jobInfoStr, Map<String, Object> jsonData) {
        try {
            JobInfo jobInfo = OBJECT_MAPPER.readValue(jobInfoStr, JobInfo.class);
            if(jobInfo.getJobId() == 2) {
                System.out.println(jobInfoStr);
            }
            eventInfo.getJobInfos().put(jobInfo.getJobId(), jobInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void buildSparkListenerJobEnd(Map<String, Object> jsonData) {
        try {
            int jobId = (Integer) jsonData.get("Job ID");
            long completionTime = (Long) jsonData.get("Completion Time");
            Map<String, String> jobResult = (Map) jsonData.get("Job Result");
            JobInfo jobInfo = eventInfo.getJobInfos().get(jobId);
            jobInfo.setEndTime(completionTime);
            String result = jobResult.get("Result");
            if(result == null) {
                result = "UnKnown";
            }
            jobInfo.setJobResult(result);
            eventInfo.getJobInfos().put(jobInfo.getJobId(), jobInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buildSparkListenerLogStart(Map<String, Object> jsonData) {
        eventInfo.setSparkVersion((String) jsonData.get("Spark Version"));
    }

    private void buildSparkListenerEnvironmentUpdate(Map<String, Object> jsonData) {
        List<String> includeProps = Arrays.asList("Scala Version", "Java Version", "Java Home", "hdp.version", "java.class.version", "java.home", "os.name",
                "os.version", "user.country", "user.home", "user.timezone", "user.name", "user.dir");

        Map<String, String> jvmInformation = getMapData(jsonData, "JVM Information");
        Map<String, String> sparkProperties = getMapData(jsonData, "Spark Properties");
        Map<String, String> classPathProperties = getMapData(jsonData, "Classpath Entries");
        Map<String, String> systemProperties = getMapData(jsonData, "System Properties");

        for (String key : systemProperties.keySet()) {
            if (includeProps.contains(key)) {
                jvmInformation.put(key, systemProperties.get(key));
            }
        }
        eventInfo.setClasspathEntries(classPathProperties);
        eventInfo.setJvmInformation(jvmInformation);
        eventInfo.setSparkProperties(sparkProperties);

    }
}