package com.ranga.spark.log.application;

import com.ranga.spark.log.application.parser.JobLogParser;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DisplayJobInformation {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("DisplayJobInformation <file_path> <job_ids_with_comma_separated>");
            System.exit(0);
        }
        String filePath = args[0];
        List<String> jobIds = Arrays.asList(args[1].split(","));
        JobLogParser.printJobIdLogs(jobIds, new File(filePath));
        System.out.println("DisplayJobInformation Done");
    }
}
