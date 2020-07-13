package com.ranga.spark.avro

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/* Ranga Reddy created on 25/06/20 */
object TestDF extends App {

    val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples")
    val spark = SparkSession.builder().config(conf).getOrCreate();

    val sampleDF = spark.read.option("inferSchema", "true").csv("/Users/rangareddy.avula/work/spark_cases/691786/REP_CONSOLE_A_IDI.csv");
    sampleDF.printSchema()
    val head = sampleDF.first()

    val filterDF = sampleDF.filter( row => row != head)

    filterDF.show(2, truncate = 0)

    val data1 = spark.sql("select * from sample_data1")
    val data2 = spark.sql("select * from sample_data1")
    //val df1 = data1.where(col('s_no').eq(100));
    // df1.show()

}
