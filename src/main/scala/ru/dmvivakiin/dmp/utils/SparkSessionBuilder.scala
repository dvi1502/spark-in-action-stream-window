package ru.dmvivakiin.dmp.utils

import org.apache.spark.sql.SparkSession
import ru.dmvivakiin.dmp.utils.Platform.Platform

object Platform extends Enumeration {
  type Platform = Value
  val YARN = Value("yarn")
  val LOCAL = Value("local[*]")
  val LOCAL1 = Value("local[1]")
  val LOCAL2 = Value("local[2]")
  val LOCAL3 = Value("local[3]")
  val LOCAL4 = Value("local[4]")
}

object SparkSessionBuilder {

  def getSession(patform: Platform): SparkSession = {

    println(patform)

    val spark: SparkSession = {
      patform match {

        case Platform.YARN =>
          SparkSession.builder
            .config("spark.debug.maxToStringFields", "1024")
            .config("hive.exec.dynamic.partition", "true")
            .config("hive.exec.dynamic.partition.mode", "nonstrict")
            .master(patform.toString) //"yarn"
            .config("spark.sql.autoBroadcastJoinThreshold", 134217728)
            .config("spark.sql.sources.partitionOverwriteMode", "dynamic")
            .config("spark.speculation", false)
            .enableHiveSupport()
            .getOrCreate()

        case _ =>
          SparkSession.builder()
            .appName(s"APP${java.util.UUID.randomUUID.toString.replace("-", "").substring(1, 5)}")
            .master(patform.toString) //"local[2]"
            .getOrCreate()

      }
    }
    //    spark.sparkContext.setLogLevel("INFO")
    spark

  }

  def getConfig(spark: SparkSession): List[String] = {
    val x = spark.sparkContext.getConf
      .getAll
      .map(a => f"${a._1}%-70s:\t${a._2}%s")
    x.toList
  }


}




