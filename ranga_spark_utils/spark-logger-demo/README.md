# Spark Log4j configuration

Spark uses **Apache Log4j**, which can be configured through a properties file. 
By default, Spark uses **$SPARK_CONF_DIR/log4j.properties** to configure log4j.

## Standalone Mode
In standalone mode, Spark Streaming driver is running on the machine where you submit the job, and each Spark worker node 
will run an executor for this job. So you need to setup log4j for both driver and executor.

## YARN Mode
The basic unit of YARN is called container, which represents a certain amount of resource (currently memory and virtual CPU cores). 
Every container has its working directory — where logs are stored, and both the driver and the executors will each have their own container.

The YARN container log directory is typically available through the **spark.yarn.app.container.log.dir** spark property.

**YARN log aggregation:** YARN will collect all the logs associated to our application, and expose them using 
the yarn command line client. We can enable the log aggregation in **yarn-site.xml** by using following property:

```xml
<property>
    <name>yarn.log-aggregation-enable</name>
    <value>true</value>
</property>

<property>
    <name>yarn.nodemanager.log-aggregation.roll-monitoring-interval-seconds</name>
 <value>3600</value>
</property>
```

As you can see, both driver and executor use the same configuration file. That is because in yarn-cluster mode, driver 
is also run as a container in YARN. In fact, the spark-submit command will just quit after job submission.
