package com.ranga.spark.event;/* rangareddy.avula created on 30/05/20 */

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.spark.scheduler.StageInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
@AllArgsConstructor
public @Data class JobInfo implements Serializable {
    private int jobId;
    private long startTime;
    private long endTime;
    private List<StageInfo> stageInfos;
    private LinkedHashMap properties;
}