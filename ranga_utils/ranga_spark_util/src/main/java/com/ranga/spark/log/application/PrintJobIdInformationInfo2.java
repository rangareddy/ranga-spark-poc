package com.ranga.spark.log.application;

import com.ranga.spark.log.util.FileUtil;

import java.io.PrintWriter;
import java.util.List;

public class PrintJobIdInformationInfo2 {
    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("PrintJobIdInformationInfo <input_file_path> <output_file_path>");
            System.exit(0);
        }

        String filePath = args[0];
        String outputFilePath = args[1];
        List<String> logLines = FileUtil.getFileContent(filePath);
        PrintWriter allPrintWriter = FileUtil.getPrintWriter(outputFilePath);

        long saslRpcClientCount = 0;
        long clientCount = 0;
        long messageDecoderCount = 0;
        long transportRequestHandlerCount = 0;
        long protobufRpcEngineCount = 0;

        long oldTime = 0;
        boolean isInfo = false;
        for (String logLine : logLines) {
            /*String date = logLine.startsWith("2020") ? logLine.substring(0, 19) : "";
            if(!date.isEmpty()) {
                long newTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
                oldTime = oldTime == 0 ? newTime : oldTime;
                long diff = (newTime - oldTime);

                if(diff > 0) {
                   // System.out.println(logLine);

                    System.out.println(diff +" "+newTime +", "+oldTime);
                    oldTime = newTime;
                }
            }
               */

            if ((logLine.startsWith("2020") && logLine.contains("INFO")) || (!logLine.startsWith("2020") && isInfo)) {
                isInfo = true;
            } else {
                isInfo = false;
                continue;
            }

            if (logLine.contains("DEBUG") || logLine.contains("TRACE")) {
                if (logLine.contains("DEBUG DFSClient:") || logLine.contains("DEBUG DFSClient:")) {
                    continue;
                }
            }

            if (logLine.contains("SaslRpcClient:634 - wrapping token of length")
                    || logLine.contains("SaslRpcClient:615 - unwrapping token of length:")
                    || logLine.contains("SaslRpcClient:594 - reading next wrapped RPC packet")
            ) {
                saslRpcClientCount++;
            } else if (logLine.contains("Client:1123 - IPC Client (") || logLine.contains("Client:1064 - IPC Client")) {
                clientCount++;
            } else if (logLine.contains("MessageDecoder:47 - Received message")) {
                messageDecoderCount++;
            } else if (logLine.contains("TransportRequestHandler:224 - Sent result")) {
                transportRequestHandlerCount++;
            } else if (logLine.contains("ProtobufRpcEngine")) {
                protobufRpcEngineCount++;
            } else if (logLine.contains("org.apache.hadoop.hdfs.protocol.ClientProtocol.getBlockLocations")
                    || logLine.contains("HttpParser:1671 - ")
                    || logLine.contains("BlockManagerInfo:54")
                    || logLine.contains("ContextCleaner:54")
                    || logLine.contains("TaskSetManager:54")
            ) {

            } else {
                // System.out.println(logLine);
                allPrintWriter.println(logLine);
            }
        }

        allPrintWriter.close();
        System.out.println("saslRpcClientCount " + saslRpcClientCount + ", clientCount " + clientCount + ", messageDecoderCount " + messageDecoderCount + ", transportRequestHandlerCount"
                + transportRequestHandlerCount + ", protobufRpcEngineCount " + protobufRpcEngineCount);

        System.out.println("DOne");
    }
}