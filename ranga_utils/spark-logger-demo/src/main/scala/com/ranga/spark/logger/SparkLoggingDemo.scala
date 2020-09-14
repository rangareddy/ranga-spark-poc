package com.ranga.spark.logger

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

object SparkLoggingDemo extends App {

    val logger =  Logger.getLogger(this.getClass.getName.replace("$", ""))
    val appName = "Spark Logging Demo"

    logger.debug(s"Building the spark session using name $appName")
    val conf = new SparkConf().setAppName("Spark Logging Demo").setIfMissing("spark.master", "local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    logger.info("SparkSession successfully created")

    val rangeDS = spark.range(1, 100)
    logger.info("rangeDS created with range 1 to 100")

    rangeDS.take(10).foreach( rangeVal => {
        logger.info(s"Range value $rangeVal")
    })
    spark.close()
    logger.warn("Application successfully shutdowned.")
}
