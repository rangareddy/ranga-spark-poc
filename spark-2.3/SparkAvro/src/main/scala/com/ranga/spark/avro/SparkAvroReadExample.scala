package scala.com.ranga.spark.avro

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/* rangareddy.avula created on 11/06/20 */

object SparkAvroReadExample {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples")
        val spark = SparkSession.builder().config(conf).getOrCreate();

        val usersDF = spark.read.format("com.databricks.spark.avro").load("./src/main/resources/users.avro");
        usersDF.printSchema();
        usersDF.foreach(row => println(row))
        spark.close();
    }
}
