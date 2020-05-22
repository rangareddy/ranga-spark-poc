package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

object SubtractDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    val names1 = Seq("ranga", "ranga", "raja", "nishanth", "vinod")
    val names2 = Seq("nishanth", "vinod", "ranga", "nishanth", "vinod")

    val names1RDD = context.parallelize(names1)
    val names2RDD = context.parallelize(names2)

    val namesSubtractRDD = names1RDD.subtract(names2RDD)

    val subtractList = namesSubtractRDD.collect().toList
    println("Subtracted Results "+subtractList)

    context.stop()
}
