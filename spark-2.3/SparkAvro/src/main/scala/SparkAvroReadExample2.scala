import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/* rangareddy.avula created on 11/06/20 */

object SparkAvroReadExample2 {
    def main(args: Array[String]): Unit = {

        val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples")
        val spark = SparkSession.builder().config(conf).getOrCreate();

        val employeeDF = spark.read.format("com.databricks.spark.avro").load("employees.avro");
        employeeDF.printSchema();
        employeeDF.foreach(employee => {println(employee);});
        spark.close();
    }
}
