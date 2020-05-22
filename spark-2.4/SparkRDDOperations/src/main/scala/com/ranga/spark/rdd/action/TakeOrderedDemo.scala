package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object TakeOrderedDemo extends App {

    def appName = this.getClass.getSimpleName
    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 6,  3, 5, 7,  10, 4, 9, 11, 2, 12, 13, 14, 8, 15)
    var numberRDD = context.parallelize(numbers)

    val takeOrderedResult = numberRDD.takeOrdered(10)
    println("takeOrderedResult " + takeOrderedResult.toList)

    context.stop()

}
