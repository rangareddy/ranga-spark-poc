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

mvn dependency:tree  -Dincludes=io.netty >> dependency.log
