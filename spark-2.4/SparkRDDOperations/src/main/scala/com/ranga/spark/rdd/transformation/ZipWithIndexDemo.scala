package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object ZipWithIndexDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    val numbers = List( 2, 3, 4, 5, 1, 6, 9)

    val numberRDD = context.parallelize(numbers)

    val zippedWithIndexRDD = numberRDD.zipWithIndex();
    val zippedWithIndexResult = zippedWithIndexRDD.collect().toList;
    println(s"zippedWithIndexResult ${zippedWithIndexResult}")

    context.stop()
}
