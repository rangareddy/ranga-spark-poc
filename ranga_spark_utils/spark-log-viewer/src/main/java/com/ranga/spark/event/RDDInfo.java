package com.ranga.spark.event;/* rangareddy.avula created on 29/06/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class RDDInfo implements Serializable {

    @JsonProperty("RDD ID")
    private int id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Number of Partitions")
    private int numPartitions;

    @JsonProperty("Storage Level")
    private StorageLevel storageLevel;

    @JsonProperty("Parent IDs")
    private List parentIds;

    @JsonProperty("Callsite")
    private String callSite;

    @JsonProperty("Scope")
    private Object scope;

    @JsonProperty("Number of Cached Partitions")
    private int numCachedPartitions;

    @JsonProperty("Memory Size")
    private long memSize;

    @JsonProperty("Disk Size")
    private long diskSize;
}