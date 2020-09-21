package scala.com.ranga.spark.avro

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import scala.com.ranga.spark.avro.bean.Employee

/* Ranga Reddy created on 12/06/20 */

object SparkAvroReadAndWriteExample extends App {

    val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples")
    val spark = SparkSession.builder().config(conf).getOrCreate();

    val employeeList = List(Employee(1, "Ranga", 10000.00f, 1),
        Employee(2, "Vinod", 1000.00f, 1),
        Employee(3, "Nishanth", 500000.00f, 2),
        Employee(4, "Manoj", 25000.03f, 1),
        Employee(5, "Yashu", 1600.343f, 1),
        Employee(6, "Raja", 50000.2423f, 2)
    );

    val employeeDF = spark.createDataFrame(employeeList);
    employeeDF.printSchema()

    //employeeDF.coalesce(1).write.format("com.databricks.spark.avro").mode("overwrite").save("employees.avro");

}
