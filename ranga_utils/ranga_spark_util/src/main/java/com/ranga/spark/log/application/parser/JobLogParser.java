package com.ranga.spark.log.application.parser;

import com.ranga.spark.log.bean.Job;
import com.ranga.spark.log.util.FileUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class JobLogParser implements Serializable {

    private static SimpleDateFormat formatter1 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    private static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void parseJobLogs(File file) throws Exception {
        List<String> jobLines = FileUtil.getFileContent(file.toPath());
        Map<Integer, Job> jobMap = new LinkedHashMap<>();

        for (String jobLine : jobLines) {
            String jobLineSplit[] = jobLine.split(" ");
            String date = null, time = null, claz = null, message = null;

            Date dateTime = null;
            int dateIndex = 0, timeIndex = 1, clazIndex = 3, clazLength = 1;
            SimpleDateFormat format = formatter1;
            if (jobLine.contains("INFO  DAGScheduler:54 - Got job") || jobLine.contains("INFO  DAGScheduler:54 - Job ")) {
                clazIndex = 4;
                clazLength = 3;
                format = formatter2;
            }

            date = jobLineSplit[dateIndex].replace("-", "/");
            time = jobLineSplit[timeIndex];
            claz = jobLineSplit[clazIndex];
            message = jobLine.substring(jobLine.indexOf(claz) + claz.length() + clazLength);
            dateTime = format.parse(date + " " + time);

            if (jobLine.contains("scheduler.DAGScheduler: Got job") || jobLine.contains("INFO  DAGScheduler:54 - Got job ")) {
                int jobId = Integer.parseInt(message.split(" ")[2]);
                String jobName = message.substring(message.indexOf("(") + 1, message.indexOf(")"));
                Job job = new Job(jobId, jobName);
                job.setStartDate(dateTime);
                jobMap.put(jobId, job);
            } else if (jobLine.contains("scheduler.DAGScheduler: Job ") || jobLine.contains("INFO  DAGScheduler:54 - Job ")) {
                int jobId = Integer.parseInt(message.split(" ")[1]);
                String timeTaken = message.substring(message.indexOf("took ") + 5);
                Job job = jobMap.get(jobId);
                job.setEndDate(dateTime);
                job.setTimeTaken(timeTaken);
                jobMap.put(jobId, job);
            }
        }

        Date endDate = jobMap.get(0).getStartDate();
        long totalSec = 0;
        System.out.println();
        for (Job job : jobMap.values()) {
            long diff = (job.getStartDate().getTime() - endDate.getTime()) / 1000;
            if (diff > 0) {
                if (job.getEndDate() != null) {
                    System.out.println(job.getJobId() + "\t" + job.getJobName() + "\t" + endDate.getTime() + "\t" + job.getStartDate().getTime()
                            + "\t" + job.getEndDate().getTime() + "\t" + diff + "\t" + job.getTimeTaken().replace(" s", ""));
                    totalSec += diff;
                }
            }
            endDate = job.getEndDate();
        }
        System.out.println("total diff in sec " + totalSec);
    }

    public static void printJobIdLogs(List<String> jobIds, File file) throws Exception {
        List<String> logLines = FileUtil.getFileContent(file.toPath());

        int countJobs = 0;
        String jobIdsFile = file.getParent() + "/jobIds_" + String.join("_", jobIds) + ".log";
        PrintWriter allPrintWriter = FileUtil.getPrintWriter(jobIdsFile);

        for (String jobId : jobIds) {
            boolean isJobEnded = false;
            boolean isJobFound = false;
            List<String> lastJobLogs = null;
            List<String> currentJobLogs = null;
            Integer jobIdValue = Integer.parseInt(jobId);

            for (String logLine : logLines) {

                if (logLine.contains("Got job " + (jobIdValue - 1) + " ")) {
                    lastJobLogs = new ArrayList<>();
                    allPrintWriter.println("\n================================   Job " + (Integer.parseInt(jobId) - 1) + " Start  ====================================\n");
                }
                if (currentJobLogs != null) {
                    if (logLine.contains("Got job " + jobId + " ")) {
                        allPrintWriter.println("\n================================   Job " + jobId + " Start   ====================================\n");
                    }
                    allPrintWriter.println(logLine);
                    if (logLine.contains("Job " + jobId + " finished:")) {
                        allPrintWriter.println("\n================================   Job " + jobId + " Start   ====================================\n");
                    }
                } else if (!isJobFound && lastJobLogs != null) {
                    allPrintWriter.println(logLine);
                    if (logLine.contains("Job " + (jobIdValue - 1) + " finished:")) {
                        allPrintWriter.println("\n================================   Job " + (Integer.parseInt(jobId) - 1) + " End  ====================================\n");
                    }
                }

                if (logLine.contains("Got job " + jobId + " ")) {
                    isJobFound = true;
                    currentJobLogs = new ArrayList<>();
                } else if (logLine.contains("Job " + jobId + " finished:")) {
                    isJobEnded = true;
                }
                if (isJobFound && isJobEnded) {
                    isJobEnded = false;
                    isJobFound = false;
                    if (++countJobs == jobIds.size()) {
                        break;
                    }
                    continue;
                }
            }
        }
        allPrintWriter.close();
    }
}