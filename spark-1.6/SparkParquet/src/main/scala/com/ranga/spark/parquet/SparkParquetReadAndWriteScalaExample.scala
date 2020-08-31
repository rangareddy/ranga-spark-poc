package com.ranga.spark.parquet;

import org.apache.spark._
import org.apache.spark.sql._

/* Ranga Reddy created on 13/07/20 */
object SparkParquetReadAndWriteScalaExample extends App {

    val appName = this.getClass.getSimpleName().replace("$", "");
    val filePath = "employee_data"
    val conf = new SparkConf().setIfMissing("spark.master", "local[3]").setAppName(appName);
    val sc = new SparkContext(conf);
    val sqlContext = new SQLContext(sc);

    writeParquet(sqlContext);
    readParquet(sqlContext);

    def writeParquet(sqlContext:SQLContext):Unit = {
        // Getting the DataFrame
        val df = getDataFrame(sqlContext)

        // Write file to parquet
        df.write.mode("overwrite").parquet(filePath);
    }

    def readParquet(sqlContext:SQLContext):Unit = {
        // Read parquet file
        val dataFrame = sqlContext.read.parquet(filePath)
        display(dataFrame)
    }

    def getDataFrame(sqlContext:SQLContext) : DataFrame = {
        val employeeList = getEmployeeData()
        val employeeDF = sqlContext.createDataFrame(employeeList)
        display(employeeDF)
        employeeDF
    }

    def getEmployeeData() : List[Employee] = {
        val employeeList = List(Employee(1, "Ranga", 10000.00f, 1),
            Employee(2, "Vinod", 1000.00f, 1),
            Employee(3, "Nishanth", 500000.00f, 2),
            Employee(4, "Manoj", 25000.03f, 1),
            Employee(5, "Yashu", 1600.343f, 1),
            Employee(6, "Raja", 50000.2423f, 2)
        )
        employeeList
    }

    def display( dataFrame:DataFrame) = {
        // printSchema
        dataFrame.printSchema();
        // show contents
        dataFrame.show(5, false);
    }
}