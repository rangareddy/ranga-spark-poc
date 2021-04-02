package com.ranga.spark.avro

import com.ranga.spark.avro.bean.Employee
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.avro.functions._
import org.apache.spark.sql.functions._

object SparkAvroWriteExample {
    def main(args: Array[String]): Unit = {

        val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Write Example")
        val spark = SparkSession.builder().config(conf).getOrCreate();

        val employeeList = List(Employee(1, "Ranga", 30, 10000.2f),
            Employee(2, "Vinod", 25, 1000.67f),
            Employee(3, "Nishanth", 2, 500000.76f),
            Employee(4, "Manoj", 14, 25000.45f),
            Employee(5, "Yashu", 10, 1600.98f),
            Employee(6, "Raja", 50, 50000.63f)
        )

        // Define Avro Schema
        val avro_schema = """{
                "type": "record",
                "name": "employeeRecords",
                "fields": [
                        {"name": "id", "type": "long", "default": "1"},
                        {"name": "name2", "type": "string", "default": ""},
                        {"name": "age", "type": "int", "default": "1"},
                        {"name": "salary", "type": "float", "default": "1f"}
                ]
        }"""

      import org.apache.avro.Schema
      import org.apache.avro.SchemaBuilder
      val AVRO_SCHEMA = SchemaBuilder.record("customRecord").
        namespace("com.big.data.spark.converter.avro").
        fields.name("int_field").`type`.intType.noDefault
              .name("str_field").`type`.stringType.noDefault
              .name("arr_str_field").`type`.array.items.stringType.noDefault
              .name("map_str_field").`type`.map.values.stringType.noDefault
        .endRecord

        val employeeDF = spark.createDataFrame(employeeList);
        employeeDF.show(5, false)
        employeeDF.printSchema()

        val df_avro_only = employeeDF.select(struct("*").alias("avro"))
        df_avro_only.show(5, false)
        df_avro_only.printSchema()

        println("to_avro")
        val df_test = employeeDF.select(to_avro(struct("*")).alias("to_avro_df"))
        df_test.show(5, false)
        df_test.printSchema()

        val df_decoded = df_test.select(from_avro(col("to_avro_df"), avro_schema).as("employee"))
          //.select("employee.*")
        df_decoded.show(5, false)
        df_decoded.printSchema()

        //employeeDF.coalesce(1).write.format("avro").mode("overwrite").save("employees");
        spark.close();
    }
}