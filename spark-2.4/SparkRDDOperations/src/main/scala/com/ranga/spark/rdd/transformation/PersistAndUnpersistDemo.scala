package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object PersistAndUnpersistDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    val numbers = List( 2, 3, 4, 5, 1, 6, 9)

    val numberRDD = context.parallelize(numbers)
    // persisting rdd
    val persistedRDD = numberRDD.persist()

    val take10 = persistedRDD.take(10).toList
    val take5 = persistedRDD.take(5).toList
    val filterNum = persistedRDD.filter( num => num % 3 == 0).collect().toList

    Thread.sleep(10000)

    // unpersisting rdd
    persistedRDD.unpersist()

    Thread.sleep(5000)

    context.stop()
}
