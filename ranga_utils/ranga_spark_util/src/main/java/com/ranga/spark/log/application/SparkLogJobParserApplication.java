package com.ranga.spark.log.application;

import com.ranga.spark.log.application.parser.JobLogParser;

import java.io.File;

public class SparkLogJobParserApplication {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("SparkLogJobParserApplication <file_path>");
            System.exit(0);
        }

        String filePath = args[0];
        JobLogParser.parseJobLogs(new File(filePath));
    }
}
