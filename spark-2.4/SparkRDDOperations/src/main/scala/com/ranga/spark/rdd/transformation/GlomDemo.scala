package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object GlomDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 2, 4, 5, 6, 4, 8, 7, 2)
    var numberRDD = context.parallelize(numbers)

    val glomRDD = numberRDD.glom().filter(value => !value.isEmpty).collect()
    glomRDD.foreach( values => println(values.toList))
    context.stop()

}
