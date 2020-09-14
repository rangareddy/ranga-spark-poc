package com.ranga.spark.log.application;

import com.ranga.spark.log.application.parser.LogParser;

import java.io.File;

public class SparkLogParserApplication {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("SparkLogParserApplication <file_path>");
            System.exit(0);
        }
        String folderPath = args[0];
        parseLogs(new File(folderPath));
    }

    private static void parseLogs(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File f : files) {
                    parseLogs(f);
                }
            } else {
                LogParser.parseLogs(file);
            }
        } else {
            throw new RuntimeException(file.getAbsolutePath() + " does not exist");
        }
    }
}
