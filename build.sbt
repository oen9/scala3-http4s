import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

val ver = new {
  val http4s     = "0.23.10"
  val catsEffect = "3.3.5"
  val log4cats   = "2.2.0"
  val logback    = "1.2.5"
  val circe      = "0.14.1"
}

val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name         := "scala3-http4s",
    version      := "0.1.4",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "log4cats-core"       % ver.log4cats,
      "org.typelevel" %% "log4cats-slf4j"      % ver.log4cats,
      "ch.qos.logback" % "logback-classic"     % ver.logback,
      "org.http4s"    %% "http4s-dsl"          % ver.http4s,
      "org.http4s"    %% "http4s-blaze-server" % ver.http4s,
      "org.http4s"    %% "http4s-blaze-client" % ver.http4s,
      "org.http4s"    %% "http4s-circe"        % ver.http4s,
      "org.typelevel" %% "cats-effect"         % ver.catsEffect,
      "io.circe"      %% "circe-core"          % ver.circe,
      "io.circe"      %% "circe-generic"       % ver.circe,
      "com.typesafe"   % "config"              % "1.4.1",
      //"com.github.pureconfig" %% "pureconfig" % "0.17.0",
      ("com.github.pureconfig" %% "pureconfig" % "0.17.1").cross(CrossVersion.for3Use2_13)
    ),
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    dockerExposedPorts                   := Seq(8080),
    dockerBaseImage                      := "oen9/sjdk:0.3",
    Docker / daemonUserUid               := None,
    Docker / daemonUser                  := "root"
  )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
