import org.apache.avro.SchemaBuilder
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.avro.functions._
import org.apache.avro.SchemaBuilder

object SparkAvroReferences extends App {

  val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Avro Write Example")
  val spark = SparkSession.builder().config(conf).getOrCreate()
  val servers = ""

  // from_avro and to_avro

  /*
  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", servers)
    .option("subscribe", "t")
    .load()
    .select(from_avro("key", SchemaBuilder.builder().stringType()).as("key"), from_avro("value", SchemaBuilder.builder().intType()).as("value"))

  // Convert structured data to binary from string (key column) and
  // int (value column) and save to a Kafka topic.
  dataDF
    .select(
      to_avro($"key").as("key"),
      to_avro($"value").as("value"))
    .writeStream
    .format("kafka")
    .option("kafka.bootstrap.servers", servers)
    .option("article", "t")
    .save()
  */

  // Reading json schema from file

  /*

  /tmp/user.avsc

  {
    "namespace": "example.avro",
    "type": "record",
    "name": "User",
    "fields": [
      {"name": "name", "type": "string"},
      {"name": "favorite_color", "type": ["string", "null"]}
    ]
  }

  from pyspark.sql.avro.functions import from_avro, to_avro
  jsonFormatSchema = open("/tmp/user.avsc", "r").read()
  */


  /*
      import org.apache.spark.sql.avro.SchemaConverters
      import org.apache.spark.sql.types.StructType
      val requiredType: StructType = SchemaConverters.toSqlType(AvroClass.getClassSchema).dataType.asInstanceOf[StructType]

      import org.apache.spark.sql.avro.SchemaConverters
      import org.apache.spark.sql.types.StructType
      val outPutSchemaStructType: StructType = SchemaConverters.toSqlType(Employee.getClassSchema).dataType.asInstanceOf[StructType]

      import org.apache.avro.Schema
      import org.apache.spark.sql.avro.SchemaConverters
      import org.apache.spark.sql.types.StructType

      def convertSchemaToStructType(schema: Schema): StructType =  {
        val structType: StructType = new StructType
        import scala.collection.JavaConversions._
        for (field <- schema.getFields)  {
          structType.add(field.name, SchemaConverters.toSqlType(field.schema).dataType)
        }
        return structType
      }
   */

  // from avro
/*
  # 1. Decode the Avro data into a struct.
  # 2. Filter by column "favorite_color".
  # 3. Encode the column "name" in Avro format.

  output = df\
  .select(from_avro("value", jsonFormatSchema).alias("user"))\
  .where('user.favorite_color == "red"')\
  .select(to_avro("user.name").alias("value")) */

  // Example with Schema Registry

  /*

  import org.apache.spark.sql.avro.functions._

// Read a Kafka topic "t", assuming the key and value are already
// registered in Schema Registry as subjects "t-key" and "t-value" of type
// string and int. The binary key and value columns are turned into string
// and int type with Avro and Schema Registry. The schema of the resulting DataFrame
// is: <key: string, value: int>.
val schemaRegistryAddr = "https://myhost:8081"
val df = spark
  .readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", servers)
  .option("subscribe", "t")
  .load()
  .select(
    from_avro($"key", "t-key", schemaRegistryAddr).as("key"),
    from_avro($"value", "t-value", schemaRegistryAddr).as("value"))

  */
}
