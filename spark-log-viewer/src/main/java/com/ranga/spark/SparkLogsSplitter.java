package com.ranga.spark;/* rangareddy.avula created on 11/05/20 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SparkLogsSplitter {

    public static final String CONTAINER = "Container: ";
    public static final String LOG_TYPE = "LogType:";
    public static final String LOG_CONTENTS = "Log Contents:";
    public static final String LOG_UPLOAD_TIME = "Log Upload Time:";
    public static final String LOG_LENGTH = "LogLength:";
    private static Logger LOG = LoggerFactory.getLogger(SparkLogsSplitter.class);

    public static List<File> splitLogIntoContainers(String dirPath, String fileName, String applicationName) throws Exception {

        String filePath = dirPath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println(file.getAbsolutePath() + " does not exist");
            throw new FileNotFoundException(file.getAbsolutePath() + " doesn't exist");
        }
        List<String> logMessageLines = Files.readAllLines(file.toPath());
        String outputDir = dirPath + applicationName;
        File outputDirFile = new File(outputDir);

        LOG.debug("Splitting the log data into container files.");
        if (outputDirFile.exists()) {
            LOG.warn("Output directory <" + outputDirFile.getAbsolutePath() + "> already exists.");
            outputDirFile.delete();
        }
        outputDirFile.mkdirs();

        List<File> containerFiles = new ArrayList<>();
        int logTypeCount = 0;
        File containerFile = null;
        StringBuilder sb = new StringBuilder();
        for (String logFileLine : logMessageLines) {
            logFileLine = logFileLine.trim();
            if (!logFileLine.isEmpty()) {
                if (logFileLine.startsWith(CONTAINER)) {
                    String[] split = logFileLine.replace(CONTAINER, "").split(" ");
                    containerFile = new File(outputDirFile + "/" + split[0]);
                    containerFile.createNewFile();
                    containerFiles.add(containerFile);
                    // LOG.info("Created container <{}> on host <{}>", containerFile.getName(), split[2]);
                    sb.append(logFileLine);
                } else {
                    if (logFileLine.contains(LOG_TYPE) || logFileLine.contains(LOG_CONTENTS)) {
                        logTypeCount++;
                    } else if (!(logFileLine.startsWith(LOG_UPLOAD_TIME) || logFileLine.startsWith(LOG_LENGTH))) {
                        sb.append("\n").append(logFileLine);
                    }
                }

                if (logTypeCount == 4) {
                    LOG.debug("Writting data container <{}>", containerFile.getName());
                    Files.write(containerFile.toPath(), sb.toString().getBytes());
                    sb = new StringBuilder();
                    //  LOG.info("Data is successfully written to container <{}>", containerFile.getName());
                    logTypeCount = 0;
                }
            }
        }
        Collections.sort(containerFiles, Comparator.comparing(File::getName));
        return containerFiles;
    }
}