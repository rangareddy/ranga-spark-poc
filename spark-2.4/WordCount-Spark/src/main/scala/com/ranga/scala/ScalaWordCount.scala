package com.ranga.scala

import org.apache.spark.api.java.{JavaPairRDD, JavaRDD}
import org.apache.spark.sql.SparkSession

object ScalaWordCount {
  def main(args: Array[String]): Unit = {

    if (args.length < 2) {
      System.err.println("Usage: ScalaWordCount <appName> <fileName>")
      System.exit(1)
    }

    val appName = args(0)
    val fileName = args(1)

    val spark: SparkSession = SparkSession.builder.appName(appName).config("spark.master", "local[*]").getOrCreate
    val lines = spark.read.textFile(fileName).rdd

    val map: JavaRDD[String] = lines.flatMap(line => line.split(" "))
    val pairRDD: JavaPairRDD[String, Integer] = map.mapToPair((word: String) => new Tuple2[String, Integer](word, 1))
    val resultRDD: JavaPairRDD[String, Integer] = pairRDD.reduceByKey((i1: Integer, i2: Integer) => i1 + i2)
    resultRDD.foreach((result: Tuple2[String, Integer]) => System.out.println(result._1 + " " + result._2))
    spark.close()
  }
}
