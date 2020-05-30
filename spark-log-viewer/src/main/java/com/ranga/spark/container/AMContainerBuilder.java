package com.ranga.spark.container;/* rangareddy.avula created on 11/05/20 */

import com.ranga.spark.container.ApplicationMasterContainer;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class AMContainerBuilder {
    private File file;

    public AMContainerBuilder(File file) {
        this.file = file;
    }

    public ApplicationMasterContainer build() {
        try {
            List<String> logMessages = Files.readAllLines(file.toPath());
            String logFirstLine = logMessages.get(0);
            ApplicationMasterContainer amContainer = new ApplicationMasterContainer(logFirstLine);

            for(int i=1; i<logMessages.size(); i++) {
                String logMessage = logMessages.get(i);
                if(!logMessage.contains("==========================")) {
                    if(logMessage.contains("INFO") || logMessage.contains("ERROR") || logMessage.contains("WARN")) {
                        int lineCount = 0;
                        int index = logMessage.indexOf(" ");
                        String dateTime = "";
                        String logType = null;
                        while (index != -1) {
                            if(lineCount == 0) {
                                dateTime = logMessage.substring(0, index);
                            } else if(lineCount == 1){
                                dateTime += " "+logMessage.substring(0, index);
                            } else if(lineCount == 2) {
                                logType = logMessage.substring(0, index);
                            }
                            logMessage = logMessage.substring(index+1);
                            index = logMessage.indexOf(" ");
                            lineCount++;

                            if(lineCount == 3) {
                                break;
                            }
                        }

                        int colonIndex = logMessage.indexOf(":");
                        String serviceName = logMessage.substring(0, colonIndex).trim();
                        String message = logMessage.substring(colonIndex+1).trim();
                        if(serviceName.equals("yarn.ApplicationMaster") && message.contains("ApplicationAttemptId: appattempt_")) {
                            amContainer.setApplicationAttemptId(message.replace("ApplicationAttemptId: ", ""));
                        } else {

                        }
                    } else {
                        System.out.println(i +" == " +logMessage);
                    }
                }
            }
            return amContainer;
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred while getting AMContainer logs");
        }
    }
}
