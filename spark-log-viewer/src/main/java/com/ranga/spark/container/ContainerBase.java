package com.ranga.spark.container;/* rangareddy.avula created on 11/05/20 */

public class ContainerBase {

    protected String id;
    protected String host;
    protected String startDate;
    protected String endDate;

    public ContainerBase(String containerInfo) {
        String[] split = containerInfo.replace("Container: ", "").split(" ");
        id = split[0];
        host = split[2];
    }

    public ContainerBase(String id, String host) {
        this.id = id;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    @Override
    public String toString() {
        return "ContainerBase{" +
                "id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
