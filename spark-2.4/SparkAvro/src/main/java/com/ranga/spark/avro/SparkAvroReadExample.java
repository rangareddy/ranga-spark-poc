package com.ranga.spark.avro;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/* rangareddy.avula created on 11/06/20 */

public class SparkAvroReadExample {
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().master("local[1]")
                .appName("Spark Avro Read Examples")
                .getOrCreate();

        Dataset<Row> usersDF = spark.read().format("avro").load("./src/main/resources/users.avro");
        usersDF.printSchema();
        usersDF.foreach(row -> {System.out.println(row);});
        spark.close();
    }
}
