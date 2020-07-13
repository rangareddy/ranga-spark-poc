package com.ranga.spark.avro.delete

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/* Ranga Reddy created on 22/06/20 */
object TestAvroFile extends App {

    val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples")
    val spark = SparkSession.builder().config(conf).getOrCreate();

    //val testDF = spark.read.format("avro").load("src/main/resources/test");
    val testDF = spark.read.format("avro").load("/Users/rangareddy.avula/work/spark_cases/691355/cloudera.case/data.before");
    testDF.printSchema()

    testDF.show(truncate = false)

    testDF.repartition(1).write.format("avro").option("compression","snappy").option("mode", "append")save("output")

    val resultDF = spark.read.format("avro").load("output");
    resultDF.printSchema()

    resultDF.show(1, truncate = false)


}
