package com.ranga.spark.log;

import com.ranga.spark.log.parser.LogParser;

public class SparkLogsParserApplication {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("SparkLogsParserApplication <file_path>");
            System.exit(0);
        }
        String filePath = args[0];
        LogParser.printLogExceptions(filePath);
    }
}
