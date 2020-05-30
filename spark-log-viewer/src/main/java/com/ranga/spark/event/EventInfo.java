package com.ranga.spark.event;/* rangareddy.avula created on 30/05/20 */

import java.io.Serializable;
import java.util.List;
import java.util.Map;

class EventInfo implements Serializable  {

    private String applicationName;
    private String sparkVersion;
    private Map<String, String> jvmInformation;
    private Map<String, String> sparkProperties;
    private Map<String, String> classpathEntries;
    private List<Map<String, String>> eventMessages;

}
