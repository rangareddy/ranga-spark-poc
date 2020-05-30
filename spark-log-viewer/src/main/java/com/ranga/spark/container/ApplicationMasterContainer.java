package com.ranga.spark.container;/* rangareddy.avula created on 11/05/20 */

import java.util.List;

public class ApplicationMasterContainer extends ContainerBase {

    private String status;
    private int exitCode;
    private List<Container> containerList;
    private String applicationAttemptId;

    public ApplicationMasterContainer(String containerInfo) {
        super(containerInfo);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public List<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    public String getApplicationAttemptId() {
        return applicationAttemptId;
    }

    public void setApplicationAttemptId(String applicationAttemptId) {
        this.applicationAttemptId = applicationAttemptId;
    }

    @Override
    public String toString() {
        return "ApplicationMasterContainer{" +
                "status='" + status + '\'' +
                ", exitCode=" + exitCode +
                ", containerList=" + containerList +
                ", applicationAttemptId='" + applicationAttemptId + '\'' +
                ", id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
