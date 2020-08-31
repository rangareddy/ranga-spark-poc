package com.ranga.spark.event;/* rangareddy.avula created on 29/06/20 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data
class StageInfo implements Serializable {

    @JsonProperty("Details")
    public String details;
    @JsonProperty("Stage ID")
    private int stageId;
    @JsonProperty("Stage Attempt ID")
    private int attemptId;
    @JsonProperty("Stage Name")
    private String name;

    @JsonProperty("Number of Tasks")
    private int numTasks;
    @JsonProperty("RDD Info")
    private List<RDDInfo> rddInfos;
    @JsonProperty("Parent IDs")
    private List<Object> parentIds;

    // todo need to handle Accumulables
}
