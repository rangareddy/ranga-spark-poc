package com.ranga.spark.log.application.parser;

import java.io.*;
import java.util.Scanner;

public class LogParser implements Serializable {

    public static void parseLogs(File logFile) {
        String allPrintPath = logFile.getParent() + "/all_" + logFile.getName();
        String jobPrintPath = logFile.getParent() + "/job_" + logFile.getName();
        File allFile = getFile(allPrintPath);
        File jobFile = getFile(jobPrintPath);

        try (PrintWriter allPrintWriter = new PrintWriter(new FileWriter(allFile)); PrintWriter jobPrintWriter = new PrintWriter(new FileWriter(jobFile));
             FileInputStream inputStream = new FileInputStream(logFile); Scanner myReader = new Scanner(inputStream, "UTF-8");
        ) {
            long count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("DAGScheduler:")) {
                    count++;
                    allPrintWriter.println(data);
                    if (count % 10000 == 0) {
                        allPrintWriter.flush();
                    }
                    if (data.contains("Got job") || data.contains(": Job ")) {
                        jobPrintWriter.println(data);
                    }
                }
            }
            System.out.println("done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static File getFile(String outputDir) {
        File outputFile = new File(outputDir);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        return outputFile;
    }
}