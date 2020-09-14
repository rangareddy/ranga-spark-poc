package com.ranga.spark.log.parser;

import com.ranga.spark.log.bean.MyLogInfo;
import com.ranga.spark.log.util.FileUtil;
import com.ranga.spark.log.util.Tuple3;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class LogParser {

    public static void printLogExceptions(String filePath) {
        File file = new File(filePath);
        long fileSize = file.length();
        System.out.println("Parsing the logs for file <" + file.getName() + "> with size " + FileUtil.getFileSizeInfo(file));
        String outputFile = (file.getParent() == null ? "." : file.getParent()) + "/error_" + file.getName();
        File outFile = new File(outputFile);

        long lengthPerPercent = 100 / fileSize;
        long messageLength = 0, startTime = System.currentTimeMillis();
        PrintWriter printWriter = FileUtil.getPrintWriter(outputFile);
        MyLogInfo myLogInfo = new MyLogInfo(printWriter);
        boolean isLogAdded = false, isEventLog = false, isAppLog = false;
        Tuple3<Boolean, String, String> messageInfo = null;
        try (FileInputStream inputStream = new FileInputStream(filePath); Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String logMessage = scanner.nextLine();
                messageLength += logMessage.length();
                long progress = Math.round(lengthPerPercent * messageLength);
                long currentUnixTime = System.currentTimeMillis();
                if ((currentUnixTime - startTime) / 1000 >= 60) {
                    System.out.println("File reading " + progress + "% completed");
                    startTime = currentUnixTime;
                }

                if (!(isEventLog && isAppLog)) {
                    if (!isEventLog && logMessage.contains("{\"Event\"")) {
                        isEventLog = true;

                    } else if (logMessage.contains("Container: container_")) {
                        isAppLog = true;
                    }
                }

                if (isEventLog) {
                    myLogInfo.addEventLog(logMessage);
                } else if (isAppLog) {
                    if (logMessage.contains("Container: container_")) {
                        String containerInfo[] = logMessage.replace("Container: ", "").replace(" on ", " ").split(" ");
                        String containerName = containerInfo[0];
                        String host = containerInfo[1];
                        messageInfo = new Tuple3<>(false, containerName, host);
                    }
                    messageInfo = myLogInfo.addAppLog(logMessage, messageInfo);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception occurred while parsing logs : " + ex.getMessage());
        }
        printWriter.close();
        try {
            System.out.println("Event log exceptions written to " + outFile.getCanonicalPath() + " path");
        } catch (Exception ex) {

        }


    }
}
