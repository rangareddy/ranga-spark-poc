package com.ranga.spark.avro;/* rangareddy.avula created on 11/06/20 */

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class SparkAvroWriteExample {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Write Examples");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Ranga", 10000, 1));
        employeeList.add(new Employee(2, "Vinod", 1000, 1));
        employeeList.add(new Employee(3, "Nishanth", 500000, 2));
        employeeList.add(new Employee(4, "Manoj", 25000, 1));
        employeeList.add(new Employee(5, "Yashu", 1600, 1));
        employeeList.add(new Employee(6, "Raja", 50000, 2));
        Dataset<Row> employeeDF = spark.createDataFrame(employeeList, Employee.class);

        employeeDF.coalesce(1).write().format("avro").mode("overwrite").save("employees.avro");
        spark.close();
    }
}
