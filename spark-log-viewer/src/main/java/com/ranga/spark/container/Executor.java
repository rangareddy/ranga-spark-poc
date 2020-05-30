package com.ranga.spark.container;/* rangareddy.avula created on 10/05/20 */

import java.util.ArrayList;
import java.util.List;

public class Executor {

    private String id;
    private String host;
    private String startDate;
    private String endDate;
    private List<String> logs;

    public Executor(String id, String host, String dateTime) {
        this.id = id;
        this.host = host;
        this.startDate = dateTime;
        logs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "Executor{" +
                "id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", logs=" + logs +
                '}';
    }
}
