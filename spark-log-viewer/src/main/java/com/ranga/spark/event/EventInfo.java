package com.ranga.spark.event;/* rangareddy.avula created on 30/05/20 */

import lombok.*;

import java.io.Serializable;
import java.util.*;

public @Data  class EventInfo implements Serializable  {

    private String sparkVersion;
    private Map<String, Object> appProperties = new HashMap<>();
    private Map<String, String> jvmInformation;
    private Map<String, String> sparkProperties;
    private Map<String, String> classpathEntries;
    private List<Map<String, String>> eventMessages = new ArrayList<>();
    private Set<String> hosts = new HashSet<>();
    private Map<Integer, JobInfo> jobInfos = new TreeMap<>();
}
