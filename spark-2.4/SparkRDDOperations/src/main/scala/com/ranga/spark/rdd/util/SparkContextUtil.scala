package com.ranga.spark.rdd.util

import org.apache.spark.{SparkConf, SparkContext}

/* Ranga Reddy created on 21/05/20 */

object SparkContextUtil {

    def getSparkContext(appName:String, master:String) : SparkContext = {
        val conf:SparkConf = new SparkConf().setAppName(appName).setMaster(master)
        val sc:SparkContext = new SparkContext(conf)
        return sc
    }

    def getSparkContext(appName:String) : SparkContext = {
        return getSparkContext(appName, "local[*]")
    }

  /*  def getSparkContext(claz: Class[T] ) : SparkContext = {
        return getSparkContext(claz.getSimpleName, "local[*]")
    }*/
}
