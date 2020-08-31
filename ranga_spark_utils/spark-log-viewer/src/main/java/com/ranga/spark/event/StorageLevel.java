package com.ranga.spark.event;/* rangareddy.avula created on 29/06/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class StorageLevel implements Serializable {

    @JsonProperty("Use Disk")
    boolean useDisk;

    @JsonProperty("Use Memory")
    boolean useMemory;

    @JsonProperty("Use OffHeap")
    boolean useOffHeap;

    @JsonProperty("Deserialized")
    boolean deserialized;

    @JsonProperty("Replication")
    int replication;
}
