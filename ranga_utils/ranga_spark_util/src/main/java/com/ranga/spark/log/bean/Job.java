package com.ranga.spark.log.bean;

import java.util.Date;

public class Job {

    private int jobId;
    private Date startDate;
    private Date endDate;
    private long totalDiff;
    private String timeTaken;
    private String jobName;

    public Job(int jobId, String jobName) {
        this.jobId = jobId;
        this.jobName = jobName;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalDiff=" + totalDiff +
                ", timeTaken='" + timeTaken + '\'' +
                ", jobName='" + jobName + '\'' +
                '}';
    }

    public long getTotalDiff() {
        return (getEndDate().getTime() - getStartDate().getTime()) / 1000;
    }

    public void setTotalDiff(long totalDiff) {
        this.totalDiff = totalDiff;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
