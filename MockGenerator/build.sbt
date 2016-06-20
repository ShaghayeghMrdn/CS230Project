name := "Mock Generator"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.apache.spark" %% "spark-core" % "1.5.2",
    "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"
    )
