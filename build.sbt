val Ver = new {
  val http4s     = "0.23.0-RC1"
  val catsEffect = "3.1.1"
}

val scala3Version = "3.0.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.http4s"    %% "http4s-core"         % Ver.http4s,
      "org.http4s"    %% "http4s-blaze-server" % Ver.http4s,
      "org.http4s"    %% "http4s-blaze-client" % Ver.http4s,
      "org.typelevel" %% "cats-effect"         % Ver.catsEffect
    ),
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
