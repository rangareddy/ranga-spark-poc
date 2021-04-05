package com.ranga.spark.hbase.entity

case class Employee(key: String, fName: String, lName: String,
                    mName:String, addressLine:String, city:String,
                    state:String, zipCode:String)
