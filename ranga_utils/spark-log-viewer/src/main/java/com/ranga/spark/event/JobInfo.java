package com.ranga.spark.event;

/* rangareddy.avula created on 30/05/20 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class JobInfo implements Serializable {

    @JsonProperty("Job ID")
    private int jobId;

    @JsonProperty("Submission Time")
    private long startTime;

    private long endTime;

    private String jobResult;

    @JsonProperty("Stage Infos")
    private List<StageInfo> stageInfos;

    @JsonProperty("Stage IDs")
    private List<Integer> stageIds;

    @JsonProperty("Properties")
    private LinkedHashMap properties;
}