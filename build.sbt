val tapirVersion = "1.1.0"

lazy val rootProject = (project in file(".")).settings(
  Seq(
    name := "zeiner",
    version := "0.1.0",
    organization := "jaackotorus",
    scalaVersion := "3.2.0",
    scalacOptions :=
      Seq(
        "-deprecation",
        "-explain",
        "-feature",
        "-language:implicitConversions",
        "-unchecked",
        "-Xfatal-warnings",
        // "-Yexplicit-nulls", // experimental (I've seen it cause issues with circe)
        "-Ykind-projector",
        "-Ysafe-init", // experimental (I've seen it cause issues with circe)
      ) ++ Seq("-rewrite", "-indent") ++ Seq("-source", "future-migration"),
    libraryDependencies := Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,

      "org.http4s" %% "http4s-blaze-server" % "0.23.12",

      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,

      "ch.qos.logback" % "logback-classic" % "1.4.0",

      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirVersion % Test,
      "dev.zio" %% "zio-test" % "2.0.2" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.2" % Test,
      "com.softwaremill.sttp.client3" %% "circe" % "3.7.6" % Test
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )
)
