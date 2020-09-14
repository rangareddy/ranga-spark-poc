package com.ranga.spark.log.application;

import com.ranga.spark.log.util.FileUtil;

import java.io.PrintWriter;
import java.util.List;

public class PrintJobIdInformation {
    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("PrintJobIdInformation <input_file_path> <output_file_path>");
            System.exit(0);
        }

        String filePath = args[0];
        String outputFilePath = args[1];
        List<String> logLines = FileUtil.getFileContent(filePath);
        PrintWriter allPrintWriter = FileUtil.getPrintWriter(outputFilePath);

        for (String logLine : logLines) {
            if (!(logLine.contains("memory.MemoryStore:") || logLine.contains("scheduler.TaskSetManager:")
                    || logLine.contains("spark.MapOutputTrackerMasterEndpoint:")
                    || logLine.contains("storage.BlockManagerInfo:")
                    || logLine.contains("scheduler.DAGScheduler: Parents of final stage:") || logLine.contains("DAGScheduler:54 - Parents of final stage")
                    || logLine.contains("scheduler.DAGScheduler: looking for newly runnable stages")
                    || logLine.contains("scheduler.DAGScheduler: running:") || logLine.contains("DAGScheduler:54 - running")
                    || logLine.contains("scheduler.DAGScheduler: waiting:") || logLine.contains("DAGScheduler:54 - waiting")
                    || logLine.contains("scheduler.DAGScheduler: failed:") || logLine.contains("DAGScheduler:54 - failed")
                    || logLine.contains("scheduler.DAGScheduler: Missing parents:") || logLine.contains("DAGScheduler:54 - Missing parents")
                    || logLine.contains("DAGScheduler:58 - missing")
                    || logLine.contains("YarnScheduler:58 - parentName")
                    || logLine.contains("TaskSetManager:58 - No tasks for")
                    || logLine.contains("spark.ContextCleaner: Cleaned ") || logLine.contains("ContextCleaner:58")
                    || logLine.contains("spark.SparkContext: Created broadcast") || logLine.contains("SparkContext:54 - Created broadcast")
                    || logLine.contains("cluster.YarnClusterScheduler: Adding task set ") || logLine.contains("YarnScheduler:54 - Adding task")
                    || logLine.contains("cluster.YarnClusterScheduler: Removed TaskSet") || logLine.contains("YarnScheduler:54 - Removed TaskSet")
                    || logLine.contains("codegen.CodeGenerator")
                    || logLine.contains("HttpParser:1671 - HEADER")
                    || logLine.contains("BlockInfoManager:62 - Task")
            )) {
                allPrintWriter.println(logLine);
            }
        }
        allPrintWriter.close();
        System.out.println("DOne");
    }
}