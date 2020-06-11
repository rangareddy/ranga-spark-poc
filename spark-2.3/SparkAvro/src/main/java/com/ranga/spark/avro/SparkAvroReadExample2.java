package com.ranga.spark.avro;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/* rangareddy.avula created on 11/06/20 */

public class SparkAvroReadExample2 {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        Dataset<Row> employeeDF = spark.read().format("avro").load("employees.avro");
        employeeDF.printSchema();
        employeeDF.foreach(employee -> {System.out.println(employee);});
        spark.close();
    }
}
