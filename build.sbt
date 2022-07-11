
inThisBuild(
  List(
    scalaVersion := "2.13.8",
    organization := "nl.thijsbroersen"
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(mavenGraph)

lazy val mavenGraph = Project("mavenCache", file("maven-cache"))
  .settings(
    libraryDependencies ++= Seq(
      "com.michaelpollmeier" %% "gremlin-scala" % "3.5.3.2",
      "org.apache.tinkerpop" % "tinkergraph-gremlin" % "3.5.3"
    )
  )
