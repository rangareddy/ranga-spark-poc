package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object ZipWithUniqueIdDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    val numbers = List( 2, 3, 4, 5, 1, 6, 9)

    val numberRDD = context.parallelize(numbers)

    val zipWithUniqueIdResult = numberRDD.zipWithUniqueId().collect().toList;
    println(s"zipWithUniqueIdResult ${zipWithUniqueIdResult}")

    context.stop()
}
