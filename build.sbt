val ver = new {
  val http4s     = "0.23.0-RC1"
  val catsEffect = "3.1.1"
  val log4cats   = "2.1.1"
  val logback    = "1.2.3"
}

val scala3Version = "3.0.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel"  %% "log4cats-core"       % ver.log4cats,
      "org.typelevel"  %% "log4cats-slf4j"      % ver.log4cats,
      "ch.qos.logback" % "logback-classic"      % ver.logback,
      "org.http4s"     %% "http4s-dsl"          % ver.http4s,
      "org.http4s"     %% "http4s-blaze-server" % ver.http4s,
      "org.http4s"     %% "http4s-blaze-client" % ver.http4s,
      "org.typelevel"  %% "cats-effect"         % ver.catsEffect
    ),
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
