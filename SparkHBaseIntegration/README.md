# Spark HBase Integration

## HDP - Spark HBase Integration

### Launch HBase Shell and create Employee table
```
# hbase shell
# create 'employees', 'e'
```

### Launch the Spark Shell
```sh
spark-shell --master yarn --executor-cores 5 --deploy-mode client \
--jars /usr/hdp/current/hbase-client/lib/hbase-client.jar,\
/usr/hdp/current/hbase-client/lib/hbase-common.jar,\
/usr/hdp/current/hbase-client/lib/hbase-server.jar,\
/usr/hdp/current/hbase-client/lib/hbase-mapreduce.jar,\
/usr/hdp/current/hbase-client/lib/hbase-protocol.jar,\
/usr/hdp/current/hbase-client/lib/hbase-protocol-shaded.jar,\
/usr/hdp/current/hbase-client/lib/hbase-spark.jar,\
/usr/hdp/current/hbase-client/lib/hbase-zookeeper.jar,\
/usr/hdp/current/hbase-client/lib/hbase-hadoop2-compat.jar,\
/usr/hdp/current/hbase-client/lib/hbase-shaded-netty-2.2.0.jar,\
/usr/hdp/current/hbase-client/lib/hbase-shaded-protobuf-2.2.0.jar,\
/usr/hdp/current/hbase-client/lib/hbase-shaded-miscellaneous-2.2.0.jar,\
/usr/hdp/current/hbase-client/lib/hbase-replication.jar,\
/usr/hdp/current/hbase-client/lib/hbase-procedure.jar,\
/usr/hdp/current/hbase-client/lib/hbase-metrics.jar,\
/usr/hdp/current/hbase-client/lib/hbase-http.jar,\
/usr/hdp/current/hbase-client/lib/hbase-hadoop-compat.jar,\
/usr/hdp/current/hbase-client/lib/phoenix-server.jar \
--files /etc/hbase/conf/hbase-site.xml
```

### Execute the following code
```scala
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.fs.Path

val conf = HBaseConfiguration.create()
conf.addResource(new Path("/etc/hbase/conf/hbase-site.xml"))
new HBaseContext(sc, conf)

// creating the employee dataset
case class Employee(id:Long, name: String, age: Integer, salary: Float)

var employeeDS = Seq(
 Employee(1L, "Ranga Reddy", 32, 80000.5f),
 Employee(2L, "Nishanth Reddy", 3, 180000.5f),
 Employee(3L, "Raja Sekhar Reddy", 59, 280000.5f)
).toDS

val columnMapping = "id Long :key, name STRING e:name, age Integer e:age, salary FLOAT e:salary"
val format = "org.apache.hadoop.hbase.spark"
val tableName = "employees"

// writting the data to hbase table
employeeDS.write.format(format).option("hbase.columns.mapping",columnMapping).option("hbase.table", tableName).save()

// reading the data from hbase table
val df = spark.read.format(format).option("hbase.columns.mapping", columnMapping).option("hbase.table", tableName).load()
df.show(truncate=false)
```