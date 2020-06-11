package com.ranga.spark.event;

/* rangareddy.avula created on 30/05/20 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.spark.scheduler.StageInfo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class JobInfo implements Serializable {

    @JsonProperty("Event")
    private String event;

    @JsonProperty("Job ID")
    private int jobId;

    @JsonProperty("Submission Time")
    private long startTime;

    @JsonIgnore
    private long endTime;

    @JsonIgnore
    private String jobResult;

    @JsonProperty("Stage Infos")
    private List<StageInfo> stageInfos;

    @JsonProperty("Properties")
    private LinkedHashMap properties;

    //Job ID, Submission Time, Stage Infos, Stage IDs,
}