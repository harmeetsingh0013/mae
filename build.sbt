name := """mae-play-app"""
organization := "com.mae"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

lazy val slickVersion = "3.2.0"

libraryDependencies ++= Seq(
  guice,
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "com.typesafe.slick" %% "slick-codegen" % slickVersion % "compile",
  "com.typesafe.slick" %% "slick-testkit" % "3.2.0" % "test",
  "com.h2database" % "h2" % "1.4.196" % "test",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  "org.mockito" % "mockito-all" % "1.10.19" % "test"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

// code generation task
/*
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "slick").getPath
  val url = "jdbc:mysql://localhost:3306/mae_app?user=root&password=root&sslmode=disable&nullNamePatternMatchesAll=true"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.jdbc.MySQLProfile"
  val pkg = "com.mae.repo.models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
  val fname = outputDir + "/com/mae/repo/models/Tables.scala"
  Seq(file(fname))
}

slick <<= slickCodeGenTask // register manual sbt command
sourceGenerators in Compile <+= slickCodeGenTask // register automatic code generation on every compile, remove for only manual use
*/

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")

parallelExecution in Test := false