package com.ranga.spark.container;/* rangareddy.avula created on 11/05/20 */

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SparkContainerDataReader {
    static List<String> includeServices = new ArrayList<>();

    static {
        includeServices.add("yarn.ApplicationMaster");
        includeServices.add("client.TransportClientFactory");
        includeServices.add("yarn.YarnAllocator");
    }

    public static ApplicationMasterContainer getContainerData(List<File> containerFiles) throws Exception {
        List<Container> containerList = new ArrayList<>(containerFiles.size() - 1);
        ApplicationMasterContainer amContainer = new AMContainerBuilder(containerFiles.get(0)).build();

        for(int i=1; i<containerFiles.size(); i++) {
            File containerFile = containerFiles.get(i);
            List<String> containerFileData = Files.readAllLines(containerFile.toPath());
            Container container = null;
            for (String logFileLine : containerFileData) {
                if (logFileLine.startsWith("Container: ")) {
                    container = new Container(logFileLine);
                } else if (container != null && (logFileLine.contains("INFO") || logFileLine.contains("ERROR") || logFileLine.contains("WARN"))) {
                    int lineCount = 0;
                    int index = logFileLine.indexOf(" ");
                    String dateTime = "";
                    String logType = null;
                    while (index != -1) {
                        if (lineCount == 0) {
                            dateTime = logFileLine.substring(0, index);
                        } else if (lineCount == 1) {
                            dateTime += " " + logFileLine.substring(0, index);
                        } else if (lineCount == 2) {
                            logType = logFileLine.substring(0, index);
                        }
                        logFileLine = logFileLine.substring(index + 1);
                        index = logFileLine.indexOf(" ");
                        lineCount++;

                        if (lineCount == 3) {
                            break;
                        }
                    }
                    int colonIndex = logFileLine.indexOf(":");
                    String serviceName = logFileLine.substring(0, colonIndex).trim();
                    String message = logFileLine.substring(colonIndex + 1).trim();
                    container.buildContainer(dateTime, logType, serviceName, message);
                }
            }
            containerList.add(container);
        }
        amContainer.setContainerList(containerList);
        return amContainer;
    }
}