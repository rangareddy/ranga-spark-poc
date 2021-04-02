package com.kumar.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SessionBuilder {
  private var session:SparkSession = null;
  def getSparkSession():SparkSession = {
      if(session == null) {
        val conf = new SparkConf().
          setAppName("SparkHbaseIntegration").
          setIfMissing("spark.master", "local[4]")

        session = SparkSession.builder()
          .config(conf)
          .getOrCreate()
      }
      session
  }
}
