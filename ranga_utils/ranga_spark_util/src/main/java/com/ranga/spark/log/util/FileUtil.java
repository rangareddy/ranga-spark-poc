package com.ranga.spark.log.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

    public static List<String> getFileContent(String filePath) {
        return getFileContent(Paths.get(filePath));
    }

    public static List<String> getFileContent(Path path) {
        try {
            List<String> logLines = Files.readAllLines(path);
            return logLines;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static PrintWriter getPrintWriter(String outputFilePath) {
        try {
            File jobIdFile = new File(outputFilePath);
            if (jobIdFile.exists()) {
                jobIdFile.delete();
            }
            return new PrintWriter(new FileWriter(jobIdFile));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getFileSizeInfo(File file) {
        long fileSize = file.length();
        long fileSizeInMB = file.length() / (1024 * 1024);
        if (fileSizeInMB > 1000) {
            return (fileSizeInMB / 1024) + " gb";
        } else {
            if (fileSizeInMB > 0) {
                return fileSizeInMB + " mb";
            } else {
                return (fileSize / 1024) + " kb";
            }
        }
    }
}