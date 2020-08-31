package com.ranga.spark.event;/* rangareddy.avula created on 11/05/20 */

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class EventLogReader {

    public static void main(String[] args) throws Exception {
        //String filePath = "/Users/rangareddy.avula/work/spark_cases/678370/application_1586761957017_74880_1";
        String filePath = "./src/main/resources/app-20170708141026-0013";
        generateEventLog(filePath);
    }

    private static void generateEventLog(String filePath) throws Exception {
        EventInfo eventInfo = new EventInfoBuilder(filePath).getEventInfo();

        /*Set<String> events = new TreeSet<>();
        List<String> includeProps = Arrays.asList("Scala Version","Java Version","Java Home", "hdp.version", "java.class.version", "java.home", "os.name",
                "os.version", "user.country", "user.home", "user.timezone", "user.name", "user.dir" );

        for (int i = 0; i < lines.size(); i++)) {
            String line = lines.get(i);
            Map<String, Object> valueMap = new ObjectMapper().readValue(line, Map.class);
            String event = (String) valueMap.remove("Event");
            events.add(event);

            if ("SparkListenerLogStart".equals(event))) {
                eventInfo.setSparkVersion((String) valueMap.get("Spark Version"));
            } else if ("SparkListenerEnvironmentUpdate".equals(event))) {
                Map<String, String> jvmInformation = getMapData(valueMap, "JVM Information");
                Map<String, String> sparkProperties = getMapData(valueMap, "Spark Properties");
                Map<String, String> classPathProperties = getMapData(valueMap, "Classpath Entries");
                Map<String, String> systemProperties = getMapData(valueMap, "System Properties");

                for(String key : systemProperties.keySet())) {
                    if(includeProps.contains(key))) {
                        jvmInformation.put(key, systemProperties.get(key));
                    }
                }
                eventInfo.setClasspathEntries(classPathProperties);
                eventInfo.setJvmInformation(jvmInformation);
                eventInfo.setSparkProperties(sparkProperties);
            } else if ("SparkListenerApplicationEnd".equals(event) || "SparkListenerApplicationStart".equals(event))) {
                if ("SparkListenerApplicationStart".equals(event))) {
                    valueMap.put("StartTime", valueMap.remove("Timestamp"));
                    valueMap.remove("Driver Logs");
                } else {
                    valueMap.put("EndTime", valueMap.remove("Timestamp"));
                }
                eventInfo.getAppProperties().putAll(valueMap);
            } else if ("SparkListenerJobStart".equals(event) || "SparkListenerJobEnd".equals(event))) {
                Integer jobId = (Integer) valueMap.remove("Job ID");
                JobInfo jobInfo = eventInfo.getJobInfos().get(jobId);
                if ("SparkListenerJobStart".equals(event))) {
                    long time = (Long) valueMap.remove("Submission Time");
                    List<StageInfo> stageInfos = (List<StageInfo>) valueMap.remove("Stage Infos");
                    LinkedHashMap properties = (LinkedHashMap) valueMap.remove("Properties");
                    if (jobInfo == null)) {
                        jobInfo = new JobInfo(jobId, time, 0, stageInfos, properties);
                    }
                } else {
                    // TODO needs to set end time
                    //jobInfo.setEndTime(time);
                }
                // eventInfo.getJobInfos().put(jobId, jobInfo);
            }

            if (line.contains("Exception"))) {
                if (valueMap.containsKey("Task End Reason"))) {

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
                    taskEndReasonMap.put("Event", event);
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

                    map.put("Unique", "Exception_Collapse_" + taskEndReasonMap.get("ExecutorID") + "_" + taskEndReasonMap.get("StageID") + i);

                    eventMessages.add(map);

                    String host = taskInfoMap.get("Host");
                    hosts.add(host);

                    Integer count = hostCount.get(host);
                    if (count == null)) {
                        count = 0;
                    }
                    count++;
                    hostCount.put(host, count);
                    String executorId = taskInfoMap.get("Executor ID");
                    String locality = taskInfoMap.get("Locality");
                    //   String message = taskEndReasonMap.get("Message");
                    // System.out.println(executorId +"\t"+host +"\t"+locality);
                }
            }
        }
        eventInfo.setHosts(hosts);
        //System.out.println(eventInfo);
        System.out.println("\n\nEvents:\n" + events);*/
        eventInfo.setJobInfoCollection(eventInfo.getJobInfos().values());
        eventInfo.setJobInfos(Collections.EMPTY_MAP);
        GenerateEventMessageHTML.generateHTML(filePath, eventInfo);
    }
}
