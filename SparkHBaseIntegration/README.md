# Spark HBase Integration

## Create the directory in edge node
```sh
mkdir -p /usr/apps/spark/spark-hbase
cd /usr/apps/spark/spark-hbase
```

## Build the project
```sh
mvn clean package -DskipTests
```

## Copy the jar and run script to edge node
```sh

scp target/spark-hase-integration-1.0.0-SNAPSHOT.jar username@host:/usr/apps/spark/spark-hbase
scp run_spark_hbase_integration.sh username@host:/usr/apps/spark/spark-hbase
```

## Run the shell script from edge node 
```sh
cd /usr/apps/spark/spark-hbase
sh run_spark_hbase_integration.sh
```

```sh
mkdir -p $HOME/spark-hbase && cd $HOME/spark-hbase && git clone https://github.com/hortonworks-spark/shc.git && cd shc
git checkout origin/branch-2.3 -b shc_core_2.3
mvn -T 4 clean package -DskipTests
cp $HOME/spark-hbase/shc/core/target/shc-core-1.1.2-2.3-s_2.11-SNAPSHOT.jar /tmp
cp $HOME/spark-hbase/shcexamples/target/shc-examples-1.1.2-2.3-s_2.11-SNAPSHOT.jar /tmp

sudo -u spark spark-submit --verbose \
--class org.apache.spark.sql.execution.datasources.hbase.examples.HBaseSource \
--master yarn --deploy-mode client \
--jars $HOME/spark-hbase/shc-core-1.1.2-2.3-s_2.11-SNAPSHOT.jar \
--files /usr/hdp/current/hbase-client/conf/hbase-site.xml \
$HOME/spark-hbase/shc-examples-1.1.2-2.3-s_2.11-SNAPSHOT.jar
```

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

```scala
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.fs.Path

val conf = HBaseConfiguration.create()
conf.addResource(new Path("/etc/hbase/conf/hbase-site.xml"))
new HBaseContext(sc, conf)

case class Employee(id:Long, name: String, age: Integer, salary: Float)

var employeeDS = Seq(
 Employee(1L, "Ranga Reddy", 32, 80000.5f),
 Employee(2L, "Nishanth Reddy", 3, 180000.5f),
 Employee(3L, "Raja Sekhar Reddy", 59, 280000.5f)
).toDS

val columnMapping = "id Long :key, name STRING e:name, age Integer e:age, salary FLOAT e:salary"
val format = "org.apache.hadoop.hbase.spark"
val tableName = "employees"

employeeDS.write.format(format).option("hbase.columns.mapping",columnMapping).option("hbase.table", tableName).save()
 
val df = spark.read.format(format).option("hbase.columns.mapping", columnMapping).option("hbase.table", tableName).load()
df.show(truncate=false)
```