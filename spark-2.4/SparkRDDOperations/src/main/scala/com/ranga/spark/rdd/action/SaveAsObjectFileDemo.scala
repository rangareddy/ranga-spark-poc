package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object SaveAsObjectFileDemo extends App {

    def appName = this.getClass.getSimpleName
    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 6,  3, 5, 7,  10, 4, 9, 11, 2, 12, 13, 14, 8, 15)
    var numberRDD = context.parallelize(numbers)
    numberRDD.coalesce(1).saveAsObjectFile("object_file_data")

    context.stop()

}
