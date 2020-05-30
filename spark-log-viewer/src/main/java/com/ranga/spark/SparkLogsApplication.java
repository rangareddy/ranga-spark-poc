package com.ranga.spark;/* rangareddy.avula created on 11/05/20 */

public class SparkLogsApplication {
    public static void main(String[] args) throws Exception {
        String dirPath = "/Users/rangareddy.avula/ranga/spark-case/681166/";
        String fileName = "application_1588690063856_133115_yarn_log.txt";
        SparkLogsReader.generateSparkLogs(dirPath, fileName);
    }
}
