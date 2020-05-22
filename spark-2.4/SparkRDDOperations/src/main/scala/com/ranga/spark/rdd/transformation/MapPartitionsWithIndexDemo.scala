package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object MapPartitionsWithIndexDemo extends App {

    def appName = this.getClass.getSimpleName
    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15)
    var numberRDD = context.parallelize(numbers)

    val mapPartitionsRDD = numberRDD.mapPartitionsWithIndex(
        (index, iterator) => {
            val numList = iterator.toList
            println(s"Index $index Number List $numList")
            numList.map(x => x).iterator
        })

    mapPartitionsRDD.foreach(line => println(line))
    context.stop()

}
