package com.ranga.spark;/* rangareddy.avula created on 10/05/20 */

import com.ranga.spark.container.ApplicationMasterContainer;
import com.ranga.spark.container.SparkContainerDataReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class SparkLogsReader {

    private static Logger LOG = LoggerFactory.getLogger(SparkLogsReader.class);

    public static void generateSparkLogs(final String dirPath, final String fileName) throws Exception {
        String applicationName = fileName.substring(0, StringUtils.ordinalIndexOf(fileName, "_", 3));
        List<File> containerFiles = SparkLogsSplitter.splitLogIntoContainers(dirPath, fileName, applicationName);
        LOG.info("Total container files {}", containerFiles.size());
        ApplicationMasterContainer applicationMasterContainer = SparkContainerDataReader.getContainerData(containerFiles);
        System.out.println(applicationMasterContainer);
    }
}