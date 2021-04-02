package example

import java.io.File
import java.util
import java.util.concurrent.CountDownLatch

import org.apache.log4j.Logger
import org.apache.spark.launcher.SparkAppHandle.Listener
import org.apache.spark.launcher.{ SparkAppHandle, SparkLauncher}

/**
 * SparkLauncher for Spark applications.
 * Use this class to start Spark applications programmatically.
 */
object MySparkLauncher extends App with Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  if (args.length < 4) {
    logger.error("MySparkLauncher <master> <deploy_mode> <class_name> <resource_location> <appArgs>")
    logger.info("MySparkLauncher <yarn> <cluster> <org.apache.spark.examples.SparkPi> </path/to/spark-examples_2.11-2.3.2.3.1.5.0-152.jar> ")
    System.exit(0)
  }

  val data = args.take(4)
  val appName = "Launch MySpark Application"

  def setRequiredForLocal():Unit = {
    setEnv("SPARK_HOME", "/Users/rangareddy.avula/apache/spark")
    setEnv("HADOOP_CONF_DIR", "/Users/rangareddy.avula/apache/hadoop/hadoop-3.1.3/etc/hadoop")

    def setEnv(key: String, value: String): Unit = {
      try {
        val env:util.Map[String, String] = System.getenv
        val cl = env.getClass
        val field = cl.getDeclaredField("m")
        field.setAccessible(true)
        val writableEnv = field.get(env).asInstanceOf[util.Map[String, String]]
        writableEnv.put(key, value)
      } catch {
        case e: Exception =>
          throw new IllegalStateException("Failed to set environment variable", e)
      }
    }
  }

  val master = data(0)
  if(master.contains("local")) {
    setRequiredForLocal()
  }

  val sparkLauncher = new SparkLauncher()
    .setAppName(appName)
    .setMaster(master)
    .setDeployMode(data(1))
    .setMainClass(data(2))
    .setAppResource(data(3))
    .redirectError(new File("error.log"))
    .redirectOutput(new File("output.log"))
    .setVerbose(true)

  val env = Map(
    SparkLauncher.DRIVER_MEMORY -> "1g",
    SparkLauncher.EXECUTOR_MEMORY -> "1g",
    SparkLauncher.EXECUTOR_CORES-> "2",
    "spark.executor.instances"-> "1"
  )

  for((key, value) <- env) {
    sparkLauncher.setConf(key, value)
  }

  // Adding command line arguments
  val applicationArgs = args.dropRight(4)
  if (applicationArgs.length > 0) {
    sparkLauncher.addAppArgs(applicationArgs: _*)
  }

  // Monitors when the Spark app has been successfully started
  val countDownLatch = new CountDownLatch(1)

  // Starts a Spark application.
  sparkLauncher.startApplication(new Listener() {
    var oldAppState:SparkAppHandle.State = SparkAppHandle.State.UNKNOWN

    override def stateChanged(sparkAppHandle: SparkAppHandle): Unit = {
      val sparkAppId = sparkAppHandle.getAppId
      val newAppState = sparkAppHandle.getState
      if (sparkAppId != null) {
        logger.info("Spark job with appId: " + sparkAppId + ", State changed from " + oldAppState + " to " + newAppState)
      } else {
        logger.info("Spark job's state changed from " + oldAppState +" to "+newAppState)
        oldAppState = newAppState
      }

      if (newAppState != null && newAppState.isFinal) {
        println("State is Final "+newAppState.isFinal +" "+newAppState)
        countDownLatch.countDown()
        logger.info("Final app status: " + sparkAppHandle.getState)
        println("Final app status: " + sparkAppHandle.getState)
      } else if (newAppState != null && newAppState.isFinal && newAppState == SparkAppHandle.State.FAILED) {
        println("State is  "+newAppState.isFinal +" "+newAppState)

        logger.info("Stage failed with Spark Failure")
        println("Stage failed with Spark Failure")
        countDownLatch.countDown()
      }
    }

    override def infoChanged(sparkAppHandle: SparkAppHandle): Unit = {
      logger.debug("Info Changed ")
    }
  })
}