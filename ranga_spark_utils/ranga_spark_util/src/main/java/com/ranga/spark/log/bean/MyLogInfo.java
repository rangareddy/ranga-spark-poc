package com.ranga.spark.log.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranga.spark.log.util.Tuple3;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyLogInfo implements Serializable {

    private static ObjectMapper mapper = new ObjectMapper();

    private boolean isEventLog;
    private boolean isApplicationLog;
    private PrintWriter printWriter;
    private Set<String> containersSet = new HashSet<>();

    public MyLogInfo(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public boolean isEventLog() {
        return isEventLog;
    }

    public void setEventLog(boolean eventLog) {
        isEventLog = eventLog;
    }

    public boolean isApplicationLog() {
        return isApplicationLog;
    }

    public void setApplicationLog(boolean applicationLog) {
        isApplicationLog = applicationLog;
    }

    public Tuple3<Boolean, String, String> addAppLog(String logLine, Tuple3<Boolean, String, String> tuple3) {
        boolean isLogAdded = false;
        boolean isException = tuple3._1();
        String spiltLog[] = logLine.split(" ");
        if (spiltLog.length > 2 && !isException) {
            String logType = spiltLog[2];
            if ((logType.contains("ERROR") || logType.contains("WARN")) && !logType.contains("DEBUG")) {
                if (logLine.contains("Exception") && !logLine.contains("Operation category READ is not supported in state standby")) {
                    if (!containersSet.contains(tuple3._2() + " on " + tuple3._3())) {
                        String container = "Container: " + tuple3._2() + " on " + tuple3._3();
                        printWriter.println("");
                        printWriter.println(container);
                        System.out.println(container);
                    }
                    System.out.println(logLine);
                    printWriter.println(logLine);
                    isLogAdded = true;
                }
            } else {
                isLogAdded = false;
            }
        } else if (isException &&
                !(logLine.contains("ERROR") || logLine.contains("WARN") || logLine.contains("DEBUG")) &&
                !logLine.contains("End of LogType")
        ) {
            System.out.println(logLine);
            printWriter.println(logLine);
            isLogAdded = true;
        } else {
            isLogAdded = false;
        }
        tuple3.setV1(isLogAdded);
        return tuple3;
    }

    public void addEventLog(String logLine) {
        if (logLine.contains("Exception") && !logLine.contains("Operation category READ is not supported in state standby")) {
            try {
                Map<String, Object> logLineMap = mapper.readValue(logLine, Map.class);
                Map<String, Object> taskEndReasonMap = (Map<String, Object>) logLineMap.get("Task End Reason");
                Map<String, Object> failureReasonMap = (Map<String, Object>) logLineMap.get("Failure Reason");

                if (taskEndReasonMap != null) {
                    String stackTrace = taskEndReasonMap.get("Full Stack Trace").toString();
                    printWriter.println(stackTrace);
                    String stackTraceArray[] = stackTrace.split("\\n");
                    for (int i = 0; i < stackTraceArray.length; i++) {
                        if (i < 5) {
                            System.out.println(stackTraceArray[i]);
                        }
                    }
                } else if (failureReasonMap != null) {
                    printWriter.println(failureReasonMap);
                }
            } catch (Exception ex) {
                printWriter.println(logLine);
            }
        }
    }
}