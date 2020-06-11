import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

case class Employee( id:Long, name:String, salary:Float, deptId: Int)

object SparkAvroWriteExample {
    def main(args: Array[String]): Unit = {

        val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Read Examples")
        val spark = SparkSession.builder().config(conf).getOrCreate();

        val employeeList = List(Employee(1, "Ranga", 10000, 1),
            Employee(2, "Vinod", 1000, 1),
            Employee(3, "Nishanth", 500000, 2),
            Employee(4, "Manoj", 25000, 1),
            Employee(5, "Yashu", 1600, 1),
            Employee(6, "Raja", 50000, 2)
        );

        val employeeDF = spark.createDataFrame(employeeList);
        employeeDF.coalesce(1).write.format("com.databricks.spark.avro").mode("overwrite").save("employees.avro");
        spark.close();
    }
}
