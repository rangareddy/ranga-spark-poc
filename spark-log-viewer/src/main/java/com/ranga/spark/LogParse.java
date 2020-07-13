package com.ranga.spark;/* rangareddy.avula created on 08/07/20 */

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogParse {
    public static void main(String[] args) throws Exception {
        String folderName = "/Users/rangareddy.avula/work/spark_cases/691873/";


        Charset utf8 = StandardCharsets.UTF_8;
        String[] logFileNames = new File(folderName).list();
        for (String logFileName : logFileNames) {
            int index = logFileName.lastIndexOf(".");
            String fileName = logFileName.substring(0, index);
            String fileExtension = logFileName.substring(index + 1);
            if (fileExtension.equalsIgnoreCase("log") && !fileName.contains("_error")) {
                String logFile = folderName + logFileName;
                List<String> lines = Files.readAllLines(Paths.get(logFile));
                boolean isERROR = false;
                List<String> errorLines = new ArrayList<>();
                int count = 0;
                System.out.printf(" ======================================== %s ======================================== \n", fileName);

                for (String line : lines) {
                    if (isERROR && (line.contains("INFO") || line.contains("WARN") || line.contains("DEBUG"))) {
                        isERROR = false;
                    }

                    if (line.contains("ERROR") || isERROR) {
                        errorLines.add(line);
                        isERROR = true;

                        if (line.contains("ERROR")) {
                            count = 0;
                        }

                        if (count++ < 10) {
                            System.out.println(line);
                        }
                    }
                }

                String outputFileName = folderName + fileName + "_error." + fileExtension;
                File file = new File(outputFileName);
                if (!file.exists() && !errorLines.isEmpty()) {
                    Files.write(file.toPath(), errorLines, utf8);
                }
            }
        }
    }
}