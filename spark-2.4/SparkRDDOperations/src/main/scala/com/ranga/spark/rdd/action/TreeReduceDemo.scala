package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 22/05/20 */
object TreeReduceDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var numberRDD = context.parallelize(numbers)
    var result = (sum:Int, value:Int) => {println(s"$sum ==> $value"); sum + value}
    val treeReduceResult = numberRDD.treeReduce(result)
    println(s"treeReduceResult ${treeReduceResult}")

    context.stop()
}
