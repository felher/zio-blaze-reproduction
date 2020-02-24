scalaVersion := "2.13.1"

name := "server"

organization := "org.felher"

libraryDependencies ++= Seq(
  "dev.zio"         %% "zio"                 % "1.0.0-RC17",
  "dev.zio"         %% "zio-interop-cats"    % "2.0.0.0-RC10",
  "org.http4s"      %% "http4s-blaze-server" % "0.21.1",
  "org.http4s"      %% "http4s-dsl"          % "0.21.1",
  "ch.qos.logback"   % "logback-classic"     % "1.2.3",
)

