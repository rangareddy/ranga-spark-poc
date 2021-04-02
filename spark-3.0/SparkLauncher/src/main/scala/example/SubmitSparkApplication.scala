package example

import org.apache.log4j.Logger
import org.apache.spark.launcher.{SparkAppHandle, SparkLauncher}

/**
 * An example of submitting a Spark application in a programmatic manner
 */

//  java -cp target/Spark3Avro-1.0.0-SNAPSHOT.jar SubmitSparkApplication yarn client
//  org.apache.spark.examples.SparkPi $SPARK_HOME/examples/target/original-spark-examples_2.12-3.1.0-SNAPSHOT.jar
object SubmitSparkApplication extends App with Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  if (args.length < 4) {
    logger.error("SubmitSparkApplication <master> <deploy_mode> <class_name> <resource_location> <appArgs>")
    logger.info("SubmitSparkApplication <yarn> <cluster> <org.apache.spark.examples.SparkPi> </path/to/spark-examples_2.11-2.1.0.jar> ")
    System.exit(0)
  }

  // Spark Home
  // cdh/cdp - /opt/cloudera/parcels/SPARK2/lib/spark2/
  // hdp     -
  //  java_home - /usr/jdk64/jdk1.8.0_112
  //  spark_home - /usr/hdp/current/spark2-client/
  //  java -cp Spark3Avro-1.0.0-SNAPSHOT.jar SubmitSparkApplication

  /*val envParams = new Nothing
  envParams.put("YARN_CONF_DIR", "/path/to/hadoop-client/conf")
  envParams.put("HADOOP_CONF_DIR", "/path/to/hadoop-client/conf")
  envParams.put("SPARK_HOME", "/path/to/spark2.2-client")
  envParams.put("SPARK_PRINT_LAUNCH_COMMAND", "1") */

  val master = args(0) // Master
  val deployMode = args(1) // Deploy-mode
  val mainClass = args(2) // MainClass
  val appResource = args(3) // Specify user app jar path
  val appName = "Launch MySpark Application"

  // .setSparkHome("/home/knoldus/spark-1.4.0-bin-hadoop2.6")
  // .setAppResource("/home/knoldus/spark_launcher-assembly-1.0.jar")
  // .addJar("hdfs:///user/user/jars/log4j-api-2.7.jar")
  // .setAppResource("hdfs:///user/user/apps/hello-spark-0.0.1.jar")

  //launcher.addSparkArg("--executor-memory", yarnConfigs.executorMemory)
  //launcher.addSparkArg("--driver-memory", yarnConfigs.driverMemory)

  /*
  if (yarnConfigs.dynamicAllocation)  { launcher.setConf("spark.dynamicAllocation.enabled", "true")
    launcher.setConf("spark.shuffle.service.enabled", "true")
    launcher.setConf("spark.dynamicAllocation.minExecutors", String.valueOf(yarnConfigs.minExecutors))
    launcher.setConf("spark.dynamicAllocation.maxExecutors", String.valueOf(yarnConfigs.maxExecutors))
    }
    else  { launcher.setConf("spark.dynamicAllocation.enabled", "false")
    launcher.addSparkArg("--num-executors", String.valueOf(yarnConfigs.numExecutors))
    }
    if ((yarnConfigs.deployMode eq DeployMode.CLUSTER) && yarnConfigs.waitForCompletion)
    {
      launcher.setConf("spark.yarn.submit.waitAppCompletion", "true")
    }

    applyConfIfPresent("spark.yarn.principal", configs.credentialsConfigBean.principal, launcher.setConf)
    applyConfIfPresent("spark.yarn.keytab", configs.credentialsConfigBean.keytab, launcher.setConf)
    applyConfIfPresent("--proxy-user", yarnConfigs.proxyUser, launcher.addSparkArg)
    ApplicationLaunchFailureException
   */
  val launcher: SparkLauncher = new SparkLauncher()

  launcher
    .setAppName(appName)
    .setMaster(master)
    .setDeployMode(deployMode)
    .setAppResource(appResource)
    .setMainClass(mainClass)
    .setVerbose(true)

  launcher.setConf(SparkLauncher.DRIVER_MEMORY, "2g")
  launcher.setConf(SparkLauncher.EXECUTOR_MEMORY, "2g")
  launcher.setConf(SparkLauncher.EXECUTOR_CORES, "5")
  launcher.setConf("spark.executor.instances", "2")

  // launcher.redirectError(new File("submit.err.log"))
  // launcher.redirectOutput(new File("submit.out.log"));
  //  "--arg" "5"

  // Adding command line arguments
  val applicationArgs = args.drop(3)
  if (applicationArgs.length > 0) {
    // logger.info("Provided application arguments: {}", Array[AnyRef](pySparkUtils.getCsvStringFromArray(pySparkAppArgsArray)))
    launcher.addAppArgs(applicationArgs: _*)
  }

  // Start the execution of the application
  val sparkAppHandle: SparkAppHandle = launcher.startApplication()
  logger.info("Started application --> handle " + sparkAppHandle)

  // Poll until application gets submitted
  while (sparkAppHandle.getAppId == null) {
    logger.info("Waiting for application to be submitted: status= " + sparkAppHandle.getState)
    Thread.sleep(3000L)
  }
  logger.info("Submitted as " + sparkAppHandle.getAppId)

  val waitForCompletion = true
  if (waitForCompletion) {
    while (!sparkAppHandle.getState.isFinal) {
      logger.info(sparkAppHandle.getAppId + " status is " + sparkAppHandle.getState)
      Thread.sleep(3000L)
    }
    logger.info("Finished as " + sparkAppHandle.getState)
  } else {
    sparkAppHandle.disconnect
  }

  /*
  var failedFlag = false
  val maxRetryTimes = 3
  var currentRetryTimes = 0

  while ( !failedFlag || sparkAppHandle.getState != SparkAppHandle.State.FINISHED) {
    currentRetryTimes += 1
    // View the status of the application every 6s (UNKNOWN, SUBMITTED, RUNNING, FINISHED, FAILED, KILLED, LOST)
    Thread.sleep(6000L)
    val state = sparkAppHandle.getState
    val applicationId = sparkAppHandle.getAppId

    System.out.println("ApplicationId is " + applicationId)
    System.out.println("Current State is " + state)

    if ((applicationId == null && (state eq SparkAppHandle.State.FAILED)) && currentRetryTimes > maxRetryTimes) {
      System.out.println(String.format("Tried launching application for %s times but failed, exit.", maxRetryTimes))
      failedFlag = true
    }
  }*/

  /*
  import org.apache.spark.launcher.SparkAppHandle

  def stateChanged(handle: SparkAppHandle): Unit = {
    logger.info("STATE CHANGED")
    val sparkAppId = handle.getAppId
    val appState = handle.getState
    if (sparkAppId != null) logger.info("Spark job with app id: " + sparkAppId + ", State changed to: " + appState)
    else logger.info("Spark job's state changed to: " + appState)
    if (appState != null && appState.isFinal && appState == SparkAppHandle.State.FINISHED) {
      logger.info("Spark Job FINISHED without failure")
      countDownLatch.countDown
    }
    else if (appState != null && appState.isFinal && appState == SparkAppHandle.State.FAILED) {
      logger.info("Stage failed with SPARK FAILURE !!")
      countDownLatch.countDown
    }
  }
   */
}
