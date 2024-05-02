ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.19"

lazy val root = (project in file("."))
  .settings(
    name := "spark-in-action-stream-window",
    idePackagePrefix := Some("ru.dmvivakiin.dmp.stream.window")
  )


resolvers += "Maven Central Server" at "https://repo1.maven.org/maven2"


val sparkVersion = "3.0.1"


libraryDependencies ++= Seq(

  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion % "provided",

  //  "org.apache.spark" %% "spark-catalyst" % sparkVersion % "provided",
  //  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",

  "com.typesafe" % "config" % "1.4.2",

  //  "log4j" % "log4j" % "1.2.17",
  //  "ch.qos.logback" % "logback-classic" % "1.2.12",
  //  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",

  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "org.scalatest" %% "scalatest-funsuite" % "3.2.15" % Test,
)


assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

